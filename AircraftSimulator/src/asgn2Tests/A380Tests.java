package asgn2Tests;

import asgn2Aircraft.A380;
import asgn2Aircraft.Aircraft;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.*;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Created by eddy on 15/05/2016.
 *
 * Functionality by Curtis Fulton
 */
public class A380Tests {

    A380 basicAircraft;

    First testPassenger;

    Class aircraftClass = Aircraft.class;

    @org.junit.Before
    public void setUp() throws Exception {
        basicAircraft = new A380("TE35T", 12, 14, 64, 35, 371);

        testPassenger = new First(3, 12);
    }

    @org.junit.Test
    public void testAircraftConstructor_FlightCodeCorrect() throws AircraftException, NoSuchFieldException, IllegalAccessException {
        String expectedFlightCode = "TE35T";

        Field flightCodeField = aircraftClass.getDeclaredField("flightCode");
        flightCodeField.setAccessible(true);

        String flightCode = (String) flightCodeField.get(basicAircraft);

        assertEquals(expectedFlightCode, flightCode);
    }

    @org.junit.Test
    public void testAircraftConstructor_DepartureTimeCorrect() throws AircraftException, NoSuchFieldException, IllegalAccessException {
        int expectedDepartureTime = 12;

        Field departureTimeField = aircraftClass.getDeclaredField("departureTime");
        departureTimeField.setAccessible(true);

        int flightCode = departureTimeField.getInt(basicAircraft);

        assertEquals(expectedDepartureTime, flightCode);
    }

    @org.junit.Test
    public void testAircraftConstructor_FirstCorrect() throws AircraftException {
        int expectedFirst = 14;

        assertEquals(expectedFirst, basicAircraft.getNumFirst());
    }

    @org.junit.Test
    public void testAircraftConstructor_BusinessCorrect() throws AircraftException {
        int expectedBusiness = 64;

        assertEquals(expectedBusiness, basicAircraft.getNumBusiness());
    }

    @org.junit.Test
    public void testAircraftConstructor_PremiumCorrect() throws AircraftException {
        int expectedPremium = 35;

        assertEquals(expectedPremium, basicAircraft.getNumPremium());
    }

    @org.junit.Test
    public void testAircraftConstructor_EconomyCorrect() throws AircraftException {
        int expectedEconomy = 371;

        assertEquals(expectedEconomy, basicAircraft.getNumEonomy());
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testAircraftConstructor_FlightCodeNull() throws AircraftException {
        new A380(null, 12, 14, 64, 35, 371);
    }

    @org.junit.Test
    public void testAircraftConstructor_FlightCodeEmpty() throws AircraftException {
        //TODO: Check this is correct and it is not meant to throw an error when empty
        assertNotNull(new A380("", 12, 14, 64, 35, 371));
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testAircraftConstructor_NegativeDepartureTime() throws AircraftException {
        new A380("TE35T", -1, 14, 64, 35, 371);
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testAircraftConstructor_DepartureTimeZero() throws AircraftException {
        assertNotNull(new A380("TE35T", 0, 14, 64, 35, 371));
    }

    @org.junit.Test
    public void testAircraftConstructor_ValidDepartureTime() throws AircraftException {
        new A380("TE35T", 1, 14, 64, 35, 371);
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testAircraftConstructor_NegativeFirst() throws AircraftException {
        new A380("TE35T", 12, -1, 64, 35, 371);
    }

    @org.junit.Test
    public void testAircraftConstructor_ZeroFirst() throws AircraftException {
        assertNotNull(new A380("TE35T", 12, 0, 64, 35, 371));
    }

    @org.junit.Test
    public void testAircraftConstructor_ValidFirst() throws AircraftException {
        assertNotNull(new A380("TE35T", 12, 14, 64, 35, 371));
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testAircraftConstructor_NegativeBusiness() throws AircraftException {
        new A380("TE35T", 12, 14, -1, 35, 371);
    }

    @org.junit.Test
    public void testAircraftConstructor_ZeroBusiness() throws AircraftException {
        assertNotNull(new A380("TE35T", 12, 14, 0, 35, 371));
    }

    @org.junit.Test
    public void testAircraftConstructor_ValidBusiness() throws AircraftException {
        assertNotNull(new A380("TE35T", 12, 14, 64, 35, 371));
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testAircraftConstructor_NegativePremium() throws AircraftException {
        new A380("TE35T", 12, 14, 64, -1, 371);
    }

    @org.junit.Test
    public void testAircraftConstructor_ZeroPremium() throws AircraftException {
        assertNotNull(new A380("TE35T", 12, 14, 64, 0, 371));
    }

    @org.junit.Test
    public void testAircraftConstructor_ValidPremium() throws AircraftException {
        assertNotNull(new A380("TE35T", 12, 14, 64, 35, 371));
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testAircraftConstructor_NegativeEconomy() throws AircraftException {
        new A380("TE35T", 12, 14, 64, 35, -1);
    }

    @org.junit.Test
    public void testAircraftConstructor_ZeroEconomy() throws AircraftException {
        assertNotNull(new A380("TE35T", 12, 14, 64, 35, 0));
    }

    @org.junit.Test
    public void testAircraftConstructor_ValidEconomy() throws AircraftException {
        assertNotNull(new A380("TE35T", 12, 14, 64, 35, 371));
    }

    @org.junit.Test
    public void testCancelBooking_PassengerRemovedFromAircraft() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 8);

        basicAircraft.cancelBooking(testPassenger, 11);

        assertEquals(0, basicAircraft.getNumPassengers());
    }

    @org.junit.Test
    public void testCancelBooking_PassengerIsNew() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 8);

        basicAircraft.cancelBooking(testPassenger, 11);

        assertTrue(testPassenger.isNew());
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testCancelBooking_PassengerNotConfirmed() throws AircraftException, PassengerException {
        basicAircraft.cancelBooking(testPassenger, 14);
    }

    @org.junit.Test
    public void testConfirmBooking_ValidConfirmation() throws AircraftException, PassengerException {
        // Upper limit/Latest possible booking someone can do in this situation.
        basicAircraft.confirmBooking(testPassenger, 12);

        // Checks that the passenger is not only added to the right class
        // But also that they are only added once
        assertEquals(1, basicAircraft.getNumFirst());
        assertEquals(1, basicAircraft.getNumPassengers());
    }

    // TODO: Add extra Confirm Booking tests. Namely any exceptions

    @org.junit.Test
    public void testFinalState_NoPassengers() throws AircraftException {
        String expectedResult = "A380:TE35T:12 Pass: 0\n\n";

        assertEquals(expectedResult, basicAircraft.finalState());
    }

    @org.junit.Test
    public void testFinalState_WithPassenger() throws AircraftException {
        // TThis may be wrong. It's quite hard to tell with all the \n's
        String expectedResult = "A380:TE35T:12 Pass: 0\n" +
                "passID: F:0\n" +
                "BT: 3\n" +
                "NotQ\n\n";

        assertEquals(expectedResult, basicAircraft.finalState());
    }

    @org.junit.Test
    public void testFlightEmpty_IsEmptyAircraft() throws AircraftException {
        assertTrue(basicAircraft.flightEmpty());
    }

    @org.junit.Test
    public void testFlightEmpty_NotEmptyAircraft() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 8);

        assertFalse(basicAircraft.flightEmpty());
    }

    @org.junit.Test
    public void testFlightFull_IsFullAircraft() throws AircraftException {
        // Still deciding best way to to this. Probably reflection and artificially fill the plane.
    }

    @org.junit.Test
    public void testFlightFull_NotFull() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 8);

        assertFalse(basicAircraft.flightFull());
    }

