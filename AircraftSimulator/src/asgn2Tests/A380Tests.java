package asgn2Tests;

import asgn2Aircraft.A380;
import asgn2Aircraft.Aircraft;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.*;

import static org.junit.Assert.*;

/**
 * Created by eddy on 15/05/2016.
 *
 * Functionality by Curtis Fulton
 */
public class A380Tests {

    A380 testAircraft;
    First testPassenger;

    @org.junit.Before
    public void setUp() throws Exception {
        testAircraft = new A380("TE35T", 12);

        testPassenger = new First(3, 12);
    }

    @org.junit.Test
    public void testCancelBooking_ValidCancellation() throws Exception {
        testAircraft.confirmBooking(testPassenger, 8);

        testAircraft.cancelBooking(testPassenger, 11);
    }

    @org.junit.Test(expected = PassengerException.class)
    public void testCancelBooking_NegativeCancellationTime() throws Exception {
        testAircraft.confirmBooking(testPassenger, 8);

        testAircraft.cancelBooking(testPassenger, -10);
    }

    @org.junit.Test(expected = PassengerException.class)
    public void testCancelBooking_CancelledAfterDeparture() throws Exception {
        testAircraft.confirmBooking(testPassenger, 8);

        // The cancellation time is invalid when departure time is less than cancellation time.
        testAircraft.cancelBooking(testPassenger, 13);
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testCancelBooking_PassengerNotConfirmed() throws Exception {
        testAircraft.cancelBooking(testPassenger, 14);
    }

    // TODO: Add extra Cancel booking tests. Check that i need to check any Passenger exceptions here as well?

    @org.junit.Test
    public void testConfirmBooking_ValidConfirmation() throws Exception {
        // Upper limit/Latest possible booking someone can do in this situation.
        testAircraft.confirmBooking(testPassenger, 12);

        // Checks that the passenger is not only added to the right class
        // But also that they are only added once
        assertEquals(1, testAircraft.getNumFirst());
        assertEquals(1, testAircraft.getNumPassengers());
    }

    // TODO: Add extra Confirm Booking tests. Namely any exceptions

    @org.junit.Test
    public void testFinalState_NoPassengers() throws Exception {
        String expectedResult = "A380:TE35T:12 Pass: 0\n\n";

        assertEquals(expectedResult, testAircraft.finalState());
    }

    @org.junit.Test
    public void testFinalState_WithPassenger() throws Exception {
        // TThis may be wrong. It's quite hard to tell with all the \n's
        String expectedResult = "A380:TE35T:12 Pass: 0\n" +
                "passID: F:0\n" +
                "BT: 3\n" +
                "NotQ\n\n";

        assertEquals(expectedResult, testAircraft.finalState());
    }

    @org.junit.Test
    public void testFlightEmpty_IsEmptyAircraft() throws Exception {
        assertTrue(testAircraft.flightEmpty());
    }

    @org.junit.Test
    public void testFlightEmpty_NotEmptyAircraft() throws Exception {
        testAircraft.confirmBooking(testPassenger, 8);

        assertFalse(testAircraft.flightEmpty());
    }

    @org.junit.Test
    public void testFlightFull_IsFullAircraft() throws Exception {
        // Still deciding best way to to this. Probably reflection and artificially fill the plane.
    }

    @org.junit.Test
    public void testFlightFull_NotFull() throws Exception {
        testAircraft.confirmBooking(testPassenger, 8);

        assertFalse(testAircraft.flightFull());
    }

    @org.junit.Test
    public void testFlightFull_EmptyAircraft() throws Exception {
        assertFalse(testAircraft.flightFull());
    }

    @org.junit.Test
    public void testFlyPassengers() throws Exception {
        Economy testPassenger2 = new Economy(3, 12);

        testAircraft.confirmBooking(testPassenger, 8);
        testAircraft.confirmBooking(testPassenger2, 8);

        testAircraft.flyPassengers(12);

        // Makes sure that it doesn't just work for the first passenger, but all of them.
        assertTrue(testPassenger.isFlown());
        assertTrue(testPassenger2.isFlown());
    }

    @org.junit.Test
    public void testGetNumPassengers() throws Exception {
        Economy testPassenger2 = new Economy(3, 12);
        Premium testPassenger3 = new Premium(3, 12);
        Business testPassenger4 = new Business(3, 12);

        testAircraft.confirmBooking(testPassenger, 8);
        testAircraft.confirmBooking(testPassenger2, 8);
        testAircraft.confirmBooking(testPassenger3, 8);
        testAircraft.confirmBooking(testPassenger4, 8);

        assertEquals(4, testAircraft.getNumPassengers());
    }

    //Do test this
    @org.junit.Test
    public void testGetPassengers() throws Exception {
        //TODO: What does this need to test?
    }

    @org.junit.Test
    public void testGetStatus() throws Exception {
        // Should this really be tested?
    }

    @org.junit.Test
    public void testHasPassenger() throws Exception {
        testAircraft.confirmBooking(testPassenger, 8);
    }

    @org.junit.Test
    public void testInitialState() throws Exception {
        // This shouldn't be tested right?
    }

    @org.junit.Test
    public void testSeatsAvailable_FirstClassPassengerWithAvailableSeats() throws Exception {
        assertTrue(testAircraft.seatsAvailable(testPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_FirstClassPassengerWithNoAvailableSeats() throws Exception {
        // FIXME: Add reflection to artificially 'fill' the first class seats
        assertFalse(testAircraft.seatsAvailable(testPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_EconomyPassengerWithAvailableSeats() throws Exception {
        Economy economyPassenger = new Economy(3, 12);

        assertTrue(testAircraft.seatsAvailable(economyPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_EconomyPassengerWithNoAvailableSeats() throws Exception {
        // FIXME: Add reflection to artificially 'fill' the first class seats
        Economy economyPassenger = new Economy(3, 12);

        assertFalse(testAircraft.seatsAvailable(economyPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_BusinessPassengerWithAvailableSeats() throws Exception {
        Business businessPassenger = new Business(3, 12);

        assertTrue(testAircraft.seatsAvailable(businessPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_BusinessPassengerWithNoAvailableSeats() throws Exception {
        // FIXME: Add reflection to artificially 'fill' the first class seats
        Business businessPassenger = new Business(3, 12);

        assertFalse(testAircraft.seatsAvailable(businessPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_PremiumPassengerWithAvailableSeats() throws Exception {
        Premium premiumPassenger = new Premium(3, 12);

        assertTrue(testAircraft.seatsAvailable(premiumPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_PremiumPassengerWithNoAvailableSeats() throws Exception {
        // FIXME: Add reflection to artificially 'fill' the first class seats
        Premium premiumPassenger = new Premium(3, 12);

        assertFalse(testAircraft.seatsAvailable(premiumPassenger));
    }

    @org.junit.Test
    public void testUpgradeBookings() throws Exception {
        // Don't fully understand what this one is actually doing... Or more so WHY it is doing it.
    }

}