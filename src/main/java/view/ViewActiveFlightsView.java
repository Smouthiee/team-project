package view;

import interface_adapter.ViewActiveFlights.ViewActiveFlightsController;
import entity.AirlineFlight;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ViewActiveFlightsView {

    private ViewActiveFlightsController controller;
    private JTextArea resultArea;

    public ViewActiveFlightsView(ViewActiveFlightsController controller) {
        this.controller = controller;
    }

    public void setController(ViewActiveFlightsController controller) {
        this.controller = controller;
    }

    public void display(Runnable onClose) {
        JFrame frame = new JFrame("View Active Airline Flights");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter airline ICAO prefix (e.g. AAL, DAL, ACA):");
        JTextField airlineField = new JTextField();
        JButton searchButton = new JButton("Search");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(airlineField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {

            String airline = airlineField.getText();

            resultArea.setText("Loading data... please wait...");

            SwingWorker<Void, Void> worker = new SwingWorker<>() {

                @Override
                protected Void doInBackground() {
                    controller.viewActiveFlights(airline);
                    return null;
                }
            };

            worker.execute();
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (onClose != null) {
                    onClose.run();
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showFlights(List<AirlineFlight> flights) {
        StringBuilder builder = new StringBuilder();

        if (flights == null || flights.isEmpty()) {
            builder.append("No active flights found for this airline.");
        } else {
            builder.append("Active Flights\n");
            builder.append("------------------------------\n\n");
            for (AirlineFlight f : flights) {

                double altitudeFeet = f.getAltitude() * 3.28084;
                double speedKmh = f.getSpeed() * 3.6;

                builder.append("Flight: ").append(f.getFlightNumber()).append("\n")
                        .append("Location: (")
                        .append(String.format("%.2f", f.getLatitude()))
                        .append(", ")
                        .append(String.format("%.2f", f.getLongitude()))
                        .append(")\n")
                        .append("Altitude: ").append(altitudeFeet > 0 ?
                                String.format("%.0f ft", altitudeFeet) : "Unknown").append("\n")
                        .append("Speed: ").append(speedKmh > 0 ?
                                String.format("%.0f km/h", speedKmh) : "Unknown").append("\n")
                        .append("-----------------------------------------------------\n");
            }
        }
        resultArea.setText(builder.toString());
    }

    public void showInvalidPrefixMessage() {
        resultArea.setText("Invalid input. Please enter a valid ICAO prefix (e.g., AAL, DAL, UAL, ACA).");
    }
}
