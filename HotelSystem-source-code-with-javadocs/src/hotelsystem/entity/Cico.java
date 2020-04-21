package hotelsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;

<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/entity/Cico.java

=======
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/entity/CheckInCheckOut.java
@SuppressWarnings("serial")
public class Cico implements Serializable{
	private static int maxID = 1;
	private int CheckInCheckOut_ID;
	private Guest guest;
	private int numChildren;
	private int numAdults;
	private String status;
	private ArrayList<Room_Status> roomStatus = new ArrayList<>();
	private Billing bill;

	public Cico(int checkInCheckOut_ID, Guest guest, int numChildren, int numAdults,
			ArrayList<Room_Status> roomStatus, String status, Billing bill) {
		CheckInCheckOut_ID = maxID;
		this.guest = guest;
		this.numChildren = numChildren;
		this.numAdults = numAdults;
		this.roomStatus = roomStatus;
		this.status = status;
		this.bill = bill;
		maxID++;
	}
	
	public Cico(Guest guest, int numChildren, int numAdults,
			ArrayList<Room_Status> roomStatus, String status, Billing bill) {
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

	public static void setMaxID(int maxID) { Cico.maxID = maxID; }

	public int getCheckInCheckOut_ID() { return CheckInCheckOut_ID; }

	public void setCheckInCheckOut_ID(int checkInCheckOut_ID) { CheckInCheckOut_ID = checkInCheckOut_ID; }

	public Guest getGuest() { return guest; }

	public void setGuest(Guest guest) { this.guest = guest; }

	public int getnumChildren() { return numChildren; }

	public void setnumChildren(int numChildren) { this.numChildren = numChildren; }

	public int getnumAdults() { return numAdults; }

	public void setnumAdults(int numAdults) { this.numAdults = numAdults; }

	public ArrayList<Room_Status> getRoomStatus() { return roomStatus; }

	public void setRoomStatus(ArrayList<Room_Status> roomStatus) { this.roomStatus = roomStatus; }

	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }

	public Billing getBill() { return bill;}

	public void setBill(Billing bill) { this.bill = bill; }
}
