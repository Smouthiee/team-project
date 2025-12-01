package use_case.AirportDataRouteSearch;

import entity.Airport;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AirportDataRouteSearchInteractorTest {

    /**
     * Simple presenter stub: just stores the last output it receives.
     */
    private static class TestPresenter implements AirportDataRouteSearchOutputBoundary {
        AirportDataRouteSearchOutputData lastOutput;

        @Override
        public void present(AirportDataRouteSearchOutputData outputData) {
            this.lastOutput = outputData;
        }
    }

    /**
     * Simple in-memory DAO: stores airports by IATA in a map.
     */
    private static class MapAirportDAO implements AirportDataAccessInterface {
        private final Map<String, Airport> airports = new HashMap<>();

        void put(Airport airport) {
            airports.put(airport.getIATA(), airport);
        }

        @Override
        public Airport getAirportByCode(String iataCode) {
            if (iataCode == null) return null;
            return airports.get(iataCode.toUpperCase());
        }
    }

    @Test
    void bothAirportsFound_andDirectConnection() {
        // --- Arrange ---
        Airport takeOff = new Airport("AAA", "Toronto", "Canada");
        Airport landing = new Airport("BBB", "Hong Kong", "China");

        // Connect the two airports (undirected connection)
        takeOff.addconnectTo(landing);

        MapAirportDAO dao = new MapAirportDAO();
        dao.put(takeOff);
        dao.put(landing);

        TestPresenter presenter = new TestPresenter();
        AirportDataRouteSearchInteractor interactor =
                new AirportDataRouteSearchInteractor(dao, presenter);

        AirportDataRouteSearchInputData input =
                new AirportDataRouteSearchInputData("AAA", "BBB");

        // --- Act ---
        interactor.execute(input);

        // --- Assert ---
        assertNotNull(presenter.lastOutput, "Presenter should have been called");

        AirportDataRouteSearchOutputData out = presenter.lastOutput;

        assertSame(takeOff, out.getTakeOffAirport());
        assertSame(landing, out.getLandingAirport());
        assertTrue(out.areBothFound(), "bothFound should be true when both airports exist");

        // Exact string from your interactor
        assertEquals("Air ports has direct connect flight", out.getMessage());
    }

    @Test
    void bothAirportsFound_butNoDirectConnection() {
        // --- Arrange ---
        Airport takeOff = new Airport("AAA", "Toronto", "Canada");
        Airport landing = new Airport("BBB", "Hong Kong", "China");

        // NOTE: no addconnectTo call here → not connected
        MapAirportDAO dao = new MapAirportDAO();
        dao.put(takeOff);
        dao.put(landing);

        TestPresenter presenter = new TestPresenter();
        AirportDataRouteSearchInteractor interactor =
                new AirportDataRouteSearchInteractor(dao, presenter);

        AirportDataRouteSearchInputData input =
                new AirportDataRouteSearchInputData("AAA", "BBB");

        // --- Act ---
        interactor.execute(input);

        // --- Assert ---
        assertNotNull(presenter.lastOutput, "Presenter should have been called");

        AirportDataRouteSearchOutputData out = presenter.lastOutput;

        assertSame(takeOff, out.getTakeOffAirport());
        assertSame(landing, out.getLandingAirport());
        assertTrue(out.areBothFound(), "bothFound should be true when both airports exist");

        assertEquals(
                "Airports has no direct flight, force-directed graphs search algo not yet implemented.",
                out.getMessage()
        );
    }

    @Test
    void neitherAirportFound() {
        // --- Arrange ---
        MapAirportDAO dao = new MapAirportDAO(); // empty → nothing found

        TestPresenter presenter = new TestPresenter();
        AirportDataRouteSearchInteractor interactor =
                new AirportDataRouteSearchInteractor(dao, presenter);

        String takeoffCode = "AAA";
        String landingCode = "BBB";
        AirportDataRouteSearchInputData input =
                new AirportDataRouteSearchInputData(takeoffCode, landingCode);

        // --- Act ---
        interactor.execute(input);

        // --- Assert ---
        assertNotNull(presenter.lastOutput, "Presenter should have been called");

        AirportDataRouteSearchOutputData out = presenter.lastOutput;

        assertNull(out.getTakeOffAirport());
        assertNull(out.getLandingAirport());
        assertFalse(out.areBothFound(), "bothFound should be false when both airports missing");

        assertEquals(
                "Neither airport " + takeoffCode + " nor " + landingCode + " was found.",
                out.getMessage()
        );
    }

    @Test
    void onlyTakeoffMissing() {
        // --- Arrange ---
        Airport landing = new Airport("BBB", "Hong Kong", "China");

        MapAirportDAO dao = new MapAirportDAO();
        dao.put(landing); // only landing is present

        TestPresenter presenter = new TestPresenter();
        AirportDataRouteSearchInteractor interactor =
                new AirportDataRouteSearchInteractor(dao, presenter);

        String takeoffCode = "AAA";
        String landingCode = "BBB";
        AirportDataRouteSearchInputData input =
                new AirportDataRouteSearchInputData(takeoffCode, landingCode);

        // --- Act ---
        interactor.execute(input);

        // --- Assert ---
        assertNotNull(presenter.lastOutput, "Presenter should have been called");

        AirportDataRouteSearchOutputData out = presenter.lastOutput;

        assertNull(out.getTakeOffAirport());
        assertSame(landing, out.getLandingAirport());
        assertFalse(out.areBothFound(), "bothFound should be false when takeoff is missing");

        assertEquals(
                "Takeoff airport " + takeoffCode + " was not found.",
                out.getMessage()
        );
    }

    @Test
    void onlyLandingMissing() {
        // --- Arrange ---
        Airport takeOff = new Airport("AAA", "Toronto", "Canada");

        MapAirportDAO dao = new MapAirportDAO();
        dao.put(takeOff); // only takeoff is present

        TestPresenter presenter = new TestPresenter();
        AirportDataRouteSearchInteractor interactor =
                new AirportDataRouteSearchInteractor(dao, presenter);

        String takeoffCode = "AAA";
        String landingCode = "BBB";
        AirportDataRouteSearchInputData input =
                new AirportDataRouteSearchInputData(takeoffCode, landingCode);

        // --- Act ---
        interactor.execute(input);

        // --- Assert ---
        assertNotNull(presenter.lastOutput, "Presenter should have been called");

        AirportDataRouteSearchOutputData out = presenter.lastOutput;

        assertSame(takeOff, out.getTakeOffAirport());
        assertNull(out.getLandingAirport());
        assertFalse(out.areBothFound(), "bothFound should be false when landing is missing");

        assertEquals(
                "Landing airport " + landingCode + " was not found.",
                out.getMessage()
        );
    }
}