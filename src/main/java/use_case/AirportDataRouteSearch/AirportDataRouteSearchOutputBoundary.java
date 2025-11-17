package use_case.AirportDataRouteSearch;

public interface AirportDataRouteSearchOutputBoundary {
    void present(AirportDataRouteSearchOutputData OutputData);

    void present (AirportDataRouteSearchOutputBoundary OutputData);
}
