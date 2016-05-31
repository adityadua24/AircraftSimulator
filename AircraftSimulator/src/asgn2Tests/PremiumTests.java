package asgn2Tests;

import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

import java.lang.reflect.Field;

import static org.junit.Assert.*;


/**
 * Created by eddy on 15/05/2016.
 */
public class PremiumTests {

    Passenger p;

    @org.junit.Before
    public void setUp() throws PassengerException {
        p = new Premium(10, 30);
    }
    @org.junit.Test
    public void testNoSeatsMsg() throws PassengerException {
        assertEquals(0, "No seats available in Premium".compareTo(p.noSeatsMsg()));
    }

    @org.junit.Test
    public void  testUpgrade_PassID() throws PassengerException {
        Passenger pUpgraded = p.upgrade();
        String str = "P(U)" + p.getPassID();
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
    public void testPremiumConstructor_NegativeBookingTime() throws PassengerException {
        Passenger pTest = new Premium(-10, 30);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testPremiumConstructor_NegativeDepartureTime() throws PassengerException {
        Passenger pTest = new Premium(10, -30);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testPremiumConstructor_BookingTimeGreater() throws PassengerException {
        Passenger pTest = new Premium(30, 10);
    }
    @org.junit.Test
    public void testPremiumConstructor_BookingTime() throws PassengerException {
        assertEquals(10, p.getBookingTime());
    }
    @org.junit.Test
    public void testPremiumConstructor_DepartureTime() throws PassengerException {
        assertEquals(30, p.getDepartureTime());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testPremiumConstructor_DepartureTimeZero() throws PassengerException {
        Passenger pTest = new Premium(10, 0);
    }
    @org.junit.Test
    public void testPremiumConstructor_passId() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        String original = p.getPassID();
        Field f = Passenger.class.getDeclaredField("index");
        f.setAccessible(true);
        String expected = "P:"+ (f.getInt(p) - 1);
        assertEquals(0, original.compareTo(expected));
    }
}