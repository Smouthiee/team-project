package use_case.FavouriteFlight;

public interface FavouriteFlightOutputBoundary {
    void present(FavouriteFlightOutputData outputData);
    void presentError();
}
