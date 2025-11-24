package view;

import interface_adapter.FavouriteFlight.FavouriteFlightController;
import use_case.FavouriteFlight.FavouriteFlightInteractor;

import javax.swing.*;
import java.awt.*;
import java.util.Set;


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
        frame.setSize(550, 450);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Manage Favourite Flights", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel flightLabel = new JLabel("Flight Number:");
        flightLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextField flightField = new JTextField();
        flightField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton favouriteButton = new JButton("❤️ Add to Favourites");
        favouriteButton.setFont(new Font("Arial", Font.BOLD, 14));

        JButton viewAllButton = new JButton("📋 View All Favourites");
        viewAllButton.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton backButton = new JButton("← Back to Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));

        inputPanel.add(flightLabel);
        inputPanel.add(flightField);
        inputPanel.add(favouriteButton);
        inputPanel.add(new JLabel(""));
        inputPanel.add(viewAllButton);
        inputPanel.add(backButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Status / Favourites List"));


        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        favouriteButton.addActionListener(e -> {
            String flightNumber = flightField.getText().trim();

            if (controller != null) {
                // Clear previous results and show processing
                resultArea.setText("Processing...");
                resultArea.setForeground(Color.BLACK);

                controller.favouriteFlight(flightNumber);
            } else {
                resultArea.setText("Error: Controller not initialized.");
                resultArea.setForeground(Color.RED);
            }
        });

        viewAllButton.addActionListener(e -> {
            displayAllFavourites();
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
        resultArea.setForeground(new Color(0, 128, 0)); // Green
    }

    public void showError() {
        resultArea.setText("✗ ERROR\n\nFlight number is invalid or already in favourites.\n" +
                "Please check and try again.");
        resultArea.setForeground(new Color(180, 0, 0)); // Red
    }

    private void displayAllFavourites() {
        Set<String> favourites = FavouriteFlightInteractor.getAllFavourites();

        if (favourites == null || favourites.isEmpty()) {
            resultArea.setText("📋 MY FAVOURITES\n\n" +
                    "No favourites yet!\n\n" +
                    "Add flights using the form above.");
            resultArea.setForeground(Color.GRAY);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("📋 MY FAVOURITES\n\n");
            sb.append("You have ").append(favourites.size())
                    .append(" favourite flight(s):\n\n");

            int count = 1;
            for (String flight : favourites) {
                sb.append(count++).append(". ").append(flight).append("\n");
            }

            resultArea.setText(sb.toString());
            resultArea.setForeground(new Color(0, 0, 139)); // Dark blue
        }
    }


}
