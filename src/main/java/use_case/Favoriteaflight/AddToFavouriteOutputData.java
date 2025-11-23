package use_case.Favoriteaflight;

public class AddToFavouriteOutputData {
    private final String message;
    private final String flightNumber;

    public AddToFavouriteOutputData(String message, String flightNumber) {
        this.message = message;
        this.flightNumber = flightNumber;
    }

    public String getMessage() {
        return message;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
