package use_case.Favoriteaflight;

import entity.Favourite;
import java.util.HashSet;
import java.util.Set;

public class FavouriteFlightInteractor implements FavouriteFlightInputBoundary {
    private static final String DEFAULT_USER_ID = "default_user";

    private static final Set<Favourite> favouritesStorage = new HashSet<>();

    private final FavouriteFlightOutputBoundary presenter;

    public FavouriteFlightInteractor(FavouriteFlightOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void execute(FavouriteFlightInputData inputData) {

        String flight_number = inputData.getFlight_number();

        if (flight_number == null || flight_number.trim().isEmpty()) {
            presenter.presentError();
            return;
        }

        Favourite newFavourite = new Favourite(DEFAULT_USER_ID, flight_number);

        if (favouritesStorage.contains(newFavourite)) {
            presenter.presentError();
            return;
        }

        boolean added = favouritesStorage.add(newFavourite);

        FavouriteFlightOutputData outputData =
                new FavouriteFlightOutputData(flight_number, added);

        presenter.present(outputData);
    }

}
