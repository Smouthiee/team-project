package view;

import interface_adapter.TrackFlightStatus.TrackFlightStatusController;
import interface_adapter.TrackFlightStatus.TrackFlightStatusViewModel;

import javax.swing.*;
import java.awt.*;

public class TrackFlightStatusView extends JFrame {

    private final TrackFlightStatusController controller;
    private final TrackFlightStatusViewModel viewModel;

    private JTextField flightNumberField;
    private JButton trackButton;
    private JLabel messageLabel;
    private JLabel latLabel;
    private JLabel lonLabel;
    private JLabel altLabel;
    private JLabel speedLabel;

    public TrackFlightStatusView(TrackFlightStatusController controller,
                                 TrackFlightStatusViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        setTitle("📡 Track Live Flight Status");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initComponents();
    }

    private void initComponents() {
        // 顶部标题
        JLabel title = new JLabel("Track Live Flight Status", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // 中间输入 + 按钮
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(new JLabel("Flight number (call sign):"));

        flightNumberField = new JTextField(10);
        inputPanel.add(flightNumberField);

        trackButton = new JButton("Track");
        inputPanel.add(trackButton);

        add(inputPanel, BorderLayout.CENTER);

        // 下方结果区域
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        messageLabel = new JLabel("Please enter a flight number and click Track.");
        latLabel = new JLabel("Latitude: -");
        lonLabel = new JLabel("Longitude: -");
        altLabel = new JLabel("Altitude: -");
        speedLabel = new JLabel("Speed: -");

        resultPanel.add(messageLabel);
        resultPanel.add(Box.createVerticalStrut(5));
        resultPanel.add(latLabel);
        resultPanel.add(lonLabel);
        resultPanel.add(altLabel);
        resultPanel.add(speedLabel);

        add(resultPanel, BorderLayout.SOUTH);

        // 按钮事件
        trackButton.addActionListener(e -> onTrackClicked());
    }

    private void onTrackClicked() {
        String flightNumber = flightNumberField.getText().trim();
        if (flightNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a flight number.",
                    "Input error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 调用 use case
        controller.track(flightNumber);

        // 用 viewModel 更新界面
        refreshFromViewModel();
    }

    private void refreshFromViewModel() {
        messageLabel.setText(viewModel.getMessage());
        latLabel.setText("Latitude: " + viewModel.getLatitude());
        lonLabel.setText("Longitude: " + viewModel.getLongitude());
        altLabel.setText("Altitude: " + viewModel.getAltitude());
        speedLabel.setText("Speed: " + viewModel.getSpeed());
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
