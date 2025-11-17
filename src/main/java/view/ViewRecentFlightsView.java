package view;

import interface_adapter.ViewRecentFlights.ViewRecentFlightsController;
import entity.AirlineFlight;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.List;

public class ViewRecentFlightsView {
    private ViewRecentFlightsController controller;

    public ViewRecentFlightsView(ViewRecentFlightsController controller) {
        this.controller = controller;
    }

    public void setController(ViewRecentFlightsController controller) {
        this.controller = controller;
    }

    public void display(Runnable onClose) {
        JFrame frame = new JFrame("View Recent Airline Flights");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter airline name:");
        JTextField airlineField = new JTextField();
        JButton searchButton = new JButton("Search");
        JTextArea resultArea = new JTextArea();
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
            if (controller != null) {
                controller.viewRecentFlights(airline);
            }
            resultArea.setText("Simulating: showing flights for airline '" + airline + "'...");
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
        if (flights == null || flights.isEmpty()) {
            System.out.println("No recent flights found for this airline.");
        } else {
            System.out.println("\nRecent Flights:");
            for (AirlineFlight flight : flights) {
                System.out.println("- " + flight.getAirline() + " " + flight.getFlightNumber()
                        + " | From " + flight.getDepartureAirport()
                        + " → " + flight.getArrivalAirport()
                        + " | Status: " + flight.getStatus());
            }
        }
    }
}
