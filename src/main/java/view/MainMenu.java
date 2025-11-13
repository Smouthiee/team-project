package view;

import data_access.ViewRecentFlightDataAccess;
import data_access.ViewRecentFlightsDataAccessInterface;
import interface_adapter.ViewRecentFlights.ViewRecentFlightsController;
import interface_adapter.ViewRecentFlights.ViewRecentFlightsPresenter;
import use_case.ViewRecentFlights.ViewRecentFlightsInputBoundary;
import use_case.ViewRecentFlights.ViewRecentFlightsInteractor;
import use_case.ViewRecentFlights.ViewRecentFlightsOutputBoundary;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {

        setTitle("GlobalFlightApp — Main Menu");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Welcome to Global Flight", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));
        add(title, BorderLayout.NORTH);

        // Left panel (use case 1， 2， 4)
        JPanel leftPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton searchFlightButton = new JButton("🔍 Search Flight Details");
        JButton favouriteButton = new JButton("❤️ Favourite a Flight");
        JButton searchByAirportButton = new JButton("🛫 Search Flights by Airport");

        leftPanel.add(searchFlightButton);
        leftPanel.add(favouriteButton);
        leftPanel.add(searchByAirportButton);

        // Right panel (use case 5， 6 + Exit)
        JPanel rightPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton recentFlightsButton = new JButton("🕓 View Recent Flights of an Airline");
        JButton trackFlightButton = new JButton("📡 Track Flight Status");
        JButton exitButton = new JButton("❌ Exit");

        rightPanel.add(recentFlightsButton);
        rightPanel.add(trackFlightButton);
        rightPanel.add(exitButton);

        // Add buttons to panel
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Button Actions
        // Use Case 1: Search Flight Details
        searchFlightButton.addActionListener(e -> JOptionPane.showMessageDialog
                (this, "Coming soon!"));

        // Use Case 2: Favourite a Flight
        favouriteButton.addActionListener(e -> JOptionPane.showMessageDialog
                (this, "Coming soon!"));

        // Use Case 4: Search Flights by Airports
        searchByAirportButton.addActionListener(e -> JOptionPane.showMessageDialog
                (this, "Coming soon!"));

        // Use case 5: View recent flights
        recentFlightsButton.addActionListener(e -> {
            this.setVisible(false);

            ViewRecentFlightsDataAccessInterface dataAccess = new ViewRecentFlightDataAccess();
            ViewRecentFlightsView view = new ViewRecentFlightsView(null);
            ViewRecentFlightsOutputBoundary presenter = new ViewRecentFlightsPresenter(view);
            ViewRecentFlightsInputBoundary interactor = new ViewRecentFlightsInteractor(dataAccess, presenter);
            ViewRecentFlightsController controller = new ViewRecentFlightsController(interactor);

            view.setController(controller);
            view.display(() -> this.setVisible(true));
        });

        // Use Case 6: Track Flight Status
        trackFlightButton.addActionListener(e -> JOptionPane.showMessageDialog
                (this, "Coming soon!"));

        // Exit
        exitButton.addActionListener(e -> System.exit(0));
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
