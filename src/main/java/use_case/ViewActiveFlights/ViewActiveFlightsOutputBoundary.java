package use_case.ViewActiveFlights;

import entity.AirlineFlight;

import java.util.List;


public interface ViewActiveFlightsOutputBoundary {
    void present(List<AirlineFlight> flights);

    void presentInvalidPrefix();
}
