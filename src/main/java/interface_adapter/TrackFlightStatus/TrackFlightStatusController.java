package interface_adapter.TrackFlightStatus;

import use_case.TrackFlightStatus.TrackFlightStatusInputBoundary;
import use_case.TrackFlightStatus.TrackFlightStatusInputData;

public class TrackFlightStatusController {

    private final TrackFlightStatusInputBoundary interactor;

    public TrackFlightStatusController(TrackFlightStatusInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void track(String flightNumber) {
        TrackFlightStatusInputData inputData =
                new TrackFlightStatusInputData(flightNumber);
        interactor.execute(inputData);
    }
}
