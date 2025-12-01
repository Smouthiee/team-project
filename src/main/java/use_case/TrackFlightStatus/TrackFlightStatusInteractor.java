package use_case.TrackFlightStatus;
import data_access.TrackFlightStatusDataAccessInterface;
import entity.FlightStatus;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
        double lat = 0, lon = 0, alt = 0, speed = 0;
        int lastUpdate = 0;

        if (status == null) {
            message = String.format(
                    "Live status unavailable for flight %s.",
                    flightNumber
            );
        } else {
            lat = status.getLatitude();
            lon = status.getLongitude();
            alt = status.getAltitude();
            speed = status.getSpeed();
            lastUpdate = status.getLastUpdate();

            String lastUpdateText;
            if (lastUpdate > 0) {
                Instant instant = Instant.ofEpochSecond(lastUpdate);
                ZonedDateTime localTime = instant.atZone(ZoneId.systemDefault());

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                lastUpdateText = localTime.format(formatter);
            } else {
                lastUpdateText = "unknown";
            }

            message = String.format(
                    "Live status for flight %s (last update: %s)",
                    status.getFlightNumber(),
                    lastUpdateText
            );
        }

    TrackFlightStatusOutputData outputData =
            new TrackFlightStatusOutputData(
                    flightNumber,
                    lat, lon, alt, speed, lastUpdate,
                    message
            );

        presenter.present(outputData);
    }
}
