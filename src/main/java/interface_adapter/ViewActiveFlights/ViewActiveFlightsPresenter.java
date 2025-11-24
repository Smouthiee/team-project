package interface_adapter.ViewActiveFlights;

import use_case.ViewActiveFlights.ViewActiveFlightsOutputBoundary;
import use_case.ViewActiveFlights.ViewActiveFlightsOutputData;
import view.ViewActiveFlightsView;

import javax.swing.*;

public class ViewActiveFlightsPresenter implements ViewActiveFlightsOutputBoundary {

    private final ViewActiveFlightsView view;

    public ViewActiveFlightsPresenter(ViewActiveFlightsView view) {
        this.view = view;
    }

    @Override
    public void present(ViewActiveFlightsOutputData outputData) {
        view.showFlights(outputData.getFlights());
    }

    @Override
    public void presentInvalidPrefix() {
        view.showInvalidPrefixMessage();
    }
}
