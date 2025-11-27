package use_case.ViewActiveFlights;

public interface ViewActiveFlightsOutputBoundary {
    void present(ViewActiveFlightsOutputData outputData);

    void presentInvalidPrefix();
}
