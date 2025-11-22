package use_case.ViewActiveFlights;

import entity.AirlineFlight;
import java.util.List;


public interface ViewActiveFlightsDataAccessInterface {
    // Returns active flights whose call sign begins with the given airline prefix.
    // Example: "ACA" represents Air Canada.
    List<AirlineFlight> getActiveFlightsByAirline(String airlinePrefix);
}
