/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Passengers;


import asgn2Simulators.Constants;

import java.util.Random;

/**
 * Passenger is an abstract class specifying the basic state of an airline passenger,  
 * and providing methods to set and access that state. A passenger is created upon booking, 
 * at which point the reservation is confirmed, or the passenger goes on to a waiting list. 
 * If the waiting list is full, then the passenger is either bumped to the next available 
 * service or lost altogether from the system. A passenger lost from the system is recorded 
 * and their fare level used in the calculation of lost revenue.<br><br>
 * 
 * Passengers have the following states and permitted transitions:<br>
 * State: New - on creation; immediately transition to {Confirmed,Queued,Refused}
 * <ul>
 * <li>{@link #queuePassenger(int, int)}  | New -> Queued</li>
 * <li>{@link #confirmSeat(int,int)} | New -> Confirmed</li>
 * <li>{@link #refusePassenger(int)} | New -> Refused</li> 
 * </ul>
 * <br>
 * State: Queued - on central waiting list to see if seat available. 
 * Transitions to {Confirmed,Refused}
 * <ul>
 * <li>{@link #confirmSeat(int,int)} | Queued -> Confirmed; up until departureTime</li>
 * <li>{@link #refusePassenger(int)} | Queued -> Refused; finalised on departureTime</li> 
 * </ul>
 * <br>
 * State: Confirmed - seat confirmed on requested flight. Transitions to {New,Flown}
 * <ul>
 * <li>{@link #cancelSeat(int)}   | Confirmed -> New; up until departureTime</li>
 * <li>{@link #flyPassenger(int)} | Confirmed -> Flown; finalised on departureTime</li> 
 * </ul>
 * <br>
 * State: Refused - final state - no transitions permitted<br> 
 * State: Flown - final state - no transitions permitted<br>
 * <br>
 * {@link asgn2Passengers.PassengerException} is thrown if the state is inappropriate: see method javadoc for details. 
 * The method javadoc also indicates the constraints on the time and other parameters.<br><br>
 * 
 * @author hogan
 *
 */
public abstract class Passenger {

	private static int index = 0;
	protected String passID;
	protected boolean newState;
	protected boolean confirmed;
	protected boolean inQueue;
	protected boolean flown;
	protected boolean refused;
	protected int bookingTime;
	protected int enterQueueTime;
	protected int exitQueueTime;
	protected int confirmationTime;
	protected int departureTime; 
	
	
	/**
	 * Passenger Constructor 
	 * On creation, Passenger is in a New state. Subsequent processing transitions to a 
	 * confirmed reservation, queued, or the booking is refused if waiting list is full. 
	 * 
	 * @param bookingTime <code>int</code> day of the original booking. 
	 * @param departureTime <code>int</code> day of the intended flight. 
	 * @throws PassengerException if (bookingTime < 0) OR (departureTime <= 0) 
	 * OR (departureTime < bookingTime) 
	 */
	public Passenger(int bookingTime, int departureTime) throws PassengerException  {
		if (bookingTime < 0){
			throw new PassengerException("Booking time for passenger (" + Passenger.index + ") was negative.");
		}

		if (departureTime <= 0){
			throw new PassengerException("Departure time for passenger (" + Passenger.index + ") was less than or equal to 0.");
		}

		if (departureTime < bookingTime){
			throw new PassengerException("Departure time (" + departureTime + ") for passenger " +
					"(" + Passenger.index + ") less than the booking time (" + bookingTime + ".)");
		}

		this.bookingTime = bookingTime;
		this.departureTime = departureTime;
		this.newState = true;
		this.flown = false;
		this.confirmed = false;
		this.refused = false;
		this.inQueue = false;
		this.enterQueueTime = 0;
		this.exitQueueTime = 0;
		this.confirmationTime = 0;
		this.passID = "" + Passenger.index;
		Passenger.index++;

		//TODO: Stuff here
	}
	
	/**
	 * Simple no-argument constructor to support {@link #upgrade()}
	 */
	protected Passenger() {

	}
	
