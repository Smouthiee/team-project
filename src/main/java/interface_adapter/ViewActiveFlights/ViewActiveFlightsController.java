package interface_adapter.ViewActiveFlights;

import use_case.ViewActiveFlights.ViewActiveFlightsInputBoundary;
import use_case.ViewActiveFlights.ViewActiveFlightsInputData;


public class ViewActiveFlightsController {
    private final ViewActiveFlightsInputBoundary interactor;

    public ViewActiveFlightsController(ViewActiveFlightsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void viewActiveFlights(String airlineName) {

        if (airlineName == null || airlineName.trim().isEmpty()) {

            ViewActiveFlightsInputData invalidInput =
                    new ViewActiveFlightsInputData(null);

            interactor.execute(invalidInput);
            return;
        }

        ViewActiveFlightsInputData inputData =
                new ViewActiveFlightsInputData(airlineName);

        interactor.execute(inputData);
    }
}
