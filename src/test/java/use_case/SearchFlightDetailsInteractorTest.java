package use_case.SearchFlightDetails;

import entity.Flight;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SearchFlightDetailsInteractorTest {

    @Test
    void testInvalidCallSign_EmptyOrNull() {

        SearchFlightDetailsDataAccessInterface dao = call -> null;

        final SearchFlightDetailsOutputData[] captured = {null};

        SearchFlightDetailsOutputBoundary presenter =
                output -> captured[0] = output;

        SearchFlightDetailsInteractor interactor =
                new SearchFlightDetailsInteractor(dao, presenter);

        interactor.execute(new SearchFlightDetailsInputData(""));

        assertNotNull(captured[0]);
        assertEquals("Unknown/Not ACTIVE Flight", captured[0].getOriginCountry());
        assertEquals(-1, captured[0].getTimePosition());
        assertEquals("Unknown", captured[0].getSquawk());
        assertFalse(captured[0].isOnGround());
    }

    @Test
    void testFlightNotFound() {

        SearchFlightDetailsDataAccessInterface dao = call -> null;

        final SearchFlightDetailsOutputData[] captured = {null};

        SearchFlightDetailsOutputBoundary presenter =
                output -> captured[0] = output;

        SearchFlightDetailsInteractor interactor =
                new SearchFlightDetailsInteractor(dao, presenter);

        interactor.execute(new SearchFlightDetailsInputData("SQ999"));

        assertNotNull(captured[0]);
        assertEquals("Unknown/Not ACTIVE Flight", captured[0].getOriginCountry());
    }

    @Test
    void testFlightFound() {

        Flight mockFlight = new Flight(
                "ACA850",
                "Canada",
                1678000000L,
                "7500",
                true
        );

        SearchFlightDetailsDataAccessInterface dao = call -> mockFlight;

        final SearchFlightDetailsOutputData[] captured = {null};

        SearchFlightDetailsOutputBoundary presenter =
                output -> captured[0] = output;

        SearchFlightDetailsInteractor interactor =
                new SearchFlightDetailsInteractor(dao, presenter);

        interactor.execute(new SearchFlightDetailsInputData("ACA850"));

        assertNotNull(captured[0]);
        assertEquals("ACA850", captured[0].getCallSign());
        assertEquals("Canada", captured[0].getOriginCountry());
        assertEquals(1678000000L, captured[0].getTimePosition());
        assertEquals("7500", captured[0].getSquawk());
        assertTrue(captured[0].isOnGround());
    }
}
