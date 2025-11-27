package interface_adapter.SearchFlightDetails;

import use_case.SearchFlightDetails.SearchFlightDetailsOutputBoundary;
import use_case.SearchFlightDetails.SearchFlightDetailsOutputData;

public class SearchFlightDetailsPresenter implements SearchFlightDetailsOutputBoundary {

    private final SearchFlightDetailsViewModel viewModel;

    public SearchFlightDetailsPresenter(SearchFlightDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SearchFlightDetailsOutputData data) {
        String text = "Callsign: " + data.getCallSign() + "\n" +
                "Origin Country: " + data.getOriginCountry() + "\n" +
                "Time Position: " + data.getTimePosition() + "\n" +
                "Squawk: " + data.getSquawk() + "\n" +
                "On Ground: " + data.isOnGround() + "\n";

        viewModel.setDisplayText(text);
    }
}
