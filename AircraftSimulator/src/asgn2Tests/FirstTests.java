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

    //Testing Passenger Constructor
    @org.junit.Test
    public void testPassengerConstructor_BookingTime() throws PassengerException {
        assertEquals(3, p.getBookingTime());
    }
    @org.junit.Test
    public void testPassengerConstructor_IndexIncremented() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        f = c.getDeclaredField("index");
        f.setAccessible(true);
        int initialIndex = f.getInt(p);
        Passenger p2 = new First(7, 15);
        int finalIndex = f.getInt(p);
        assertEquals(1, finalIndex-initialIndex);
    }
    @org.junit.Test
    public void testPassengerConstructor_DepartureTime() throws PassengerException {
        assertEquals(12, p.getDepartureTime());
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
    public void testPassengerConstructor_NegativeDepartureTime() throws PassengerException {
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
        String str = p.noSeatsMsg();
        assertEquals(0, str.compareTo("No seats available in First"));
    }

    @org.junit.Test
    public void testUpgrade() throws PassengerException {
        Passenger test = p.upgrade();
        assertNull(test);
    }
    //Cancel Seat tests
    @org.junit.Test
    public void testCancelSeat_NewStateCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetConfirmed(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.cancelSeat(5);
        assertTrue(p.isNew());
    }
    @org.junit.Test
    public void testCancelSeat_BookingTimeCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetConfirmed(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.cancelSeat(5);
        assertEquals(5, p.getBookingTime());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_inValidCancelTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetConfirmed(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.cancelSeat(13);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_NegativeCancelTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetConfirmed(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.cancelSeat(-7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_StateCheckNew() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.cancelSeat(7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_StateCheckQueued() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetInQueue(true);
        SetNewState(false);
        SetConfirmed(false);
        SetRefused(false);
        SetFlown(false);
        p.cancelSeat(5);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_StateCheckFlown() throws NoSuchFieldException, IllegalAccessException, PassengerException {
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(true);
        p.cancelSeat(6);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_StateCheckRefused() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetRefused(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetFlown(false);
        p.cancelSeat(6);
    }
    @org.junit.Test
    public void testCancelSeat_SameDepartureTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetConfirmed(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.cancelSeat(6);
        assertEquals(12, p.getDepartureTime());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testCancelSeat_ConfirmedSeat() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetConfirmed(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.cancelSeat(6);
    }
    //Confirm Seat Tests
    @org.junit.Test
    public void testConfirmSeat_InitialStateNew() throws IllegalAccessException, NoSuchFieldException, PassengerException {
        SetNewState(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        SetConfirmed(false);
        p.confirmSeat(6, 12);
        assertTrue(p.isConfirmed());
    }
    @org.junit.Test
    public void testConfirmSeat_InitialStateQueuedCheck() throws IllegalAccessException, NoSuchFieldException, PassengerException {
        SetNewState(false);
        SetRefused(false);
        SetFlown(false);
        SetConfirmed(false);
        SetInQueue(true);
        p.confirmSeat(7, 12);
        assertTrue(p.isConfirmed());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testConfirmSeat_InitialStateConfirmed() throws IllegalAccessException, NoSuchFieldException, PassengerException {
        SetConfirmed(true);
        SetNewState(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.confirmSeat(6, 8);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testConfirmSeat_InitialStateRefused() throws IllegalAccessException, NoSuchFieldException, PassengerException {
        SetRefused(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetFlown(false);
        p.confirmSeat(7, 9);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testConfirmSeat_InitialStateFlown() throws IllegalAccessException, NoSuchFieldException, PassengerException {
        SetFlown(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        p.confirmSeat(6, 10);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testConfirmSeat_NegativeConfirmationTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.confirmSeat(-5, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testConfirmSeat_DepartureTimeLessThanConfirmationTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.confirmSeat(16, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testConfirmSeat_NegativeDepartureTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.confirmSeat(5, -12);
    }
    //Fly Passenger tests
    @org.junit.Test
    public void testFlyPassenger_InitialStateConfirmed() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetRefused(false);
        SetFlown(false);
        SetConfirmed(true);
        SetInQueue(false);
        p.flyPassenger(12);
        assertTrue(p.isFlown());
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_InitialStateNotConfirmed() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetConfirmed(false);
        SetNewState(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.flyPassenger(12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_NewStateCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.flyPassenger(12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_QueuedState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetInQueue(true);
        SetNewState(false);
        SetConfirmed(false);
        SetRefused(false);
        SetFlown(false);
        p.flyPassenger(12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_FlownState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetFlown(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        p.flyPassenger(12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_RefusedState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetRefused(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetFlown(false);
        p.flyPassenger(12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_DepartureTimeZero() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetConfirmed(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.flyPassenger(0);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testFlyPassenger_DepartureTimeNegative() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetConfirmed(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.flyPassenger(-5);
    }

    @org.junit.Test
    public void testGetConfirmationTime_SeatNotConfirmed() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetConfirmed(false);
        SetNewState(false);
        SetInQueue(true);
        SetRefused(false);
        SetFlown(false);
        assertEquals(0, p.getConfirmationTime());
    }
    @org.junit.Test
    public void testGetPassID() throws PassengerException {
        assertEquals(0, "F:0".compareTo(p.getPassID()));
    }

    @org.junit.Test
    public void testIsConfirmed() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetConfirmed(true);
        SetNewState(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        assertTrue(p.isConfirmed());
    }

    @org.junit.Test
    public void testIsFlown() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetFlown(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        assertTrue(p.isFlown());
    }

    @org.junit.Test
    public void testIsNew() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        assertTrue(p.isNew());
    }

    @org.junit.Test
    public void testIsQueued() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetInQueue(true);
        SetNewState(false);
        SetConfirmed(false);
        SetRefused(false);
        SetFlown(false);
        assertTrue(p.isQueued());
    }

    @org.junit.Test
    public void testIsRefused() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetRefused(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetFlown(false);
        assertTrue(p.isRefused());
    }

    @org.junit.Test
    public void testQueuePassenger() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.queuePassenger(7, 12);
        assertTrue(p.isQueued());

    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_QueueInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetInQueue(true);
        SetNewState(false);
        SetConfirmed(false);
        SetRefused(false);
        SetFlown(false);
        p.queuePassenger(7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_ConfirmedInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(false);
        SetConfirmed(true);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.queuePassenger(7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_FlownInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetFlown(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        p.queuePassenger(7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_RefusedInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetRefused(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetFlown(false);
        p.queuePassenger(7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_NegativeQueueTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.queuePassenger(-7, 12);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testQueuePassenger_QueueTimePastDepartureTime() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.queuePassenger(15, 12);
    }
    @org.junit.Test
    public void testRefusePassenger_NewInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.refusePassenger(7);
        assertTrue(p.isRefused());
    }
    @org.junit.Test
    public void testRefusePassenger_QueueInitialState() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetInQueue(true);
        SetNewState(false);
        SetConfirmed(false);
        SetRefused(false);
        SetFlown(false);
        p.refusePassenger(12);
        assertTrue(p.isRefused());

    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_isConfirmed() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetConfirmed(true);
        SetNewState(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.refusePassenger(7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_isRefused() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetRefused(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetFlown(false);
        p.refusePassenger(7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_isFlown() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetFlown(true);
        SetNewState(false);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        p.refusePassenger(7);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_RefusalTimeCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.refusePassenger(-2);
    }
    @org.junit.Test(expected = PassengerException.class)
    public void testRefusePassenger_RefusalTimeAgainstBookingTimeCheck() throws PassengerException, NoSuchFieldException, IllegalAccessException {
        SetNewState(true);
        SetConfirmed(false);
        SetInQueue(false);
        SetRefused(false);
        SetFlown(false);
        p.refusePassenger(1);
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