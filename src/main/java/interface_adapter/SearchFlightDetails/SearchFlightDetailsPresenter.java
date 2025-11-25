package interface_adapter.SearchFlightDetails;

import use_case.SearchFlightDetails.SearchFlightDetailsOutputBoundary;
import use_case.SearchFlightDetails.SearchFlightDetailsOutputData;
import view.SearchFlightDetailsScreen;

public class SearchFlightDetailsPresenter implements SearchFlightDetailsOutputBoundary {

    private final SearchFlightDetailsScreen view;

    public SearchFlightDetailsPresenter(SearchFlightDetailsScreen view) {
        this.view = view;
    }

    @Override
    public void present(SearchFlightDetailsOutputData outputData) {
        view.showFlightDetails(outputData);
    }
}
