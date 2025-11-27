package view;


import data_access.ViewActiveFlightDataAccess;
import interface_adapter.SearchFlightDetails.SearchFlightDetailsController;
import interface_adapter.SearchFlightDetails.SearchFlightDetailsPresenter;
import interface_adapter.SearchFlightDetails.SearchFlightDetailsViewModel;
import interface_adapter.ViewActiveFlights.ViewActiveFlightsViewModel;
import use_case.SearchFlightDetails.SearchFlightDetailInputBoundary;
import use_case.SearchFlightDetails.SearchFlightDetailsInteractor;
import use_case.SearchFlightDetails.SearchFlightDetailsOutputBoundary;
import use_case.ViewActiveFlights.ViewActiveFlightsDataAccessInterface;
import interface_adapter.ViewActiveFlights.ViewActiveFlightsController;
import interface_adapter.ViewActiveFlights.ViewActiveFlightsPresenter;
import use_case.ViewActiveFlights.ViewActiveFlightsInputBoundary;
import use_case.ViewActiveFlights.ViewActiveFlightsInteractor;
import use_case.ViewActiveFlights.ViewActiveFlightsOutputBoundary;


import interface_adapter.FavouriteFlight.FavouriteFlightController;
import interface_adapter.FavouriteFlight.FavouriteFlightPresenter;
import use_case.FavouriteFlight.FavouriteFlightInputBoundary;
import use_case.FavouriteFlight.FavouriteFlightInteractor;
import use_case.FavouriteFlight.FavouriteFlightOutputBoundary;
import view.FavouriteFlightView;


import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class MainMenu extends JFrame {

    public MainMenu() {

        setTitle("GlobalFlightApp — Main Menu");
        setSize(1100, 600);
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
        add(leftPanel, BorderLayout.WEST);

        // Right panel (use case 5， 6 + Exit)
        JPanel rightPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton activeFlightsButton = new JButton("🕓 View Active Flights of an Airline");
        JButton trackFlightButton = new JButton("📡 Track Flight Status");
        JButton exitButton = new JButton("❌ Exit");

        rightPanel.add(activeFlightsButton);
        rightPanel.add(trackFlightButton);
        rightPanel.add(exitButton);
        add(rightPanel, BorderLayout.EAST);

        // Unify the size of buttons on MainMenu.
        unifyButtonSize(searchFlightButton, favouriteButton,  searchByAirportButton,
                activeFlightsButton, trackFlightButton, exitButton);

        // Center panel for image
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/MainMenu.jpg")));

            Image scaled = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaled));

            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            add(imageLabel, BorderLayout.CENTER);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Image could not be loaded. Check file path.");
        }

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Button Actions
        // Use Case 1: Search Flight Details
        searchFlightButton.addActionListener(e -> {
            this.setVisible(false);

            SearchFlightDetailsDataAccess dataAccess = new SearchFlightDetailsDataAccess();
            SearchFlightDetailsViewModel viewModel = new SearchFlightDetailsViewModel();
            SearchFlightDetailsScreen view = new SearchFlightDetailsScreen(null, viewModel);
            SearchFlightDetailsOutputBoundary presenter = new SearchFlightDetailsPresenter(viewModel);
            SearchFlightDetailInputBoundary interactor = new SearchFlightDetailsInteractor(dataAccess, presenter);
            SearchFlightDetailsController controller = new SearchFlightDetailsController(interactor);

            view.setController(controller);
            view.display(() -> this.setVisible(true));
        });

        // Use Case 2: Favourite a Flight
        favouriteButton.addActionListener(e -> {
            this.setVisible(false);

            // Create the View (without controller initially)
            FavouriteFlightView view = new FavouriteFlightView(null);

            // Create the Presenter (needs the view)
            FavouriteFlightOutputBoundary presenter =
                    new FavouriteFlightPresenter(view);

            // Create the Interactor (needs the presenter)
            FavouriteFlightInputBoundary interactor =
                    new FavouriteFlightInteractor(presenter);

            // Create the Controller (needs the interactor)
            FavouriteFlightController controller =
                    new FavouriteFlightController(interactor);

            // Give the view the controller
            view.setController(controller);

            // Display the view
            view.display(() -> this.setVisible(true));
        });

        // Use Case 4: Search Flights by Airports
        searchByAirportButton.addActionListener(e -> JOptionPane.showMessageDialog
                (this, "Coming soon!"));
        
        // ----- Once complete:
        //searchByAirportButton.addActionListener(e -> {
        //    this.setVisible(false);
        //    AirportDataRouteSearchView view = new AirportDataRouteSearchView(controller);
        //    view.display(() -> this.setVisible(true));


        // Use case 5: View Active Flights of an Airline
        activeFlightsButton.addActionListener(e -> {
            this.setVisible(false);

            ViewActiveFlightsDataAccessInterface dataAccess = new ViewActiveFlightDataAccess();
            ViewActiveFlightsViewModel viewModel = new ViewActiveFlightsViewModel();
            ViewActiveFlightsView view = new ViewActiveFlightsView(null);
            ViewActiveFlightsOutputBoundary presenter = new ViewActiveFlightsPresenter(view, viewModel);
            ViewActiveFlightsInputBoundary interactor = new ViewActiveFlightsInteractor(dataAccess, presenter);
            ViewActiveFlightsController controller = new ViewActiveFlightsController(interactor);

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

    private void unifyButtonSize(JButton... buttons) {
        int maxWidth = 0;
        int maxHeight = 0;

        for (JButton b : buttons) {
            Dimension size = b.getPreferredSize();
            maxWidth = Math.max(maxWidth, size.width);
            maxHeight = Math.max(maxHeight, size.height);
        }

        Dimension unified = new Dimension(maxWidth + 20, maxHeight + 20);

        for (JButton b : buttons) {
            b.setPreferredSize(unified);
        }
    }
}
