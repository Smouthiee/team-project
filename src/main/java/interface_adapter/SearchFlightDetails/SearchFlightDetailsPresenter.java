package interface_adapter.SearchFlightDetails;

import use_case.SearchFlightDetails.SearchFlightDetailsOutputBoundary;
import use_case.SearchFlightDetails.SearchFlightDetailsOutputData;
import view.

public class SearchFlightDetailsPresenter implements SearchFlightDetailsOutputBoundary {

    private final SearchFlightDetailsView view;

    public SearchFlightDetailsPresenter(SearchFlightDetailsView view) {
        this.view = view;
    }

    @Override
    public void present(SearchFlightDetailsOutputData outputData) {
        view.showFlightDetails(data);
    }
}
