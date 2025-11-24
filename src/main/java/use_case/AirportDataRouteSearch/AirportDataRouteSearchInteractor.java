package use_case.AirportDataRouteSearch;

import entity.Airport;

public class AirportDataRouteSearchInteractor implements AirportDataRouteSearchInputBoundary{
    private final AirportDataAccessInterface airportDAO;
    private final AirportDataRouteSearchOutputBoundary presenter;

    public AirportDataRouteSearchInteractor(AirportDataAccessInterface airportDAO,
                                            AirportDataRouteSearchOutputBoundary presenter) {
        this.airportDAO = airportDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(AirportDataRouteSearchInputData inputData) {
        String takeoffCode = inputData.getTakeOffAirportCode();
        String landingCode = inputData.getLandingAirportCode();

        Airport takeoff = airportDAO.getAirportByCode(takeoffCode);
        Airport landing = airportDAO.getAirportByCode(landingCode);
    }
}
