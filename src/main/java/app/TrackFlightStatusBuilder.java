package app;

import data_access.TrackFlightStatusDataAccess;
import interface_adapter.TrackFlightStatus.TrackFlightStatusController;
import interface_adapter.TrackFlightStatus.TrackFlightStatusPresenter;
import interface_adapter.TrackFlightStatus.TrackFlightStatusViewModel;
import data_access.TrackFlightStatusDataAccessInterface;
import use_case.TrackFlightStatus.TrackFlightStatusInputBoundary;
import use_case.TrackFlightStatus.TrackFlightStatusInteractor;
import use_case.TrackFlightStatus.TrackFlightStatusOutputBoundary;
import view.TrackFlightStatusView;

public class TrackFlightStatusBuilder {

    public static TrackFlightStatusView buildTrackFlightStatusFeature() {

        TrackFlightStatusViewModel viewModel = new TrackFlightStatusViewModel();

        TrackFlightStatusDataAccessInterface dataAccess =
                new TrackFlightStatusDataAccess();

        TrackFlightStatusOutputBoundary presenter =
                new TrackFlightStatusPresenter(viewModel);

        TrackFlightStatusInputBoundary interactor =
                new TrackFlightStatusInteractor(dataAccess, presenter);

        TrackFlightStatusController controller =
                new TrackFlightStatusController(interactor);

        return new TrackFlightStatusView(controller, viewModel);
    }
}