	/**
	 * Transition passenger to New<br>
	 * PRE: isConfirmed(this)<br>
	 * <ul>
	 * <li>cancelSeat: Confirmed -> New; up until departureTime</li>
	 * </ul>
	 * 
	 * Cancellation returns booking to New state; Booking time is adjusted to this 
	 * cancellation time and Passenger is processed as a new booking. Cannot guarantee 
	 * either a new confirmed seat or a place in the queue. DepartureTime remains the same 
	 * initially, adjusted by subsequent method calls. 
	 *  
	 * @param cancellationTime <code>int</code> holding cancellation time 
	 * @throws PassengerException if isNew(this) OR isQueued(this) OR isRefused(this) OR 
	 *         isFlown(this) OR (cancellationTime < 0) OR (departureTime < cancellationTime)
	 */
	public void cancelSeat(int cancellationTime) throws PassengerException {
		if (this.isNew()){
			throw new PassengerException("Passenger (" + this.passID + ") is in new state and cannot cancel their seat");
		}
		if (this.isQueued()){
			throw new PassengerException("Passenger (" + this.passID + ") is in queued state and cannot cancel their seat");
		}
		if (this.isRefused()){
			throw new PassengerException("Passenger (" + this.passID + ") is in refused state and cannot cancel their seat");
		}
		if (this.isFlown()){
			throw new PassengerException("Passenger (" + this.passID + ") is in flown state and cannot cancel their seat");
		}

		if (cancellationTime < 0){
			throw new PassengerException("Cancellation time is negative for passenger " + this.passID + ", and cannot cancel their seat");
		}

		if (this.departureTime < cancellationTime){
			throw new PassengerException("Cancellation time (" + cancellationTime + ")is less than departure time (" + this.departureTime + ") for passenger "
					+ this.passID + ", and cannot cancel their seat");
		}

		if(this.isConfirmed()){
			this.newState = true;
			this.confirmed = false;
			this.bookingTime = cancellationTime;
		}
	}

	/**
	 * Transition passenger to Confirmed seat<br>
	 * PRE: isNew(this) OR inQueue(this)<br>
	 * <ul>
	 * <li>confirmSeat: New -> Confirmed</li>
	 * <li>confirmSeat: Queued -> Confirmed; up until departureTime</li> 
	 * </ul>
	 * 
	 * @param confirmationTime <code>int</code> day when seat is confirmed
	 * @param departureTime <code>int</code> day flight is scheduled to depart 
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this)
	 * 		   OR (confirmationTime < 0) OR (departureTime < confirmationTime)
	 */
	public void confirmSeat(int confirmationTime, int departureTime) throws PassengerException {
		if (this.isConfirmed()){
			throw new PassengerException("Passenger (" + this.passID + ") is in a confirmed state and cannot reconfirm their seat");
		}
		if (this.isRefused()){
			throw new PassengerException("Passenger (" + this.passID + ") is in a refused state and cannot confirm their seat");
		}
		if (this.isFlown()){
			throw new PassengerException("Passenger (" + this.passID + ") is in a flown state and cannot confirm their seat");
		}

		if (confirmationTime < 0){
			throw new PassengerException("Confirmation time is negative for passenger: " + this.passID + ", and cannot confirm their seat ");
		}

		if (this.departureTime < confirmationTime){
			throw new PassengerException("Departure time (" + this.departureTime + ") is less than confirmation time (" + this.confirmationTime + ") " +
					"for passenger " + this.passID + ", and cannot confirm their seat");
		}

		if( this.isNew() || this.isQueued()) {
			if(this.isQueued()){
				this.exitQueueTime = confirmationTime;
			}

			this.newState = false;
			this.inQueue = false;
			this.confirmed = true;
			this.confirmationTime = confirmationTime;
			this.departureTime = departureTime;
		}
	}

	/**
	 * Transition passenger to Flown<br>
	 * PRE: isConfirmed(this)<br>
	 * <ul>
	 * <li>flyPassenger:Confirmed -> Flown; finalised on departureTime</li>
	 * </ul>
	 *  
	 * @param departureTime <code>int</code> holding actual departure time 
	 * @throws PassengerException if isNew(this) OR isQueued(this) OR isRefused(this) OR 
	 *         isFlown(this) OR (departureTime <= 0)
	 */
	public void flyPassenger(int departureTime) throws PassengerException {
		if (this.isNew()){
			throw new PassengerException("Passenger (" + this.passID + ") is in a new state and cannot be flown");
		}
		if (this.isRefused()){
			throw new PassengerException("Passenger (" + this.passID + ") is in a refused state and cannot be flown");
		}
		if (this.isQueued()){
			throw new PassengerException("Passenger (" + this.passID + ") is in a queued state and cannot be flown");
		}
		if (this.isFlown()){
			throw new PassengerException("Passenger (" + this.passID + ") is in a flown state and cannot be flown again");
		}

		if (departureTime <= 0){
			throw new PassengerException("Departure time is less than or equal to 0 for passenger " + this.passID + ", and they cannot be flown");
		}

		if(this.isConfirmed()){
			this.confirmed = false;
			this.flown = true;
			this.departureTime = departureTime;
		}
	}

