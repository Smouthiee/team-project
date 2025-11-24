package view;

import interface_adapter.SearchFlightDetails.SearchFlightDetailsController;
import use_case.SearchFlightDetails.SearchFlightDetailsOutputData;

import javax.swing.*;
import java.awt.*;

public class SearchFlightDetailsScreen {

    private final SearchFlightDetailsController controller;
    private JTextArea resultArea;

    public SearchFlightDetailsScreen(SearchFlightDetailsController controller) {
        this.controller = controller;
    }

    public void display(Runnable onClose) {
        JFrame frame = new JFrame("Search Flight Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter Flight Number (e.g. NHXXXX, ACXXXX, SQXXXX):");
        JTextField FlightNumber = new JTextField();
        JButton searchButton = new JButton("Search");

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        searchButton.addActionListener(e -> {
            String flightNumber = FlightNumber.getText().trim();
            controller.executeSearch(flightNumber);
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(FlightNumber, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

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

    public void showFlightDetails(SearchFlightDetailsOutputData data) {
        StringBuilder builder = new StringBuilder();

        if ("Flight not found".equals(data.getStatus())) {
            builder.append("No flight found!\n");
        } else {
            builder.append("Flight Number: ").append(data.getFlightNumber()).append("\n")
                    .append("Departure Airport: ").append(data.getDepartureAirport()).append("\n")
                    .append("Arrival Airport: ").append(data.getArrivalAirport()).append("\n")
                    .append("Departure Time: ").append(data.getDepartureTime()).append("\n")
                    .append("Arrival Time: ").append(data.getArrivalTime()).append("\n")
                    .append("Status: ").append(data.getStatus()).append("\n");
        }

        resultArea.setText(builder.toString());
    }
}
