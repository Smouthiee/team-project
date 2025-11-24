package use_case.Favoriteaflight;

public interface FavouriteFlightOutputBoundary {
    void present(FavouriteFlightOutputData outputData);
    void presentError();
}
