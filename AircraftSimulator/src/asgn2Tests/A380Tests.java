package asgn2Tests;

import asgn2Aircraft.A380;
import asgn2Aircraft.Aircraft;
import asgn2Aircraft.Bookings;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.*;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by eddy on 15/05/2016.
 *
 * Functionality by Curtis Fulton
 */
public class A380Tests {

    Aircraft basicAircraft;

    Passenger testPassenger;

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
        int expectedFirst = 0;

        assertEquals(expectedFirst, basicAircraft.getNumFirst());
    }

    @org.junit.Test
    public void testAircraftConstructor_FirstCapacityCorrect() throws AircraftException, IllegalAccessException, NoSuchFieldException {
        int expectedFirst = 14;

        assertEquals(expectedFirst, GetField("firstCapacity", basicAircraft));
    }

    @org.junit.Test
    public void testAircraftConstructor_BusinessCorrect() throws AircraftException {
        int expectedBusiness = 0;

        assertEquals(expectedBusiness, basicAircraft.getNumBusiness());
    }

    @org.junit.Test
    public void testAircraftConstructor_BusinessCapacityCorrect() throws AircraftException, IllegalAccessException, NoSuchFieldException {
        int expectedBusiness = 64;

        assertEquals(expectedBusiness, GetField("businessCapacity", basicAircraft));
    }

    @org.junit.Test
    public void testAircraftConstructor_PremiumCorrect() throws AircraftException {
        int expectedPremium = 0;

        assertEquals(expectedPremium, basicAircraft.getNumPremium());
    }

    @org.junit.Test
    public void testAircraftConstructor_PremiumCapacityCorrect() throws AircraftException, IllegalAccessException, NoSuchFieldException {
        int expectedPremium = 35;

        assertEquals(expectedPremium, GetField("premiumCapacity", basicAircraft));
    }

    @org.junit.Test
    public void testAircraftConstructor_EconomyCorrect() throws AircraftException {
        int expectedEconomy = 0;

        assertEquals(expectedEconomy, basicAircraft.getNumEconomy());
    }

    @org.junit.Test
    public void testAircraftConstructor_EconomyCapacityCorrect() throws AircraftException, IllegalAccessException, NoSuchFieldException {
        int expectedEconomy = 371;

        assertEquals(expectedEconomy, GetField("economyCapacity", basicAircraft));
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
    public void testConfirmBooking_PassengerAddedToCorrectClass() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 12);

        assertEquals(1, basicAircraft.getNumFirst());
    }

    @org.junit.Test
    public void testConfirmBooking_CorrectNumberOfPassengersAddedTotal() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 12);

        assertEquals(1, basicAircraft.getNumPassengers());
    }

    @org.junit.Test
    public void testConfirmBooking_PassengerStateChanged() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 12);

        assertTrue(testPassenger.isConfirmed());
    }

    @org.junit.Test
    public void testConfirmBooking_OneSeatAvailable() throws AircraftException, PassengerException, IllegalAccessException, NoSuchFieldException  {
        SetField("numFirst", basicAircraft, 13);

        basicAircraft.confirmBooking(testPassenger, 12);

        assertTrue(testPassenger.isConfirmed());
    }

    @org.junit.Test(expected = AircraftException.class)
    public void testConfirmBooking_NoSeatsAvailable() throws AircraftException, PassengerException, IllegalAccessException, NoSuchFieldException  {
        SetField("numFirst", basicAircraft, 14);

        basicAircraft.confirmBooking(testPassenger, 12);
    }

    @org.junit.Test
    public void testFinalState_NoPassengers() throws AircraftException {
        String expectedResult = "A380:TE35T:12 Pass: 0\n\n";

        assertEquals(expectedResult, basicAircraft.finalState());
    }

    @org.junit.Test
    public void testFinalState_WithPassenger() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 8);

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
    public void testFlightFull_IsFullAircraft() throws AircraftException, IllegalAccessException, NoSuchFieldException {
        SetField("numFirst", basicAircraft, 14);
        SetField("numBusiness", basicAircraft, 64);
        SetField("numPremium", basicAircraft, 35);
        SetField("numEconomy", basicAircraft, 371);

        assertTrue(basicAircraft.flightFull());
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
    public void testFlyPassengers_NoPassengers() throws AircraftException, PassengerException {
        // Nothing should go wrong with no passengers, and nothing should change
        basicAircraft.flyPassengers(12);
    }

    @org.junit.Test
    public void testFlyPassengers_OnePassenger() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 8);

        basicAircraft.flyPassengers(12);

        assertTrue(testPassenger.isFlown());
    }

    @org.junit.Test
    public void testFlyPassengers_MultiplePassengers() throws AircraftException, PassengerException {
        Economy testPassenger2 = new Economy(3, 12);

        basicAircraft.confirmBooking(testPassenger, 8);
        basicAircraft.confirmBooking(testPassenger2, 8);

        basicAircraft.flyPassengers(12);

        // Checks that the second passenger gets flown. We already know that passenger 1 gets flown
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

    @org.junit.Test
    public void testGetPassengers_SeparateReferences() throws AircraftException, PassengerException, IllegalAccessException, NoSuchFieldException {
        basicAircraft.confirmBooking(testPassenger, 8);

        List<Passenger> passengerCopy = basicAircraft.getPassengers();

        assertNotEquals(GetField("seats", basicAircraft), passengerCopy);
    }

    @org.junit.Test
    public void testGetPassengers_ValuesEqual() throws AircraftException, PassengerException, IllegalAccessException, NoSuchFieldException {
        basicAircraft.confirmBooking(testPassenger, 8);

        List<Passenger> passengerCopy = basicAircraft.getPassengers();
        List<Passenger> originalPassengers = (List<Passenger>) GetField("seats", basicAircraft);

        for (int i = 0; i < originalPassengers.size(); i++){
            assertEquals(originalPassengers.get(i), passengerCopy.get(i));
        }
    }

    @org.junit.Test
    public void testHasPassenger_PassengerInAircraft() throws AircraftException, PassengerException {
        basicAircraft.confirmBooking(testPassenger, 8);

        assertTrue(basicAircraft.hasPassenger(testPassenger));
    }

    @org.junit.Test
    public void testHasPassenger_PassengerNotInAircraft() throws AircraftException, PassengerException {
        assertFalse(basicAircraft.hasPassenger(testPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_FirstClassPassengerWithAvailableSeat() throws IllegalAccessException, NoSuchFieldException {
        SetField("numFirst", basicAircraft, 13);
        assertTrue(basicAircraft.seatsAvailable(testPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_FirstClassPassengerWithNoAvailableSeats() throws IllegalAccessException, NoSuchFieldException {
        SetField("numFirst", basicAircraft, 14);
        assertFalse(basicAircraft.seatsAvailable(testPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_EconomyPassengerWithAvailableSeat() throws PassengerException, IllegalAccessException, NoSuchFieldException {
        Economy economyPassenger = new Economy(3, 12);

        SetField("numEconomy", basicAircraft, 370);

        assertTrue(basicAircraft.seatsAvailable(economyPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_EconomyPassengerWithNoAvailableSeats() throws PassengerException, IllegalAccessException, NoSuchFieldException {
        Economy economyPassenger = new Economy(3, 12);

        SetField("numEconomy", basicAircraft, 371);

        assertFalse(basicAircraft.seatsAvailable(economyPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_BusinessPassengerWithAvailableSeat() throws PassengerException, IllegalAccessException, NoSuchFieldException {
        Business businessPassenger = new Business(3, 12);

        SetField("numBusiness", basicAircraft, 63);

        assertTrue(basicAircraft.seatsAvailable(businessPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_BusinessPassengerWithNoAvailableSeats() throws PassengerException, IllegalAccessException, NoSuchFieldException {
        Business businessPassenger = new Business(3, 12);

        SetField("numBusiness", basicAircraft, 64);

        assertFalse(basicAircraft.seatsAvailable(businessPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_PremiumPassengerWithAvailableSeat() throws PassengerException, IllegalAccessException, NoSuchFieldException {
        Premium premiumPassenger = new Premium(3, 12);

        SetField("numPremium", basicAircraft, 34);

        assertTrue(basicAircraft.seatsAvailable(premiumPassenger));
    }

    @org.junit.Test
    public void testSeatsAvailable_PremiumPassengerWithNoAvailableSeats() throws PassengerException, IllegalAccessException, NoSuchFieldException {
        Premium premiumPassenger = new Premium(3, 12);

        SetField("numPremium", basicAircraft, 35);

        assertFalse(basicAircraft.seatsAvailable(premiumPassenger));
    }

    @org.junit.Test
    public void testUpgradeBookings() throws AircraftException {
        // Don't fully understand what this one is actually doing... Or more so WHY it is doing it.
    }

    @org.junit.Test
    public void testGetBookings_CorrectFirstPassengers() throws PassengerException, AircraftException {
        Passenger newPassenger = new First(3, 12);

        basicAircraft.confirmBooking(newPassenger, 8);

        Bookings testBookings = basicAircraft.getBookings();

        assertEquals(1, testBookings.getNumFirst());
    }

    @org.junit.Test
    public void testGetBookings_CorrectBusinessPassengers() throws PassengerException, AircraftException {
        Passenger newPassenger = new Business(3, 12);

        basicAircraft.confirmBooking(newPassenger, 8);

        Bookings testBookings = basicAircraft.getBookings();

        assertEquals(1, testBookings.getNumBusiness());
    }

    @org.junit.Test
    public void testGetBookings_CorrectPremiumPassengers() throws PassengerException, AircraftException {
        Passenger newPassenger = new Premium(3, 12);

        basicAircraft.confirmBooking(newPassenger, 8);

        Bookings testBookings = basicAircraft.getBookings();

        assertEquals(1, testBookings.getNumPremium());
    }

    @org.junit.Test
    public void testGetBookings_CorrectEconomyPassengers() throws PassengerException, AircraftException {
        Passenger newPassenger = new Economy(3, 12);

        basicAircraft.confirmBooking(newPassenger, 8);

        Bookings testBookings = basicAircraft.getBookings();

        assertEquals(1, testBookings.getNumEconomy());
    }

    private void SetField(String fieldName, Object inst, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field flightCodeField = aircraftClass.getDeclaredField(fieldName);
        flightCodeField.setAccessible(true);

        flightCodeField.set(inst, value);
    }

    private Object GetField(String fieldName, Object inst) throws NoSuchFieldException, IllegalAccessException {
        Field flightCodeField = aircraftClass.getDeclaredField(fieldName);
        flightCodeField.setAccessible(true);

        return flightCodeField.get(inst);
    }

}