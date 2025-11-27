package view;

import interface_adapter.SearchFlightDetails.SearchFlightDetailsController;
import interface_adapter.SearchFlightDetails.SearchFlightDetailsViewModel;
import javax.swing.*;
import java.awt.*;

public class SearchFlightDetailsScreen {

    private SearchFlightDetailsController controller;
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

        searchButton.addActionListener(e -> {
            String callSign = callSignField.getText().trim();

            resultArea.setText("Loading data... please wait...");

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    controller.executeSearch(callSign);
                    return null;
                }
                @Override
                protected void done() {
                    resultArea.setText(viewModel.getDisplayText());
                }
            };
            worker.execute();
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(callSignField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (onClose != null) onClose.run();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void setController(SearchFlightDetailsController controller) {
        this.controller = controller;
    }
}
