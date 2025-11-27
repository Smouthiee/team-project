package view;

import interface_adapter.SearchFlightDetails.SearchFlightDetailsController;
import interface_adapter.SearchFlightDetails.SearchFlightDetailsViewModel;
import javax.swing.*;
import java.awt.*;

public class SearchFlightDetailsScreen {

    private final SearchFlightDetailsController controller;
    private final SearchFlightDetailsViewModel viewModel;

    private JTextArea resultArea;

    public SearchFlightDetailsScreen(SearchFlightDetailsController controller,
                                     SearchFlightDetailsViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
    }

    public void display(Runnable onClose) {

        JFrame frame = new JFrame("Search Flight Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter Flight CallSign (e.g. ACA850, SQ25, ANA12):");
        JTextField callSignField = new JTextField();
        JButton searchButton = new JButton("Search");

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        // When button clicked → ask controller to perform search
        searchButton.addActionListener(e -> {
            String callSign = callSignField.getText().trim();
            controller.executeSearch(callSign);

            // After presenter updates the ViewModel → refresh view
            resultArea.setText(viewModel.getDisplayText());
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(callSignField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Run onClose (optional callback)
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (onClose != null) onClose.run();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
