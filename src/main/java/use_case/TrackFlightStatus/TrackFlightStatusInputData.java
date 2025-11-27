package use_case.TrackFlightStatus;

public class TrackFlightStatusInputData {
    private final String flightNumber;

    public TrackFlightStatusInputData(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