	/**
	 * Simple getter for the booking time 
	 * 
	 * @return the bookingTime
	 */
	public int getBookingTime() throws PassengerException {
		return this.bookingTime;
	}

	/**
	 * Simple getter for the confirmation time
	 * Note: result may be 0 prior to confirmation 
	 * 
	 * @return the confirmationTime
	 */
	public int getConfirmationTime() throws PassengerException {
		return this.confirmationTime;
	}

	/**
	 * Simple getter for the departureTime
	 * 
	 * @return the departureTime
	 */
	public int getDepartureTime() throws PassengerException {
		return this.departureTime;
	}
	
	/**
	 * Simple getter for the queue entry time
	 * 
	 * @return the enterQueueTime
	 */
	public int getEnterQueueTime() throws PassengerException {
		return this.enterQueueTime;
	}

	/**
	 * Simple getter for the queue exit time
	 * 
	 * @return the exitQueueTime
	 */
	public int getExitQueueTime() throws PassengerException {
		return this.exitQueueTime;
	}

	/**
	 * Simple getter for the Passenger ID
	 * 
	 * @return the passID
	 */
	public String getPassID() throws PassengerException {
		return this.passID;
	}

	/**
	 * Boolean status indicating whether Passenger has a Confirmed seat
	 * 
	 * @return <code>boolean</code> true if Confirmed state; false otherwise 
	 */
	public boolean isConfirmed() throws PassengerException {
		return this.confirmed;
	}
		
	/**
	 * Boolean status indicating whether Passenger has flown
	 * 
	 * @return <code>boolean</code> true if Flown state; false otherwise 
	 */
	public boolean isFlown() throws PassengerException {
		return this.flown;
	}
	
	/**
	 * Boolean status indicating whether Passenger is New
	 * 
	 * @return <code>boolean</code> true if New state; false otherwise 
	 */
	public boolean isNew() throws PassengerException {
		return this.newState;
	}

	/**
	 * Boolean status indicating whether Passenger is currently Queued
	 * 
	 * @return <code>boolean</code> true if Queued state; false otherwise 
	 */
	public boolean isQueued() throws PassengerException {
		return this.inQueue;
	}
	
	/**
	 * Boolean status indicating whether Passenger is Refused
	 * 
	 * @return <code>boolean</code> true if Refused state; false otherwise 
	 */
	public boolean isRefused() throws PassengerException {
		return this.refused;
	}
	
	/**
	 * Abstract helper method to allow specialised "no seats" message (Supplied) 
	 * 
	 * @return <code>String</code> containing "no seats available" warning message
	 */
	public abstract String noSeatsMsg() throws PassengerException;
	
	/**
	 * Transition passenger to Queued<br>
	 * PRE: isNew(this)<br>
	 * <ul>
	 * <li>queuePassenger: New -> Queued</li>
	 * </ul>
	 * 
	 * Queue on booking if seat is not available. Intended departureTime may be set here if 
	 * required. Queuing ceases on confirmation or when the departure time is reached. 
	 * 
	 * @param queueTime <code>int</code> holding queue entry time 
	 * @param departureTime <code>int</code> holding intended departure time 
	 * @throws PassengerException if isQueued(this) OR isConfirmed(this) OR isRefused(this) OR 
	 *         isFlown(this) OR (queueTime < 0) OR (departureTime < queueTime)
	 */
	public void queuePassenger(int queueTime, int departureTime) throws PassengerException {
		if (this.isQueued()){
			throw new PassengerException("Passenger (" + this.passID + ") is already queued and cannot be queued again");
		}
		if (this.isConfirmed()){
			throw new PassengerException("Passenger (" + this.passID + ") has already been confirmed and cannot be queued");
		}
		if (this.isRefused()){
			throw new PassengerException("Passenger (" + this.passID + ") has already been refused and cannot be queued");
		}
		if (this.isFlown()){
			throw new PassengerException("Passenger (" + this.passID + ") has already flown and cannot be queued");
		}

		if (queueTime < 0){
			throw new PassengerException("Queue time is negative for passenger " + this.passID + ", and they cannot be flown");
		}
		if (departureTime < queueTime){
			throw new PassengerException("Departure time (" + departureTime + ") is less than queue time (" + queueTime + ") for passenger " + this.passID + ", and they cannot be queued");
		}

		if (isNew()){
			this.inQueue = true;
			this.newState = false;

			this.enterQueueTime = queueTime;
		}
	}
	
