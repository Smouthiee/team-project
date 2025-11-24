package use_case.TrackFlightStatus;
import data_access.TrackFlightStatusDataAccessInterface;
import entity.FlightStatus;

public class TrackFlightStatusInteractor implements TrackFlightStatusInputBoundary {
    private final TrackFlightStatusDataAccessInterface flightStatusDataAccess;
    private final TrackFlightStatusOutputBoundary presenter;

    public TrackFlightStatusInteractor(TrackFlightStatusDataAccessInterface flightStatusDataAccess,
                                       TrackFlightStatusOutputBoundary presenter) {
        this.flightStatusDataAccess = flightStatusDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(TrackFlightStatusInputData inputData) {
        String flightNumber = inputData.getFlightNumber();

        FlightStatus status = flightStatusDataAccess.getStatusByFlightNumber(flightNumber);
        String message;
        String eta = null;
        double lat = 0, lon = 0, alt = 0, speed = 0;

        if (status == null) {
            message = String.format(
                    "Live status unavailable for flight %s.",
                    flightNumber
            );
        } else {
            eta = status.getEstimatedArrivalTime();
            lat = status.getLatitude();
            lon = status.getLongitude();
            alt = status.getAltitude();
            speed = status.getSpeed();

        }
        message = String.format(
                "Flight %s live status:\n" +
                        "ETA: %s\n" +
                        "Position: (%.4f, %.4f)\n" +
                        "Altitude: %.0f\n" +
                        "Speed: %.0f",
                status.getFlightNumber(),
                eta,
                lat, lon,
                alt, speed
        );

    TrackFlightStatusOutputData outputData =
            new TrackFlightStatusOutputData(
                    flightNumber,
                    eta,
                    lat, lon, alt, speed,
                    message
            );

    // 4. 交给 presenter
        presenter.present(outputData);
    }
}
