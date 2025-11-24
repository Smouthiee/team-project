package use_case.FavouriteFlight;

import entity.Favourite;
import java.util.HashSet;
import java.util.Set;

public class FavouriteFlightInteractor implements FavouriteFlightInputBoundary {
    private static final String DEFAULT_USER_ID = "default_user";

    // In-memory storage for favourites
    private static final Set<Favourite> favouritesStorage = new HashSet<>();

    private final FavouriteFlightOutputBoundary presenter;

    public FavouriteFlightInteractor(FavouriteFlightOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(FavouriteFlightInputData inputData) {

        String flightNumber = inputData.getFlight_number();

        // Validate input
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            presenter.presentError();
            return;
        }

        // Create Favourite entity with default user
        Favourite newFavourite = new Favourite(DEFAULT_USER_ID, flightNumber);

        // Check if already favourited (using equals method)
        if (favouritesStorage.contains(newFavourite)) {
            presenter.presentError();
            return;
        }

        // Add to favourites
        boolean added = favouritesStorage.add(newFavourite);

        // Create output data and present
        FavouriteFlightOutputData outputData =
                new FavouriteFlightOutputData(flightNumber, added);

        presenter.present(outputData);
    }

    /**
     * Gets all favourited flight numbers.
     * Used by the View to display all favourites.
     * @return Set of all favourite flight numbers
     */
    public static Set<String> getAllFavourites() {
        Set<String> flightNumbers = new HashSet<>();
        for (Favourite fav : favouritesStorage) {
            flightNumbers.add(fav.getFlightNumber());
        }
        return flightNumbers;
    }

    /**
     * Removes a favourite flight.
     * Can be used for an "Unfavourite" feature in the future.
     * @param flightNumber the flight number to remove
     * @return true if removed, false if not found
     */
    public static boolean removeFavourite(String flightNumber) {
        Favourite toRemove = new Favourite(DEFAULT_USER_ID, flightNumber);
        return favouritesStorage.remove(toRemove);
    }

    /**
     * Checks if a flight is already favourited.
     * @param flightNumber the flight number to check
     * @return true if favourited, false otherwise
     */
    public static boolean isFavourited(String flightNumber) {
        Favourite check = new Favourite(DEFAULT_USER_ID, flightNumber);
        return favouritesStorage.contains(check);
    }

}
