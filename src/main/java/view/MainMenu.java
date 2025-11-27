package view;

import app.AppBuilder;
import app.TrackFlightStatusBuilder;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {

        setTitle("GlobalFlightApp — Main Menu");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Welcome to Global Flight", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Left panel (use case 1， 2， 4)
        JPanel leftPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton searchFlightButton = new JButton("🔍 Search Flight Details");
        JButton favouriteButton = new JButton("❤️ My Favourite Flights");
        JButton searchByAirportButton = new JButton("🛫 Search Flights by Airport");

        leftPanel.add(searchFlightButton);
        leftPanel.add(favouriteButton);
        leftPanel.add(searchByAirportButton);

        // Right panel (use case 5， 6 + Exit)
        JPanel rightPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton recentFlightsButton = new JButton("🕓 View Recent Airline Flights");
        JButton trackFlightButton = new JButton("📡 Track Live Flight Status");
        JButton exitButton = new JButton("❌ Exit");

        rightPanel.add(recentFlightsButton);
        rightPanel.add(trackFlightButton);
        rightPanel.add(exitButton);

        // Add buttons to panel
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);


        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Button Actions(temporary)

        //recentFlightsButton.addActionListener(e -> {
        //    ViewRecentFlightsView view = appBuilder.buildViewRecentFlightsFeature();
        //    view.display();
        //});


        searchFlightButton.addActionListener(e -> JOptionPane.showMessageDialog
                (this, "Coming soon!"));
        favouriteButton.addActionListener(e -> JOptionPane.showMessageDialog
                (this, "Coming soon!"));
        searchByAirportButton.addActionListener(e -> JOptionPane.showMessageDialog
                (this, "Coming soon!"));
        recentFlightsButton.addActionListener(e -> JOptionPane.showMessageDialog
                (this, "Coming soon!"));
        trackFlightButton.addActionListener(e -> {
            // 用 AppBuilder 组装好整条链，拿到 View
            view.TrackFlightStatusView view =
                    TrackFlightStatusBuilder.   buildTrackFlightStatusFeature();
            view.display();
        });

        // Exit
        exitButton.addActionListener(e -> System.exit(0));
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
