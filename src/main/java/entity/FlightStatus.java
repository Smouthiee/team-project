package entity;

public class FlightStatus {

    private final String flightNumber;
    private final String estimatedArrivalTime;
    private double latitude;
    private double longitude;
    private double altitude;
    private double speed;

    public FlightStatus(String flightNumber, double estimatedArrivalTime,
                        double latitude, double longitude, double altitude, double speed) {
        this.flightNumber = flightNumber;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getEstimatedArrivalTime() {
        return estimatedArrivalTime;
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

    public void updateStatus(double latitude, double longitude, double altitude, double speed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
    }
}
