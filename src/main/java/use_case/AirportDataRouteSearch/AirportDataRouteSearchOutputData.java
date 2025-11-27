package use_case.AirportDataRouteSearch;

import entity.Airport;
import java.util.HashMap;
import java.util.Map;

public class AirportDataRouteSearchOutputData{

    private final Airport takeOffAirport;
    private final Airport landingAirport;
    private final boolean bothFound;
    private final String message;

    public AirportDataRouteSearchOutputData(Airport takeOffAirport,
                                            Airport landingAirport,
                                            boolean bothFound,
                                            String message) {
        this.takeOffAirport = takeOffAirport;
        this.landingAirport = landingAirport;
        this.bothFound = bothFound;
        this.message = message;
    }

    public Airport getTakeOffAirport() {
        return takeOffAirport;
    }

    public Airport getLandingAirport() {
        return landingAirport;
    }

    public boolean areBothFound() {
        return bothFound;
    }

    public String getMessage() {
        return message;
    }
}
