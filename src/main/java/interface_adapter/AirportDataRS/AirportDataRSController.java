package interface_adapter.AirportDataRS;

import use_case.AirportDataRouteSearch.AirportDataRouteSearchInputBoundary;
import use_case.AirportDataRouteSearch.AirportDataRouteSearchInputData;


public class AirportDataRSController {
    private final AirportDataRouteSearchInputBoundary interactor;

    public AirportDataRSController(AirportDataRouteSearchInputBoundary interactor) { this.interactor = interactor; }

    public void searchRoute(String takeOffAirport, String landingAirport){
        AirportDataRouteSearchInputData inputData = new AirportDataRouteSearchInputData(takeOffAirport,landingAirport);
        interactor.execute(inputData);
    }
}
