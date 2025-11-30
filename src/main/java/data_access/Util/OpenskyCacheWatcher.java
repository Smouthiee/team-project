package data_access.Util;

import use_case.Util.OtoABridgeJob;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.lang.Runtime;

public class OpenskyCacheWatcher implements Runnable{

    private final Path directoryToWatch;
    private final String fileNameToWatch;
    private final OtoABridgeJob job;

    private volatile boolean running = false;
    private Thread thread;

    private long lastTriggerMillis = 0;
    private final long debounceMillis = 1000;
    public OpenskyCacheWatcher() {
        this.directoryToWatch = Paths.get("src");
        this.fileNameToWatch = "OpenSky_cache.json";
        this.job = new OtoABridgeJob();
    }

    public void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "OpenSkyCacheWatcher");
        thread.setDaemon(true); // won't prevent JVM shutdown
        thread.start();
    }

    public void stop() {
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }
    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()){
            if (!Files.exists(directoryToWatch)) {
                System.err.println("Directory to watch does not exist: " + directoryToWatch);
                return;
            }
            directoryToWatch.register(watchService, ENTRY_CREATE, ENTRY_MODIFY);
            System.out.println("OpenSkyCacheWatcher started, watching: " + directoryToWatch);

            while (running) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    if (!running) {
                        break; // we were asked to stop
                    }
                    continue;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    @SuppressWarnings("unchecked")
                            WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path changed = ev.context();
                    if (changed != null && changed.getFileName().toString().equals(fileNameToWatch)) {
                        long now = System.currentTimeMillis();
                        if (now - lastTriggerMillis > debounceMillis) {
                            lastTriggerMillis = now;
                            System.out.println("Detected change in " + fileNameToWatch
                                    + " (" + kind.name() + "), running job...");
                            // Run job in a separate thread so watcher loop isn't blocked
                            new Thread(job::RunOnce, "OpenSkyToAirportJobRunner").start();
                        } else {
                            System.out.println("Change detected but ignored (debounced).");
                        }
                    }
                }
                boolean valid = key.reset();
                if (!valid) {
                    System.err.println("WatchKey no longer valid, stopping watcher.");
                    break;
                }

            }

        }catch (IOException e){
            System.err.println("I/O error in OpenSkyCacheWatcher: " + e.getMessage());
        }
        System.out.println("OpenSkyCacheWatcher stopped.");
    }
}
