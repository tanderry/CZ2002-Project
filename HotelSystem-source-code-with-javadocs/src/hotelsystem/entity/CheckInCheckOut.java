package hotelsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description for Check In Check Out
 * contains get & set methods required for Check In Check Out
 * @since 17/04/2018
 * @version 1.0
 * @author Kan Kah Seng
 */
@SuppressWarnings("serial")
public class CheckInCheckOut implements Serializable{
	private static int maxID = 1;
	private int CheckInCheckOut_ID;
	private Guest guest;
	private int numChildren;
	private int numAdults;
	private String status;
	private ArrayList<RoomStatus> roomStatus = new ArrayList<>();
	private BillPayment bill;

	public CheckInCheckOut(int checkInCheckOut_ID, Guest guest, int numChildren, int numAdults,
			ArrayList<RoomStatus> roomStatus, String status, BillPayment bill) {
		CheckInCheckOut_ID = maxID;
		this.guest = guest;
		this.numChildren = numChildren;
		this.numAdults = numAdults;
		this.roomStatus = roomStatus;
		this.status = status;
		this.bill = bill;
		maxID++;
	}
	
	public CheckInCheckOut(Guest guest, int numChildren, int numAdults,
			ArrayList<RoomStatus> roomStatus, String status, BillPayment bill) {
		CheckInCheckOut_ID = maxID;
		this.guest = guest;
		this.numChildren = numChildren;
		this.numAdults = numAdults;
		this.roomStatus = roomStatus;
		this.status = status;
		this.bill = bill;
		maxID++;
	}
	
	public static int getMaxID() { return maxID; }

	public static void setMaxID(int maxID) { CheckInCheckOut.maxID = maxID; }

	public int getCheckInCheckOut_ID() { return CheckInCheckOut_ID; }

	public void setCheckInCheckOut_ID(int checkInCheckOut_ID) { CheckInCheckOut_ID = checkInCheckOut_ID; }

	public Guest getGuest() { return guest; }

	public void setGuest(Guest guest) { this.guest = guest; }

	public int getnumChildren() { return numChildren; }

	public void setnumChildren(int numChildren) { this.numChildren = numChildren; }

	public int getnumAdults() { return numAdults; }

	public void setnumAdults(int numAdults) { this.numAdults = numAdults; }

	public ArrayList<RoomStatus> getRoomStatus() { return roomStatus; }

	public void setRoomStatus(ArrayList<RoomStatus> roomStatus) { this.roomStatus = roomStatus; }

	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }

	public BillPayment getBill() { return bill;}

	public void setBill(BillPayment bill) { this.bill = bill; }
}
