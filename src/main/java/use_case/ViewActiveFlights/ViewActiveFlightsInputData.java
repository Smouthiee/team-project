package use_case.ViewActiveFlights;

public class ViewActiveFlightsInputData {

    private final String airlinePrefix;

    public ViewActiveFlightsInputData(String airline) {
        this.airlinePrefix = airline;
    }

    public String getAirlinePrefix() {
        return airlinePrefix;
    }
}
