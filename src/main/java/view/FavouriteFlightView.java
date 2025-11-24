package view;

import interface_adapter.FavouriteFlight.FavouriteFlightController;

import javax.swing.*;
import java.awt.*;

public class FavouriteFlightView {

    private FavouriteFlightController controller;
    private JTextArea resultArea;

    public FavouriteFlightView(FavouriteFlightController controller) {
        this.controller = controller;
    }

    public void setController(FavouriteFlightController controller) {
        this.controller = controller;
    }

    public void display(Runnable onClose) {
        JFrame frame = new JFrame("Favourite a Flight");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(550, 400);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Add Flight to Favourites", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel flightLabel = new JLabel("Flight Number:");
        flightLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextField flightField = new JTextField();
        flightField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton favouriteButton = new JButton("❤️ Add to Favourites");
        favouriteButton.setFont(new Font("Arial", Font.BOLD, 14));

        JButton backButton = new JButton("← Back to Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));

        inputPanel.add(flightLabel);
        inputPanel.add(flightField);
        inputPanel.add(favouriteButton);
        inputPanel.add(backButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Status"));

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        favouriteButton.addActionListener(e -> {
            String flightNumber = flightField.getText().trim();

            if (controller != null) {
                // Clear previous results and show processing
                resultArea.setText("Processing...");
                resultArea.setForeground(Color.BLACK);

                // Call the controller
                controller.favouriteFlight(flightNumber);
            } else {
                resultArea.setText("Error: Controller not initialized.");
                resultArea.setForeground(Color.RED);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            if (onClose != null) {
                onClose.run();
            }
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

    public void showSuccess(String flightNumber) {
        resultArea.setText("✓ SUCCESS!\n\nFlight " + flightNumber +
                " has been added to your favourites.");
        resultArea.setForeground(new Color(0, 128, 0));
    }

    public void showError() {
        resultArea.setText("✗ ERROR\n\nFlight number is invalid or already in favourites.\n" +
                "Please check and try again.");
        resultArea.setForeground(new Color(180, 0, 0));
    }
}
