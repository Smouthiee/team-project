package use_case.AirportDataRouteSearch;
import entity.Airport;

public class AirportDataRouteSearchInputData {
    private Airport TakeOffAirport;
    private Airport LandingAirport;

    public AirportDataRouteSearchInputData(String TakeOffAirport, String LandingAirport) {
        this.TakeOffAirport = Airport.getOrCreate(TakeOffAirport);
        this.LandingAirport = Airport.getOrCreate(LandingAirport);
    }

    public Airport getTakeOffAirport() {
        return TakeOffAirport;
    }
    public Airport getLandingAirport() {
        return LandingAirport;
    }
}
