package use_case.SearchFlightDetails;

import entity.Flight;

public class SearchFlightDetailsInteractor implements SearchFlightDetailInputBoundary {

    private final SearchFlightDetailsOutputBoundary presenter;
    private final SearchFlightDetailsDataAccessInterface dao;

    public SearchFlightDetailsInteractor(SearchFlightDetailsDataAccessInterface dao,
                                         SearchFlightDetailsOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchFlightDetailsInputData inputData) {

        String callsign = inputData.getFlightNumber();

        Flight flight = dao.getFlightByNumber(callsign);

        SearchFlightDetailsOutputData outputData;

        if (flight == null) {
            outputData = new SearchFlightDetailsOutputData(
                    callsign,
                    "Unknown",
                    -1,
                    "Unknown",
                    false
            );
        } else {
            outputData = new SearchFlightDetailsOutputData(
                    flight.getCallSign(),
                    flight.getOriginCountry(),
                    flight.getTimePosition(),
                    flight.getSquawk(),
                    flight.isOnGround()
            );
        }

        presenter.present(outputData);
    }
}
