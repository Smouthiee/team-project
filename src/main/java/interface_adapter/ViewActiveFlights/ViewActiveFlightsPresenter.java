package interface_adapter.ViewActiveFlights;

import use_case.ViewActiveFlights.ViewActiveFlightsOutputBoundary;
import use_case.ViewActiveFlights.ViewActiveFlightsOutputData;
import view.ViewActiveFlightsView;

import javax.swing.*;

import entity.AirlineFlight;

public class ViewActiveFlightsPresenter implements ViewActiveFlightsOutputBoundary {

    private final ViewActiveFlightsView view;
    private final ViewActiveFlightsViewModel viewModel;

    public ViewActiveFlightsPresenter(ViewActiveFlightsView view,
                                      ViewActiveFlightsViewModel viewModel) {
        this.view = view;
        this.viewModel = viewModel;
    }

    @Override
    public void present(ViewActiveFlightsOutputData outputData) {

        StringBuilder builder = new StringBuilder();

        if (outputData.getFlights() == null || outputData.getFlights().isEmpty()) {
            builder.append("No active flights found for this airline.");
        } else {
            builder.append("Active Flights\n");
            builder.append("------------------------------\n\n");

            for (AirlineFlight f : outputData.getFlights()) {

                double altitudeFeet = f.getAltitude() * 3.28084;
                double speedKmh = f.getSpeed() * 3.6;

                long currentTime = System.currentTimeMillis() / 1000;
                long secondsAgo = currentTime - f.getLastUpdateTime();

                builder.append("Flight: ").append(f.getFlightNumber()).append("\n")
                        .append("Registered Country: ").append(f.getRegisteredCountry()).append("\n")
                        .append("Location: (")
                        .append(String.format("%.2f", f.getLatitude()))
                        .append(", ")
                        .append(String.format("%.2f", f.getLongitude()))
                        .append(")\n")
                        .append("Altitude: ")
                        .append(altitudeFeet > 0 ? String.format("%.0f ft", altitudeFeet) : "Unknown")
                        .append("\n")
                        .append("Speed: ")
                        .append(speedKmh > 0 ? String.format("%.0f km/h", speedKmh) : "Unknown")
                        .append("\n")
                        .append("Last update: ").append(secondsAgo).append(" seconds ago\n")
                        .append("-----------------------------------------------------\n");
            }
        }

        viewModel.setDisplayText(builder.toString());

        view.showFromViewModel(viewModel);
    }

    @Override
    public void presentInvalidPrefix() {
        viewModel.setDisplayText(
                "Invalid input. Please enter a valid ICAO prefix (e.g., AAL, DAL, UAL, ACA)."
        );

        view.showFromViewModel(viewModel);
    }
}
