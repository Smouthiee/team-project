package interface_adapter.ViewActiveFlights;

import entity.AirlineFlight;
import use_case.ViewActiveFlights.ViewActiveFlightsOutputBoundary;
import view.ViewActiveFlightsView;

import javax.swing.*;
import java.util.List;

public class ViewActiveFlightsPresenter implements ViewActiveFlightsOutputBoundary {

    private final ViewActiveFlightsView view;

    public ViewActiveFlightsPresenter(ViewActiveFlightsView view) {
        this.view = view;
    }

    @Override
    public void present(List<AirlineFlight> flights) {
        view.showFlights(flights);
    }

    @Override
    public void presentInvalidPrefix() {
        view.showInvalidPrefixMessage();
    }
}
