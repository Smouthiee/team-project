package interface_adapter.Favoriteaflight;

import use_case.FavouriteFlight.FavouriteFlightOutputBoundary;
import use_case.FavouriteFlight.FavouriteFlightOutputData;
import view.FavouriteFlightView;

public class FavouriteFlightPresenter implements FavouriteFlightOutputBoundary {

    private final FavouriteFlightView view;

    public FavouriteFlightPresenter(FavouriteFlightView view) {
        this.view = view;
    }

    @Override
    public void present(FavouriteFlightOutputData outputData) {
        String flightNumber = outputData.getFlightNumber();
        view.showSuccess(flightNumber);
    }

    public void presentError() {
        view.showError();
    }

}
