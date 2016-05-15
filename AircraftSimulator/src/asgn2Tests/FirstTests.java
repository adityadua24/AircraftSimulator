package asgn2Tests;

import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

import static org.junit.Assert.*;


/**
 * Created by eddy on 15/05/2016.
 */
public class FirstTests extends Passenger {
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
    public String noSeatsMsg() throws PassengerException {

        return null;
    }

    @org.junit.Test
    public Passenger upgrade() throws PassengerException {

        return null;
    }

    @org.junit.Test
    public void cancelSeat() throws PassengerException {

    }

    @org.junit.Test
    public void confirmSeat() throws PassengerException {

    }

    @org.junit.Test
    public void flyPassenger() throws PassengerException {

    }

    @org.junit.Test
    public int getBookingTime() throws PassengerException {

        return 0;
    }

    @org.junit.Test
    public int getConfirmationTime() throws PassengerException {

        return 0;
    }

    @org.junit.Test
    public int getDepartureTime() throws PassengerException {

        return 0;
    }

    @org.junit.Test
    public int getEnterQueueTime() throws PassengerException {

        return 0;
    }

    @org.junit.Test
    public int getExitQueueTime() throws PassengerException {

        return 0;
    }

    @org.junit.Test
    public String getPassID() throws PassengerException {

        return null;
    }

    @org.junit.Test
    public boolean isConfirmed() throws PassengerException {

        return false;
    }

    @org.junit.Test
    public boolean isFlown() throws PassengerException {

        return false;
    }

    @org.junit.Test
    public boolean isNew() throws PassengerException {

        return false;
    }

    @org.junit.Test
    public boolean isQueued() throws PassengerException {

        return false;
    }

    @org.junit.Test
    public boolean isRefused() throws PassengerException {

        return false;
    }

    @org.junit.Test
    public void noSeatsMsg1() throws PassengerException {

    }

    @org.junit.Test
    public void queuePassenger() throws PassengerException {

    }

    @org.junit.Test
    public void refusePassenger() throws PassengerException {

    }

    @org.junit.Test
    public void upgrade1() throws PassengerException {

    }

    @org.junit.Test
    public boolean wasConfirmed() throws PassengerException {

        return false;
    }

    @org.junit.Test
    public boolean wasQueued() throws PassengerException {

        return false;
    }

}