package entity;

public class AirlineFlight {

    private final String flightNumber;
    private final String registeredCountry;
    private final double latitude;
    private final double longitude;
    private final double altitude;
    private final double speed;
    private final long lastUpdateTime;

    public AirlineFlight(String flightNumber, String registeredCountry, double latitude, double longitude,
                         double altitude, double speed, long lastUpdateTime) {

        this.flightNumber = flightNumber;
        this.registeredCountry = registeredCountry;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getRegisteredCountry() {
        return registeredCountry;
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

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }
}
