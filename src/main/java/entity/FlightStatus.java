package entity;

public class FlightStatus {

    private final String flightNumber;
    private double latitude;
    private double longitude;
    private double altitude;
    private double speed;
    private int lastUpdate;

    public FlightStatus(String flightNumber, double latitude,
                        double longitude, double altitude, double speed, int lastUpdate) {
        this.flightNumber = flightNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.lastUpdate = lastUpdate;
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

    public int getLastUpdate() {return lastUpdate;}

    public void updateStatus(double latitude, double longitude, double altitude, double speed) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
    }
}
