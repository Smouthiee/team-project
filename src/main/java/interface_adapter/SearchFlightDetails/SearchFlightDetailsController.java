package interface_adapter.SearchFlightDetails;

import use_case.SearchFlightDetails.SearchFlightDetailInputBoundary;
import use_case.SearchFlightDetails.SearchFlightDetailsInputData;

public class SearchFlightDetailsController {

    private final SearchFlightDetailInputBoundary interactor;

    public SearchFlightDetailsController(SearchFlightDetailInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void executeSearch(String flightNumber) {
        SearchFlightDetailsInputData inputData =
                new SearchFlightDetailsInputData(flightNumber);
        interactor.execute(inputData);
    }
}
