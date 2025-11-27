package interface_adapter.TrackFlightStatus;

import use_case.TrackFlightStatus.TrackFlightStatusOutputBoundary;
import use_case.TrackFlightStatus.TrackFlightStatusOutputData;

public class TrackFlightStatusPresenter implements TrackFlightStatusOutputBoundary {

    private final TrackFlightStatusViewModel viewModel;

    public TrackFlightStatusPresenter(TrackFlightStatusViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(TrackFlightStatusOutputData outputData) {

        viewModel.setFlightNumber(outputData.getFlightNumber());
        viewModel.setLatitude(outputData.getLatitude());
        viewModel.setLongitude(outputData.getLongitude());
        viewModel.setAltitude(outputData.getAltitude());
        viewModel.setSpeed(outputData.getSpeed());

        viewModel.setMessage(outputData.getMessage());
    }

    public TrackFlightStatusViewModel getViewModel() {
        return viewModel;
    }
}
