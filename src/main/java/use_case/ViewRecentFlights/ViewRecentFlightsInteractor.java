package use_case.ViewRecentFlights;

//import data_access.FlightDataAccessInterface;
import entity.AirlineFlight;
import java.util.List;


public class ViewRecentFlightsInteractor implements ViewRecentFlightsInputBoundary {

    private final FlightDataAccessInterface flightDataAccess;
    private final ViewRecentFlightsOutputBoundary presenter;

    public ViewRecentFlightsInteractor(FlightDataAccessInterface flightDataAccess,
                                       ViewRecentFlightsOutputBoundary presenter) {
        this.flightDataAccess = flightDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewRecentFlightsInputData inputData) {
        String airlineName = inputData.getAirline();
        List<AirlineFlight> flights = flightDataAccess.getRecentFlightsByAirline(airlineName);

        ViewRecentFlightsOutputData outputData = new ViewRecentFlightsOutputData(flights);
        presenter.present(outputData);
    }
}
