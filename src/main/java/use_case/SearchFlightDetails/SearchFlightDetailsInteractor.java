package use_case.SearchFlightDetails;

import data_access.FlightDataAccessInterface;
import entity.Flight;

public class SearchFlightDetailsInteractor implements SearchFlightDetailInputBoundary {

    private final FlightDataAccessInterface flightDataAccessObject;
    private final SearchFlightDetailsOutputBoundary searchFlightDetailsPresenter;

    public SearchFlightDetailsInteractor(FlightDataAccessInterface flightDataAccessObject,
                                         SearchFlightDetailsOutputBoundary searchFlightDetailsPresenter) {
        this.flightDataAccessObject = flightDataAccessObject;
        this.searchFlightDetailsPresenter = searchFlightDetailsPresenter;
    }


    @Override
    public void execute(SearchFlightDetailsInputData inputData) {
        String flightNumber = inputData.getFlightNumber();
        Flight flight = flightDataAccessObject.getFlightByNumber(flightNumber);

        SearchFlightDetailsOutputData outputData;

        if (flight == null) {
            outputData = new SearchFlightDetailsOutputData(flightNumber, "N/A" , "N/A",
                            "Flight not found", "N/A", "N/A");
            searchFlightDetailsPresenter.present(outputData);
            return;

        } else {
            outputData = new SearchFlightDetailsOutputData(
                    flight.getFlightNumber(),
                    flight.getDepartureAirport(),
                    flight.getArrivalAirport(),
                    flight.getStatus(),
                    flight.getDepartureTime(),
                    flight.getArrivalTime()
            );
        }
    }
}