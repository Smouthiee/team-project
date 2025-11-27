package use_case.AirportDataRouteSearch;
import entity.Airport;

public class AirportDataRouteSearchInputData {
    private final String TakeOffAirport;
    private final String LandingAirport;

    public AirportDataRouteSearchInputData(String takeOffAirport, String landingAirport) {
        this.TakeOffAirport = takeOffAirport;
        this.LandingAirport = landingAirport;
    }

    public String getTakeOffAirport() {
        return TakeOffAirport;
    }
    public String getLandingAirport() {
        return LandingAirport;
    }
}
