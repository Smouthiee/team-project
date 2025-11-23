package use_case.Favoriteaflight;

public class AddToFavouriteInputData {
    private final String userId;
    private final String flightNumber;

    public AddToFavouriteInputData(String userId, String flightNumber) {
        this.userId = userId;
        this.flightNumber = flightNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
