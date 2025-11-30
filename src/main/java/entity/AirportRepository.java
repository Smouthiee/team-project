package entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AirportRepository {

    private final Map<String, Airport> airportsByCode = new HashMap<>();

    public Airport getOrCreate(String IATA) {
        if (IATA == null || IATA.trim().isEmpty()) {
            throw new IllegalArgumentException("IATA code cannot be null or blank");
        }
        String key = IATA.trim().toUpperCase();
        return airportsByCode.computeIfAbsent(key, Airport::new);
    }

    public Airport get(String IATA) {
        if (IATA == null) return null;
        return airportsByCode.get(IATA.trim().toUpperCase());
    }

    public Collection<Airport> getAllAirports() {
        return airportsByCode.values();
    }

    public void connect(String IATA1, String IATA2) {
        Airport a = getOrCreate(IATA1);
        Airport b = getOrCreate(IATA2);
        a.addconnectTo(b);
    }
}
