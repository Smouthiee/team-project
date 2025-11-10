package use_case.SearchFlightDetails;

public class SearchFlightDetailsInputData {

    private final String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private String departureTime;
    private String arrivalTime;
    private String status;

    public SearchFlightDetailsInputData(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
    public String getDepartureAirport() {
        return departureAirport;
    }
    public String getArrivalAirport() {
        return arrivalAirport;
    }
    public String getDepartureTime() {
        return departureTime;
    }
    public String getArrivalTime() {
        return arrivalTime;
    }
    public String getStatus() {
        return status;
    }
}