	/**
	 * Transition passenger to Refused<br>
	 * PRE: isNew(this) OR isQueued(this) <br>
	 * <ul>
	 * <li>refusePassenger:New -> Refused</li> 
     * <li>refusePassenger:Queued -> Refused; finalised on departureTime</li> 
     * </ul>
     * 
	 * @param refusalTime <code>int</code> holding refusal time  
	 * @throws PassengerException if isConfirmed(this) OR isRefused(this) OR isFlown(this) 
	 * 			OR (refusalTime < 0) OR (refusalTime < bookingTime)
	 */
	public void refusePassenger(int refusalTime) throws PassengerException {
		if (this.isConfirmed()){
			throw new PassengerException("Passenger (" + this.passID + ") is already confirmed and cannot be refused");
		}
		if (this.isRefused()){
			throw new PassengerException("Passenger (" + this.passID + ") has already been confirmed and cannot be refused again");
		}
		if (this.isFlown()){
			throw new PassengerException("Passenger (" + this.passID + ") has already been flown and cannot be refused");
		}

		if (refusalTime < 0){
			throw new PassengerException("Refusal time is negative for passenger " + this.passID + ", and they cannot be refused");
		}
		if (refusalTime < this.bookingTime){
			throw new PassengerException("Refusal time (" + refusalTime + ") is less than the booking time (" + this.bookingTime + ") for passenger " + this.passID + ", so they cannot be refused");
		}

		if (this.isNew() || this.isQueued()){
			this.refused = true;
			this.newState = false;
			this.inQueue = false;

			this.exitQueueTime = refusalTime;
		}
	}
	
	/* (non-Javadoc) (Supplied) 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "passID: " + passID + "\nBT: " + this.bookingTime; 
		//Queuing Information - duration may not be accurate if multiple queuing events 
		try {
			if (this.wasQueued()) {
                str += "\nExitQ:" + this.exitQueueTime;
                int queueTime = (this.exitQueueTime - this.bookingTime);
                str += " QT: " + queueTime;
            } else {
                str += "\nNotQ";
            }
		} catch (PassengerException e) {
			e.printStackTrace();
		}

		try {
			if (this.isFlown()) {
                str+= "\nConfT: " + this.confirmationTime;
                str+= " Flew: " + this.departureTime;
            } else if (this.wasConfirmed()) {
                str+= "\nConfT: " + this.confirmationTime;
                str+= " NotFlown";
            }
		} catch (PassengerException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * Method to create new Passenger instance based on upgrade to a higher fare class.
	 * Transition rules are specific to each fare class
	 * 
	 * @return <code>Passenger</code> of the upgraded fare class 
	 */
	public abstract Passenger upgrade() throws PassengerException;

	/**
	 * Boolean status indicating whether passenger was ever confirmed
	 * 
	 * @return <code>boolean</code> true if was Confirmed state; false otherwise
	 */
	public boolean wasConfirmed() throws PassengerException {
		if (this.confirmationTime != 0){
			return true;
		}

		return false;
	}

	/**
	 * Boolean status indicating whether passenger was ever queued
	 * 
	 * @return <code>boolean</code> true if was Queued state; false otherwise
	 */
	public boolean wasQueued() throws PassengerException {
		if (this.enterQueueTime != 0){
			return true;
		}

		return false;
	}

	/**
	 * Helper method to copy state to facilitate {@link #upgrade()}
	 * 
	 * @param <code>Passenger</code> state to transfer
	 */
	protected void copyPassengerState(Passenger p) {
		this.newState = p.newState;
		this.flown = p.flown;
		this.refused = p.refused;
		this.inQueue = p.inQueue;
		this.confirmed = p.confirmed;

		this.bookingTime = p.bookingTime;
		this.confirmationTime = p.confirmationTime;
		this.departureTime = p.departureTime;
		this.enterQueueTime = p.enterQueueTime;
		this.exitQueueTime = p.exitQueueTime;

		// FIXME: This changes right?
		this.passID = p.passID;
	}
	
	//Various private helper methods to check arguments and throw exceptions

}
