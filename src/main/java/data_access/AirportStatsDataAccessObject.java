package data_access;

import entity.Airport;
import entity.AirportRepository;
import use_case.AirportDataRouteSearch.AirportDataAccessInterface;
import data_access.Util.*;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Path;

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

    // OLD CODE:
    public AirportStatsDataAccessObject() {
        // Fake data for testing / demo; you can adjust or load from a file later.
        Airport yyz = new Airport("YYZ", "Toronto", "Canada");
        Airport yvr = new Airport("YVR", "Vancouver", "Canada");
        yyz.addconnectTo(yvr);

        airports.put(yyz.getIATA(), yyz);
        airports.put(yvr.getIATA(), yvr);
    }

    @Override
    public Airport getAirportByCode(String iataCode) {
        if (iataCode == null) {
            return null;
        }
        return airports.get(iataCode.toUpperCase());
    }
}
