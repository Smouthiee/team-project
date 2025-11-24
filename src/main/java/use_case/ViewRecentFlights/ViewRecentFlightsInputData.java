package use_case.ViewRecentFlights;

public class ViewRecentFlightsInputData {

    private final String airline;

    public ViewRecentFlightsInputData(String airline) {
        this.airline = airline;
    }

    public String getAirline() {
        return airline;
    }
}
