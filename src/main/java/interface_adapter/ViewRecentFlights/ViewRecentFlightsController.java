package interface_adapter.ViewRecentFlights;

import use_case.ViewRecentFlights.ViewRecentFlightsInputBoundary;
import use_case.ViewRecentFlights.ViewRecentFlightsInputData;


public class ViewRecentFlightsController {
    private final ViewRecentFlightsInputBoundary interactor;

    public ViewRecentFlightsController(ViewRecentFlightsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void viewRecentFlights(String airlineName) {
        ViewRecentFlightsInputData inputData = new ViewRecentFlightsInputData(airlineName);
        interactor.execute(inputData);
    }
}
