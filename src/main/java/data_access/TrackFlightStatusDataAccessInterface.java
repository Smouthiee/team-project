package data_access;
import entity.FlightStatus;

public interface TrackFlightStatusDataAccessInterface {
    FlightStatus getStatusByFlightNumber(String flightNumber);
}
