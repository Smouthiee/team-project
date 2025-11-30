package view;

import interface_adapter.ViewActiveFlights.ViewActiveFlightsController;
import interface_adapter.ViewActiveFlights.ViewActiveFlightsViewModel;

import javax.swing.*;
import java.awt.*;


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
        frame.setSize(500, 600);
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
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

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

    public void showFromViewModel(ViewActiveFlightsViewModel viewModel) {
        resultArea.setText(viewModel.getDisplayText());
    }
}
