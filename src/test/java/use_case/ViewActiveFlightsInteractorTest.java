package use_case;


import use_case.ViewActiveFlights.*;
import entity.AirlineFlight;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ViewActiveFlightsInteractorTest {

    @Test
    void testInvalidPrefix_NullOrEmpty() {
        // Arrange
        ViewActiveFlightsDataAccessInterface dao = prefix -> null;

        final boolean[] invalidCalled = {false};
        ViewActiveFlightsOutputBoundary presenter =
                new ViewActiveFlightsOutputBoundary() {
                    @Override
                    public void presentInvalidPrefix() {
                        invalidCalled[0] = true;
                    }

                    @Override
                    public void present(ViewActiveFlightsOutputData outputData) { }
                };

        ViewActiveFlightsInteractor interactor =
                new ViewActiveFlightsInteractor(dao, presenter);

        // Act
        interactor.execute(new ViewActiveFlightsInputData(""));

        // Assert
        assertTrue(invalidCalled[0]);
    }

    @Test
    void testInvalidPrefix_NotInList() {
        ViewActiveFlightsDataAccessInterface dao = prefix -> null;

        final boolean[] invalidCalled = {false};
        ViewActiveFlightsOutputBoundary presenter =
                new ViewActiveFlightsOutputBoundary() {
                    @Override
                    public void presentInvalidPrefix() {
                        invalidCalled[0] = true;
                    }

                    @Override
                    public void present(ViewActiveFlightsOutputData outputData) { }
                };

        ViewActiveFlightsInteractor interactor =
                new ViewActiveFlightsInteractor(dao, presenter);

        interactor.execute(new ViewActiveFlightsInputData("TTT")); // Invalid

        assertTrue(invalidCalled[0]);
    }

    @Test
    void testValidPrefix_FlightsReturned() {
        List<AirlineFlight> fakeFlights = new ArrayList<>();
        fakeFlights.add(new AirlineFlight(
                "ACA123", "Canada", 45.0, -75.0, 10000, 250, 1234567890L
        ));

        ViewActiveFlightsDataAccessInterface dao = prefix -> fakeFlights;

        final boolean[] presentCalled = {false};
        final ViewActiveFlightsOutputData[] receivedData = {null};

        ViewActiveFlightsOutputBoundary presenter =
                new ViewActiveFlightsOutputBoundary() {
                    @Override
                    public void presentInvalidPrefix() { }

                    @Override
                    public void present(ViewActiveFlightsOutputData outputData) {
                        presentCalled[0] = true;
                        receivedData[0] = outputData;
                    }
                };

        ViewActiveFlightsInteractor interactor =
                new ViewActiveFlightsInteractor(dao, presenter);

        interactor.execute(new ViewActiveFlightsInputData("ACA"));

        assertTrue(presentCalled[0]);
        assertNotNull(receivedData[0]);
        assertEquals(1, receivedData[0].getFlights().size());
        assertEquals("ACA123", receivedData[0].getFlights().get(0).getFlightNumber());
    }

    @Test
    void testNullPrefix() {
        ViewActiveFlightsDataAccessInterface dao = prefix -> null;

        final boolean[] invalidCalled = {false};
        ViewActiveFlightsOutputBoundary presenter =
                new ViewActiveFlightsOutputBoundary() {
                    @Override
                    public void presentInvalidPrefix() {
                        invalidCalled[0] = true;
                    }

                    @Override
                    public void present(ViewActiveFlightsOutputData outputData) { }
                };

        ViewActiveFlightsInteractor interactor =
                new ViewActiveFlightsInteractor(dao, presenter);

        interactor.execute(new ViewActiveFlightsInputData(null));

        assertTrue(invalidCalled[0]);
    }
}

