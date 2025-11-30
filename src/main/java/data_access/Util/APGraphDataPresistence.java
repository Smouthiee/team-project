package data_access.Util;

import entity.Airport;
import entity.AirportRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class APGraphDataPresistence {
    private final Path dataDir;
    private static final String INITIAL_FILENAME = "airport_initial_load.json";
    private static final String OFFLOAD_PREFIX   = "airport_offload_";
    private static final String OFFLOAD_SUFFIX   = ".json";
    private static final DateTimeFormatter TS_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public APGraphDataPresistence() {this(Path.of("Data"));}

    public APGraphDataPresistence(Path dataDir) {this.dataDir = dataDir;}

    public void loadInitialAndLatestSnapshot(AirportRepository repo) throws IOException {
        Files.createDirectories(dataDir);

        // 1. Load initial
        Path initialPath = dataDir.resolve(INITIAL_FILENAME);
        loadFromFileIfExists(initialPath, repo);

        // 2. Find and load latest offload snapshot
        Path latestOffload = findLatestOffloadFile();
        if (latestOffload != null) {
            loadFromFileIfExists(latestOffload, repo);
        }
    }

    public Path offloadSnapshot(AirportRepository repo) throws IOException {
        Files.createDirectories(dataDir);

        String ts = LocalDateTime.now().format(TS_FORMAT);
        String filename = OFFLOAD_PREFIX + ts + OFFLOAD_SUFFIX;
        Path filePath = dataDir.resolve(filename);

        JSONObject snapshot = buildSnapshotJson(repo);

        try (OutputStream out = Files.newOutputStream(filePath)) {
            out.write(snapshot.toString(2).getBytes(StandardCharsets.UTF_8));
        }

        return filePath;
    }
    // internal helper functions:
    private void loadFromFileIfExists(Path file, AirportRepository repo) throws IOException {
        if (!Files.exists(file)) {
            return;
        }
        try (InputStream in = Files.newInputStream(file)) {
            JSONObject root = new JSONObject(new JSONTokener(in));
            loadFromJson(root, repo);
        }
    }
    private Path findLatestOffloadFile() throws IOException {
        if (!Files.exists(dataDir)) {
            return null;
        }

        try (Stream<Path> stream = Files.list(dataDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.startsWith(OFFLOAD_PREFIX) && name.endsWith(OFFLOAD_SUFFIX))
                    .max(Comparator.naturalOrder())
                    .map(name -> dataDir.resolve(name))
                    .orElse(null);
        }
    }

    private void loadFromJson(JSONObject root, AirportRepository repo) {
        // 1. Airports
        JSONArray airportsArr = root.optJSONArray("airports");
        if (airportsArr != null) {
            for (int i = 0; i < airportsArr.length(); i++) {
                JSONObject obj = airportsArr.getJSONObject(i);
                String code = obj.getString("code").trim().toUpperCase();
                String city = obj.optString("city", "");
                String country = obj.optString("country", "");

                Airport a = repo.getOrCreate(code);
                // You can decide overwrite logic; here we only fill missing fields:
                if (a.getCity() == null || a.getCity().isEmpty()) {
                    a.setCity(city);
                }
                if (a.getCountry() == null || a.getCountry().isEmpty()) {
                    a.setCountry(country);
                }
            }
        }
        // 2. Connections
        JSONArray connArr = root.optJSONArray("connections");
        if (connArr != null) {
            for (int i = 0; i < connArr.length(); i++) {
                JSONObject c = connArr.getJSONObject(i);
                String codeA = c.getString("a").trim().toUpperCase();
                String codeB = c.getString("b").trim().toUpperCase();

                Airport a = repo.getOrCreate(codeA);
                Airport b = repo.getOrCreate(codeB);
                a.addconnectTo(b);
            }
        }
    }
        private JSONObject buildSnapshotJson(AirportRepository repo) {
            JSONObject root = new JSONObject();
            JSONArray airportsArr = new JSONArray();
            JSONArray connectionsArr = new JSONArray();

            // 1. Airports
            for (Airport a : repo.getAllAirports()) {
                JSONObject obj = new JSONObject();
                obj.put("code", a.getIATA());
                obj.put("city", a.getCity());
                obj.put("country", a.getCountry());
                airportsArr.put(obj);
            }

            // 2. Connections (avoid duplicates by using sorted key "AAA:BBB")
            Set<String> seen = new HashSet<>();
            for (Airport a : repo.getAllAirports()) {
                String codeA = a.getIATA();
                for (Airport b : a.getConnections()) {
                    String codeB = b.getIATA();
                    if (codeA.equalsIgnoreCase(codeB)) continue;

                    String key = makeKey(codeA, codeB);
                    if (seen.add(key)) {
                        String[] parts = key.split(":");
                        JSONObject c = new JSONObject();
                        c.put("a", parts[0]);
                        c.put("b", parts[1]);
                        connectionsArr.put(c);
                    }
                }
            }

            root.put("airports", airportsArr);
            root.put("connections", connectionsArr);
            return root;
        }

        private String makeKey(String a, String b) {
            String aa = a.trim().toUpperCase();
            String bb = b.trim().toUpperCase();
            return (aa.compareTo(bb) <= 0) ? aa + ":" + bb : bb + ":" + aa;
        }
}