    @org.junit.Test
    public void testFlightFull_EmptyAircraft() throws AircraftException {
        assertFalse(basicAircraft.flightFull());
    }

    @org.junit.Test
    public void testFlyPassengers() throws AircraftException, PassengerException {
        Economy testPassenger2 = new Economy(3, 12);

        basicAircraft.confirmBooking(testPassenger, 8);
        basicAircraft.confirmBooking(testPassenger2, 8);

        basicAircraft.flyPassengers(12);

        // Makes sure that it doesn't just work for the first passenger, but all of them.
        assertTrue(testPassenger.isFlown());
        assertTrue(testPassenger2.isFlown());
    }

    @org.junit.Test
    public void testGetNumPassengers() throws AircraftException, PassengerException {
        Economy testPassenger2 = new Economy(3, 12);
        Premium testPassenger3 = new Premium(3, 12);
        Business testPassenger4 = new Business(3, 12);

        basicAircraft.confirmBooking(testPassenger, 8);
        basicAircraft.confirmBooking(testPassenger2, 8);
        basicAircraft.confirmBooking(testPassenger3, 8);
        basicAircraft.confirmBooking(testPassenger4, 8);

        assertEquals(4, basicAircraft.getNumPassengers());
    }

    //Do test this
    @org.junit.Test
    public void testGetPassengers() throws AircraftException {
        //TODO: What does this need to test?
    }

    @org.junit.Test
    public void testGetStatus() throws AircraftException {
        // Should this really be tested?
    }

    @org.junit.Test
    public void testHasPassenger() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 8);
    }

    @org.junit.Test
    public void testInitialState() throws AircraftException {
        // This shouldn't be tested right?
    }

    @org.junit.Test
    public void testSeatsAvailable_FirstClassPassengerWithAvailableSeats() throws AircraftException {
        assertTrue(basicAircraft.seatsAvailable(testPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_FirstClassPassengerWithNoAvailableSeats() throws AircraftException {
        // FIXME: Add reflection to artificially 'fill' the first class seats
        assertFalse(basicAircraft.seatsAvailable(testPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_EconomyPassengerWithAvailableSeats() throws AircraftException, PassengerException {
        Economy economyPassenger = new Economy(3, 12);

        assertTrue(basicAircraft.seatsAvailable(economyPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_EconomyPassengerWithNoAvailableSeats() throws AircraftException, PassengerException {
        // FIXME: Add reflection to artificially 'fill' the first class seats
        Economy economyPassenger = new Economy(3, 12);

        assertFalse(basicAircraft.seatsAvailable(economyPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_BusinessPassengerWithAvailableSeats() throws AircraftException, PassengerException {
        Business businessPassenger = new Business(3, 12);

        assertTrue(basicAircraft.seatsAvailable(businessPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_BusinessPassengerWithNoAvailableSeats() throws AircraftException, PassengerException {
        // FIXME: Add reflection to artificially 'fill' the first class seats
        Business businessPassenger = new Business(3, 12);

        assertFalse(basicAircraft.seatsAvailable(businessPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_PremiumPassengerWithAvailableSeats() throws AircraftException, PassengerException {
        Premium premiumPassenger = new Premium(3, 12);

        assertTrue(basicAircraft.seatsAvailable(premiumPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_PremiumPassengerWithNoAvailableSeats() throws AircraftException, PassengerException {
        // FIXME: Add reflection to artificially 'fill' the first class seats
        Premium premiumPassenger = new Premium(3, 12);

        assertFalse(basicAircraft.seatsAvailable(premiumPassenger));
    }

    @org.junit.Test
    public void testUpgradeBookings() throws AircraftException {
        // Don't fully understand what this one is actually doing... Or more so WHY it is doing it.
    }

}