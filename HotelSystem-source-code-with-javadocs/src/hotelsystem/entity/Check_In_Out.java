package hotelsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Check_In_Out implements Serializable{
	private static int maxID = 1;
	private int CheckInCheckOut_ID;
	private Guest guest;
	private int child;
	private int adults;
	private String status;
	private ArrayList<RoomStatus> roomStatus = new ArrayList<>();
	private BillPayment bill;

	public Check_In_Out(int checkInCheckOut_ID, Guest guest, int child, int adults,
			ArrayList<RoomStatus> roomStatus, String status, BillPayment bill) {
		CheckInCheckOut_ID = maxID;
		this.guest = guest;
		this.child = child;
		this.adults = adults;
		this.roomStatus = roomStatus;
		this.status = status;
		this.bill = bill;
		maxID++;
	}
	
	public Check_In_Out(Guest guest, int child ,int adults,
			ArrayList<RoomStatus> roomStatus, String status, BillPayment bill) {
		CheckInCheckOut_ID = maxID;
		this.guest = guest;
		this.child = child;
		this.adults = adults;
		this.roomStatus = roomStatus;
		this.status = status;
		this.bill = bill;
		maxID++;
	}
	
	public static int getMaxID() { return maxID; }

	public static void setMaxID(int maxID) { Check_In_Out.maxID = maxID; }

	public int getCheckInCheckOut_ID() { return CheckInCheckOut_ID; }

	public void setCheckInCheckOut_ID(int checkInCheckOut_ID) { CheckInCheckOut_ID = checkInCheckOut_ID; }

	public Guest getGuest() { return guest; }

	public void setGuest(Guest guest) { this.guest = guest; }

	public int getNumber_of_children() { return child; }

	public void setNumber_of_children(int child) { this.child = child; }

	public int getNumber_of_adults() { return adults; }

	public void setNumber_of_adults(int adults) { this.adults = adults; }

	public ArrayList<RoomStatus> getRoomStatus() { return roomStatus; }

	public void setRoomStatus(ArrayList<RoomStatus> roomStatus) { this.roomStatus = roomStatus; }

	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }

	public BillPayment getBill() { return bill;}

	public void setBill(BillPayment bill) { this.bill = bill; }
}
