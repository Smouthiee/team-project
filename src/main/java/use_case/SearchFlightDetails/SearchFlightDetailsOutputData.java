package use_case.SearchFlightDetails;

public class SearchFlightDetailsOutputData  {
    private String flightNumber;
    private final String departureAirport;
    private final String arrivalAirport;
    private final String status;
    private final String departureTime;
    private final String arrivalTime;

    public SearchFlightDetailsOutputData(String flightNumber,
                                         String departureAirport,
                                         String arrivalAirport,
                                         String status,
                                         String departureTime,
                                         String arrivalTime) {
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.status = status;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public void setFlightNumber(String flightNumber) {
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
    public String getStatus() {
        return status;
    }
    public String getDepartureTime() {
        return departureTime;
    }
    public String getArrivalTime() {
        return arrivalTime;
    }
}