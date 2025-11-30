package data_access;

import entity.Airport;
import entity.AirportRepository;
import use_case.AirportDataRouteSearch.AirportDataAccessInterface;
import data_access.Util.*;

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

}
