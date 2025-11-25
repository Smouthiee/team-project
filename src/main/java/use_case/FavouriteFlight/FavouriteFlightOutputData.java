package use_case.FavouriteFlight;

public class FavouriteFlightOutputData {
    private final String flight_number;
    private final boolean success;

    public FavouriteFlightOutputData(String flight_number, boolean success) {
        this.flight_number = flight_number;
        this.success = success;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public boolean isSuccess() {
        return success;
    }

}
