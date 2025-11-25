package use_case.SearchFlightDetails;

public class SearchFlightDetailsInputData {

    private final String flightNumber;

    public SearchFlightDetailsInputData(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}