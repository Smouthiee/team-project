package data_access;

import entity.Airport;
import entity.AirportRepository;
import use_case.AirportDataRouteSearch.AirportDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class AirportStatsDataAccessObject implements AirportDataAccessInterface{
    private final Map<String, Airport> airports = new HashMap<>();

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
