package asgn2Tests;

import asgn2Passengers.Business;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;

import static org.junit.Assert.*;

/**
 * Created by eddy on 15/05/2016.
 */
public class BusinessTests {

    Passenger p;

    @org.junit.Before
    public void setUp() throws PassengerException {
        p = new Business(10, 30);
    }
    @org.junit.Test
    public void testNoSeatsMsg() throws PassengerException {
        assertEquals(0, "No seats available in Business".compareTo(p.noSeatsMsg()));
    }

    @org.junit.Test
    public void  testUpgrade_PassID() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        String str = "F(U)" + p.getPassID();
        assertEquals(0, str.compareTo(pUpgraded.getPassID()));
    }
    @org.junit.Test
    public void testUpgrade_BookingTime() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.getBookingTime(), pUpgraded.getBookingTime());
    }
    @org.junit.Test
    public void testUpgrade_DepartureTime() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.getDepartureTime(), pUpgraded.getDepartureTime());
    }
    @org.junit.Test
    public void testUpgrade_NewStateValue() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.isNew(), pUpgraded.isNew());
    }
    @org.junit.Test
    public void testUpgrade_InQueueValue() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.isQueued(), pUpgraded.isQueued());
    }
    @org.junit.Test
    public void testUpgrade_ConfirmedValue() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.isConfirmed(), pUpgraded.isConfirmed());
    }
    @org.junit.Test
    public void testUpgrade_FlownValue() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.isFlown(), pUpgraded.isFlown());
    }
    @org.junit.Test
    public void testUpgrade_RefusedValue() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.isRefused(), pUpgraded.isRefused());
    }
    @org.junit.Test
    public void testUpgrade_enterQueueTime() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.getEnterQueueTime(), pUpgraded.getEnterQueueTime());
    }
    @org.junit.Test
    public void testUpgrade_exitQueueTime() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.getExitQueueTime(), pUpgraded.getExitQueueTime());
    }
    @org.junit.Test
    public void testUpgrade_confirmationTime() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        assertEquals(p.getConfirmationTime(), pUpgraded.getConfirmationTime());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testBusinessConstructor_NegativeBookingTime() throws PassengerException {
        Passenger pTest = new Business(-10, 30);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testBusinessConstructor_NegativeDepartureTime() throws PassengerException {
        Passenger pTest = new Business(10, -30);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testBusinessConstructor_BookingTimeGreater() throws PassengerException {
        Passenger pTest = new Business(30, 10);
    }
    @org.junit.Test
    public void testBusinessConstructor_BookingTime() throws PassengerException {
        assertEquals(10, p.getBookingTime());
    }
    @org.junit.Test
    public void testBusinessConstructor_DepartureTime() throws PassengerException {
        assertEquals(30, p.getBookingTime());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testBusinessConstructor_DepartureTimeZero() throws PassengerException {
        Passenger pTest = new Business(10, 0);
    }
    @org.junit.Test
    public void testBusinessConstructor_passId() throws PassengerException {
        assertEquals(0, "J:0".compareTo(p.getPassID()));
    }

}