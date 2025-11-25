package use_case.ViewActiveFlights;

import entity.AirlineFlight;

import java.util.List;

public class ViewActiveFlightsOutputData {
    private final List<AirlineFlight> flights;

    public ViewActiveFlightsOutputData(List<AirlineFlight> flights) {
        this.flights = flights;
    }

    public List<AirlineFlight> getFlights() {
        return flights;
    }
}
