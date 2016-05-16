package asgn2Tests;

import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

import static org.junit.Assert.*;

/**
 * Created by eddy on 15/05/2016.
 */
public class FirstTests {

    Passenger p;

    @org.junit.Before @org.junit.Test
    public void setUp() throws PassengerException {
        p = new First(3, 12);
    }

    @org.junit.After
    public void tearDown() throws PassengerException {

    }

    //Testing Passenger methods
    @org.junit.Test
    public void testPassengerConstructor_BookingTime() throws PassengerException {
        Passenger p2 = new First(3, 12);
        assertEquals(3, p2.getBookingTime());
    }

    @org.junit.Test
    public void testPassengerConstructor_DepartureTime() throws PassengerException {
        Passenger p2 = new First(3, 12);
        assertEquals(12, p2.getDepartureTime());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testPassengerConstructor_PassengerExceptionTest() throws PassengerException {
        Passenger p2 = new First(12, 3);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testPassengerConstructor_InvalidBookingTime() throws PassengerException {
        Passenger p2 = new First(-3, 5);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testPassengerConstructor_InvalidDepartureTime() throws PassengerException {
        Passenger p2 = new First(3, -10);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testPassengerConstructor_DepartureTimeZero() throws PassengerException {
        Passenger p2 = new First(3, 0);
    }
    @org.junit.Test
    public void testPassengerConstructor_StateTransitionCheck() throws PassengerException {
        assertTrue(p.isConfirmed() || p.isQueued() || p.isRefused());
    }
    @org.junit.Test
    public void testPassengerConstructor_NewStateTransitionCheck() throws PassengerException {
        assertFalse(p.isNew());
    }

    @org.junit.Test
    public void testNoSeatsMsg() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testUpgrade() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testCancelSeat() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testConfirmSeat() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testFlyPassenger() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testGetBookingTime() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testGetConfirmationTime() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testGetDepartureTime() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testGetEnterQueueTime() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testExitQueueTime() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testGetPassID() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testIsConfirmed() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testIsFlown() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testIsNew() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testIsQueued() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testIsRefused() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testNoSeatsMsg1() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testQueuePassenger() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testRefusePassenger() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testUpgrade1() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testWasConfirmed() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testWasQueued() throws PassengerException {
        fail();
    }

}