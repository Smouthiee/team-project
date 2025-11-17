package interface_adapter.AirportDataRS;

import use_case.AirportDataRouteSearch.AirportDataRouteSearchInputBoundary;
import use_case.AirportDataRouteSearch.AirportDataRouteSearchInputData;


public class AirportDataRSController {
    private final AirportDataRouteSearchInputBoundary interactor;

    public AirportDataRSController(AirportDataRouteSearchInputBoundary interactor) { this.interactor = interactor; }

    public void AirportDataRoute(String TakeOffAirport, String LandingAirport){
        AirportDataRouteSearchInputData inputData = new AirportDataRouteSearchInputData(TakeOffAirport,LandingAirport);
        interactor.execute(inputData);
    }
}
