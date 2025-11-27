package use_case.FavouriteFlight;

public class FavouriteFlightInputData {
    private final String flight_number;

    public FavouriteFlightInputData(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getFlight_number() {
        return flight_number;
    }
}
