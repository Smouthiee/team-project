package interface_adapter.TrackFlightStatus;

public class TrackFlightStatusViewModel {

    private String flightNumber;
    private double latitude;
    private double longitude;
    private double altitude;
    private double speed;
    private String message;

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getAltitude() { return altitude; }
    public void setAltitude(double altitude) { this.altitude = altitude; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
