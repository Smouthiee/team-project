package use_case.AirportDataRouteSearch;

import entity.Airport;

public interface AirportDataAccessInterface {
    Airport getAirportByCode(String iataCode);
}
