package entity;

public class AirlineFlight {

    private final String flightNumber;
    private final double latitude;
    private final double longitude;

    private final double altitude;  // meters
    private final double speed;

    public AirlineFlight(String flightNumber,
                         double latitude,
                         double longitude,
                         double altitude,
                         double speed) {

        this.flightNumber = flightNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
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
}
