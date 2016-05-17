package asgn2Tests;

import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Created by eddy on 15/05/2016.
 */
public class FirstTests {

    Passenger p;
    Class c = Passenger.class;
    Field f;

    @org.junit.Before @org.junit.Test
    public void setUp() throws PassengerException {
        p = new First(3, 12);
        f = null;
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
    public void testCancelSeat_NewStateCheck() throws PassengerException {
        p.cancelSeat(5);
        assertTrue(p.isNew());
    }
    @org.junit.Test
    public void testCancelSeat_BookingTimeCheck() throws PassengerException {
        p.cancelSeat(5);
        assertEquals(5, p.getBookingTime());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_inValidCancelTime() throws PassengerException {
        p.cancelSeat(13);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_NegativeCancelTime() throws PassengerException {
        p.cancelSeat(-7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_StateCheckNew() throws PassengerException {
        p.cancelSeat(5);
        p.cancelSeat(7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_StateCheckQueued() throws PassengerException {
        p.queuePassenger(3, 12);
        p.cancelSeat(5);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_StateCheckFlown() throws NoSuchFieldException, IllegalAccessException, PassengerException {
        this.SetFlown(true);
        p.cancelSeat(6);

    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_StateCheckRefused() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetRefused(true);
        p.cancelSeat(6);
    }
    @org.junit.Test
    public void testCancelSeat_SameDepartureTime() throws PassengerException {
        p.cancelSeat(6);
        assertEquals(12, p.getDepartureTime());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_ConfirmedSeatCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetConfirmed(false);
        p.cancelSeat(6);

    }

    @org.junit.Test
    public void testFlyPassenger() throws PassengerException {
        p.flyPassenger(12);
        assertTrue(p.isFlown());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_ConfirmedCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetConfirmed(false);
        p.flyPassenger(12);

    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_NewStateCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetNewState(true);
        p.flyPassenger(12);

    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_QueuedStateCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetInQueue(true);
        p.flyPassenger(12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_FlownStateCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetFlown(true);
        p.flyPassenger(12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_RefusedStateCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetRefused(true);
        p.flyPassenger(12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_DepartureTimeZeroCheck() throws PassengerException {
        p.flyPassenger(0);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_DepartureTimeNegativeCheck() throws PassengerException {
        p.flyPassenger(-5);
    }

    @org.junit.Test
    public void testGetConfirmationTime_SeatNotConfirmed() throws PassengerException {
        assertEquals(0, p.getConfirmationTime());
    }

    @org.junit.Test
    public void testIsConfirmed() throws PassengerException {
        fail(); // Confirm if implementation is required or not
    }

    @org.junit.Test
    public void testIsFlown() throws PassengerException {
        fail(); // Confirm if implementation is required or not
    }

    @org.junit.Test
    public void testIsNew() throws PassengerException {
        fail(); // Confirm if implementation is required or not
    }

    @org.junit.Test
    public void testIsQueued() throws PassengerException {
        fail(); // Confirm if implementation is required or not
    }

    @org.junit.Test
    public void testIsRefused() throws PassengerException {
        fail(); // Confirm if implementation is required or not
    }

    @org.junit.Test
    public void testNoSeatsMsg1() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testQueuePassenger() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetNewState(true);
        p.queuePassenger(7, 12);
        assertTrue(p.isQueued());

    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_QueueInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetInQueue(true);
        p.queuePassenger(7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_ConfirmedInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetConfirmed(true);
        p.queuePassenger(7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_FlownInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetFlown(true);
        p.queuePassenger(7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_RefusedInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetRefused(true);
        p.queuePassenger(7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_NegativeQueueTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        p.queuePassenger(-7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_QueueTimePastDepartureTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
       p.queuePassenger(15, 12);
    }
    @org.junit.Test
    public void testRefusePassenger_NewInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetNewState(true);
        p.refusePassenger(7);
        assertTrue(p.isRefused());

    }
    @org.junit.Test
    public void testRefusePassenger_QueueInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetInQueue(true);
        p.refusePassenger(12);
        assertTrue(p.isRefused());

    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_isConfirmed() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetConfirmed(true);
        p.refusePassenger(7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_isRefused() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetRefused(true);
        p.refusePassenger(7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_isFlown() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        this.SetFlown(true);
        p.refusePassenger(7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_RefusalTimeCheck() throws PassengerException {
        p.refusePassenger(-2);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_RefusalTimeAgainstBookingTimeCheck() throws PassengerException {
        p.refusePassenger(1);
    }

    @org.junit.Test
    public void testUpgrade1() throws PassengerException {
        fail();
    }

    @org.junit.Test
    public void testWasConfirmed_TrueExpected() throws PassengerException {
        p.confirmSeat(6, 12);
        p.cancelSeat(7);
        assertTrue(p.wasConfirmed());
    }
    @org.junit.Test
    public void testWasConfirmed_FalseExpected() throws PassengerException {
        //TODO //Implement
        assertTrue(p.wasConfirmed());
    }

    @org.junit.Test
    public void testWasQueued_TrueExpected() throws PassengerException {
        p.queuePassenger(5, 12);
        assertTrue(p.wasQueued());
    }
    @org.junit.Test
    public void testWasQueued_FalseExpected() throws PassengerException {
        //TODO //Implement
        assertFalse(p.wasQueued());
    }
    @org.junit.Test
    public void testCopyPassengerState() {

    }
    private void SetConfirmed(boolean state) throws PassengerException, NoSuchFieldException, IllegalAccessException {
        f = null;
        f = c.getDeclaredField("confirmed");
        f.setAccessible(true);
        if(state) {
            f.setBoolean(p, true);
        }
        else {
            f.setBoolean(p, false);
        }
    }
    private void SetInQueue(boolean state) throws PassengerException, NoSuchFieldException, IllegalAccessException {
        f = null;
        f = c.getDeclaredField("inQueue");
        f.setAccessible(true);
        if(state) {
            f.setBoolean(p, true);
        }
        else {
            f.setBoolean(p, false);
        }
    }
    private void SetFlown(boolean state) throws PassengerException, NoSuchFieldException, IllegalAccessException {
        f = null;
        f = c.getDeclaredField("flown");
        f.setAccessible(true);
        if(state) {
            f.setBoolean(p, true);
        }
        else {
            f.setBoolean(p, false);
        }
    }
    private void SetRefused(boolean state) throws PassengerException, NoSuchFieldException, IllegalAccessException {
        f = null;
        f = c.getDeclaredField("refused");
        f.setAccessible(true);
        if(state) {
            f.setBoolean(p, true);
        }
        else {
            f.setBoolean(p, false);
        }
    }
    private void SetNewState(boolean state) throws PassengerException, NoSuchFieldException, IllegalAccessException {
        f = null;
        f = c.getDeclaredField("newState");
        f.setAccessible(true);
        if(state) {
            f.setBoolean(p, true);
        }
        else {
            f.setBoolean(p, false);
        }
    }

}