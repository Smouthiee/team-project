package use_case.Favoriteaflight;

public interface AddToFavouriteOutputBoundary {
    void prepareSuccessView(AddToFavouriteOutputData outputData);
    void prepareFailView(String errorMessage);
}
