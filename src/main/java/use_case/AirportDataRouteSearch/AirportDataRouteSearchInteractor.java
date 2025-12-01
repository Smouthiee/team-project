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
        String takeoffCode = inputData.getTakeOffAirport();
        String landingCode = inputData.getLandingAirport();

        Airport takeOff = airportDAO.getAirportByCode(takeoffCode);
        Airport landing = airportDAO.getAirportByCode(landingCode);

        boolean bothFound = (takeOff != null && landing != null);
        String message;

        if (!bothFound) {
            if (takeOff == null && landing == null) {
                message = "Neither airport " + takeoffCode + " nor " + landingCode + " was found.";
            } else if (takeOff == null) {
                message = "Takeoff airport " + takeoffCode + " was not found.";
            } else {
                message = "Landing airport " + landingCode + " was not found.";
            }
        } else {
            // Route-finding not yet implemented; just report the two airports.
            if (takeOff.isConnectedTo(landing)) {
                message = "Air ports has direct connect flight";
            }
            else  {
                message = "Airports has no direct flight, force-directed graphs search algo not yet implemented.";
            }

        }

        AirportDataRouteSearchOutputData outputData =
                new AirportDataRouteSearchOutputData(takeOff, landing, bothFound, message);
        presenter.present(outputData);
    }
}
