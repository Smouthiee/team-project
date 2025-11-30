package data_access;

import entity.Airport;
import entity.AirportRepository;
import use_case.AirportDataRouteSearch.AirportDataAccessInterface;
import data_access.Util.*;

import java.io.IOException;
import java.nio.file.Path;

// Add the import for the background service part:
import data_access.Util.FlightRadarApiCaller;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


public class AirportStatsDataAccessObject implements AirportDataAccessInterface{
    private final AirportRepository airportRepository;
    private final APGraphDataPresistence persistence;

    public AirportStatsDataAccessObject() {
        this(Path.of("data"));
    }

    public AirportStatsDataAccessObject(Path dataDir) {
        this.airportRepository = new AirportRepository();
        this.persistence = new APGraphDataPresistence(dataDir);

        try {
            // Load initial + latest offload into the in-memory repo
            persistence.loadInitialAndLatestSnapshot(airportRepository);
        } catch (IOException e) {
            // For now, just log; you can improve error handling later
            System.err.println("Failed to load airport graph: " + e.getMessage());
        }
    }

    @Override
    public Airport getAirportByCode(String iataCode) {
        if (iataCode == null) return null;
        return airportRepository.get(iataCode);
    }

    public void offloadSnapshot() {
        try {
            var file = persistence.offloadSnapshot(airportRepository);
            System.out.println("Airport graph offloaded to: " + file);
        } catch (IOException e) {
            System.err.println("Failed to offload airport graph: " + e.getMessage());
        }
    }

    public AirportRepository getAirportRepository() {
        return airportRepository;
    }


    /*
    The cocurrent enrichment updater part begin
     */
    private static class Route{
        final String origin;
        final String dest;
        final String callsign;

        Route(String origin, String dest, String callsign) {
            this.origin = origin;
            this.dest = dest;
            this.callsign = callsign;
        }
    }

    private String chooseCode(String iata, String icao) {
        if (iata != null && !iata.isBlank()) {
            return iata.trim().toUpperCase();
        }
        if (icao != null && !icao.isBlank()) {
            return icao.trim().toUpperCase();
        }
        return null;
    }

    public void GraphEnrichmentByCallSign(List<String> callsigns,FlightRadarApiCaller flightClient) {
        if (callsigns == null || callsigns.isEmpty()) {
            System.out.println("No callsigns provided for the airport graph.");
            return;
        }
        Set<String> uniqueCallsigns = new HashSet<>();
        for (String e : callsigns) {
            if (e != null) {
                String trimmed = e.trim();
                if (!trimmed.isEmpty()) {
                    uniqueCallsigns.add(trimmed);
                }
            }
        }

        if (uniqueCallsigns.isEmpty()) {
            System.out.println("No valid callsigns for the airport communities.");
            return;
        }
        // limit callsign for money reason...... PlZ we need some money.....
        final int MAX_CALLSIGNS = 10;

        List<String> selectedCallsigns = new ArrayList<>(uniqueCallsigns);
        Collections.shuffle(selectedCallsigns);  // random order
        if (selectedCallsigns.size() > MAX_CALLSIGNS) {
            selectedCallsigns = selectedCallsigns.subList(0, MAX_CALLSIGNS);
        }

        System.out.println("Enriching airport graph using " + selectedCallsigns.size()
                + " callsigns (random sample of " + uniqueCallsigns.size() + " unique)." +
                "Because we are poor, if you could donate money please conatact us...... uwu");


        // Might run into issues becasue hardware cpu limitation.
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Callable<Route>> tasks = new ArrayList<>();

        for (String cs : selectedCallsigns) {
            tasks.add(() -> {
                try {
                    ArrayList<String> info = flightClient.getFlightByCallsign(cs);
                    if (info == null || info.size() < 6) {
                        System.err.println("No valid flight info for callsign " + cs);
                        return null;
                    }

                    // 0 flight, 1 callsign, 2 orig_iata, 3 dest_iata, 4 orig_icao, 5 dest_icao
                    String origIata = info.get(2);
                    String destIata = info.get(3);
                    String origIcao = info.get(4);
                    String destIcao = info.get(5);

                    String origin = chooseCode(origIata, origIcao);
                    String dest = chooseCode(destIata, destIcao);

                    if (origin == null || dest == null || origin.equalsIgnoreCase(dest)) {
                        return null;
                    }

                    return new Route(origin, dest, cs);

                } catch (Exception e) {
                    System.err.println("Error calling FlightRadar for " + cs + ": " + e.getMessage());
                    return null;
                }
            });
        }

        List<Route> routes = new ArrayList<>();

        try {
            List<Future<Route>> futures = executor.invokeAll(tasks);
            for (Future<Route> f : futures) {
                try {
                    Route r = f.get();
                    if (r != null) {
                        routes.add(r);
                    }
                } catch (ExecutionException ee) {
                    System.err.println("Task execution error: " + ee.getCause());
                }
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("Enrichment interrupted: " + ie.getMessage());
        } finally {
            executor.shutdown();
        }
        for (Route r : routes) {
            Airport a = airportRepository.getOrCreate(r.origin);
            Airport b = airportRepository.getOrCreate(r.dest);
            a.addconnectTo(b);
            System.out.println("Connected " + r.origin + " <-> " + r.dest +
                    " from callsign " + r.callsign);
        }
    }
}
