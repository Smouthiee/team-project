package use_case.SearchFlightDetails;

import entity.Flight;

public interface SearchFlightDetailsDataAccessInterface {

    /**
     * Fetches a flight's ADS-B / OpenSky details based on its callSign.
     *
     * @param callSign The flight’s callSign or flight number (e.g., AC850, SQ25).
     * @return A Flight object if found, or null if no matching flight exists.
     */
    Flight getFlightByNumber(String callSign);
}
