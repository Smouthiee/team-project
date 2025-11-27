package use_case.ViewActiveFlights;

import entity.AirlineFlight;
import java.util.List;
import java.util.Set;


public class ViewActiveFlightsInteractor implements ViewActiveFlightsInputBoundary {

    private final ViewActiveFlightsDataAccessInterface flightDataAccess;
    private final ViewActiveFlightsOutputBoundary presenter;
    /**
     * This set contains some ICAO prefixes of the airlines that are famous in the world and known to appear in the
     * OpenSky Network API.
     * OpenSky does NOT include all global ICAO codes, only airlines with active ADS-B transponders frequently
     * seen in their live data.
     * See README for further explanation for this set.
     */
    private static final Set<String> VALID_ICAO_PREFIXES = Set.of(
            "AAL","DAL","UAL","NKS","ASA","JBU","SWA","FFT","ASH","SKW","ENY","RPA","JIA", "GJS","PDT","EDV","ATN",
            "UPS","FDX","ACA","ROU","WJA","CJT","SWG","BAW","EZY","RYR","DLH","AFR","KLM", "VLG","IBE","SWR","SAS",
            "FIN","TAP","TUI","WZZ","TOM","EXS","AUR","BEE","ANA","JAL","CES","CCA","CSN", "CPA","HKE","UAE","ETD",
            "QTR","QFA","JST","ANZ");

    public ViewActiveFlightsInteractor(ViewActiveFlightsDataAccessInterface flightDataAccess,
                                       ViewActiveFlightsOutputBoundary presenter) {
        this.flightDataAccess = flightDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewActiveFlightsInputData inputData) {

        String rawPrefix = inputData.getAirlinePrefix();

        if (rawPrefix == null || rawPrefix.trim().isEmpty()) {
            presenter.presentInvalidPrefix();
            return;
        }

        String airlinePrefix = rawPrefix.trim().toUpperCase();

        if (!VALID_ICAO_PREFIXES.contains(airlinePrefix)) {
            presenter.presentInvalidPrefix();
            return;
        }

        List<AirlineFlight> flights = flightDataAccess.getActiveFlightsByAirline(airlinePrefix);

        ViewActiveFlightsOutputData outputData = new ViewActiveFlightsOutputData(flights);
        presenter.present(outputData);
    }
}
