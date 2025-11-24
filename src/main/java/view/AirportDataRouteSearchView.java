package view;

import entity.Airport;
import interface_adapter.AirportDataRS.AirportDataRSController;
import use_case.AirportDataRouteSearch.AirportDataRouteSearchOutputData;

import javax.swing.*;
import java.awt.*;

public class AirportDataRouteSearchView {
    private final AirportDataRSController controller;
    private JTextArea resultArea;

    public AirportDataRouteSearchView(AirportDataRSController controller){
        this.controller = controller;
    }

    public void display(Runnable onClose) {
        JFrame frame = new JFrame("Search Route by Airport");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout());

        // ----- Input panel: From / To IATA codes -----
        JLabel fromLabel = new JLabel("From (IATA):");
        JTextField fromField = new JTextField();
        JLabel toLabel = new JLabel("To (IATA):");
        JTextField toField = new JTextField();
        JButton searchButton = new JButton("Search");

        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        inputPanel.add(fromLabel);
        inputPanel.add(fromField);
        inputPanel.add(new JLabel());   // spacer
        inputPanel.add(toLabel);
        inputPanel.add(toField);
        inputPanel.add(searchButton);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ----- Result area -----
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // ----- Button action -----
        searchButton.addActionListener(e -> {
            String fromCode = fromField.getText().trim();
            String toCode = toField.getText().trim();

            if (fromCode.isEmpty() || toCode.isEmpty()) {
                resultArea.setText("Please enter both From and To IATA codes.");
                return;
            }

            if (controller == null) {
                resultArea.setText("Controller not set. Unable to perform search.");
                return;
            }
            resultArea.setText("Searching route from " + fromCode + " to " + toCode + "...\n");

            // Delegate to controller / interactor
            search(fromCode, toCode);
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

    public void search(String takeoffCode, String landingCode) {
        controller.searchRoute(takeoffCode, landingCode);
    }
    public void showResult(AirportDataRouteSearchOutputData data) {
        if (resultArea == null) {
            // Fallback if someone somehow calls this before display()
            System.out.println(data.getMessage());
            Airport takeOff = data.getTakeOffAirport();
            Airport landing = data.getLandingAirport();
            if (takeOff != null) {
                System.out.println("Takeoff airport: " + takeOff);
            }
            if (landing != null) {
                System.out.println("Landing airport: " + landing);
            }
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(data.getMessage()).append("\n\n");

        Airport takeOff = data.getTakeOffAirport();
        Airport landing = data.getLandingAirport();

        if (takeOff != null) {
            builder.append("Takeoff airport:\n")
                    .append("  IATA: ").append(takeOff.getIATA()).append("\n")
                    .append("  City: ").append(takeOff.getCity()).append("\n")
                    .append("  Country: ").append(takeOff.getCountry()).append("\n\n");
        }

        if (landing != null) {
            builder.append("Landing airport:\n")
                    .append("  IATA: ").append(landing.getIATA()).append("\n")
                    .append("  City: ").append(landing.getCity()).append("\n")
                    .append("  Country: ").append(landing.getCountry()).append("\n");
        }

        resultArea.setText(builder.toString());
    }
}
