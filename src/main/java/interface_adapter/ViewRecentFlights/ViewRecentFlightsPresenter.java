package interface_adapter.ViewRecentFlights;

import entity.AirlineFlight;
import use_case.ViewRecentFlights.ViewRecentFlightsOutputBoundary;
import use_case.ViewRecentFlights.ViewRecentFlightsOutputData;
import view.ViewRecentFlightsView;
import java.util.List;

public class ViewRecentFlightsPresenter implements ViewRecentFlightsOutputBoundary {
    private final ViewRecentFlightsView view;

    public ViewRecentFlightsPresenter(ViewRecentFlightsView view) {
        this.view = view;
    }

    @Override
    public void present(ViewRecentFlightsOutputData outputData) {
        List<AirlineFlight> flights = outputData.getFlights();
        view.showFlights(flights);
    }
}
