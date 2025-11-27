package use_case.TrackFlightStatus;

public class TrackFlightStatusOutputData {

    private final String flightNumber;
    private final double latitude;
    private final double longitude;
    private final double altitude;
    private final double speed;
    private final int lastUpdate;
    private final String message;

    public TrackFlightStatusOutputData(String flightNumber,
                                       double latitude,
                                       double longitude,
                                       double altitude,
                                       double speed,
                                       int lastUpdate,
                                       String message) {
        this.flightNumber = flightNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.lastUpdate = lastUpdate;
        this.message = message;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public int getLastUpdate(){
        return lastUpdate;
    }

    public String getMessage() {
        return message;
    }
}
