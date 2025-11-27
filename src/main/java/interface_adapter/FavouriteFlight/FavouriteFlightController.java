package interface_adapter.FavouriteFlight;

import use_case.FavouriteFlight.FavouriteFlightInputBoundary;
import use_case.FavouriteFlight.FavouriteFlightInputData;

public class FavouriteFlightController {

    private final FavouriteFlightInputBoundary interactor;

    public FavouriteFlightController(FavouriteFlightInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void favouriteFlight(String flightNumber) {

        FavouriteFlightInputData inputData = new FavouriteFlightInputData(flightNumber);

        interactor.execute(inputData);
    }
}
