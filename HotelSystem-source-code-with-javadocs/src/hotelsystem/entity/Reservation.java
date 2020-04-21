package hotelsystem.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Reservation implements Serializable{
	private static int incID = 1;
	private int reservation_ID;
	private String reservation_code;
	private Guest guest;
	private ArrayList<Room_Status> statusList = new ArrayList<>();
	private int numChildren;
	private int numAdults;
	private String status;
	
	public Reservation(Guest guest, ArrayList<Room_Status> statusList, 
			int noChild, int noAdult, String status) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
    	LocalDate localDate = LocalDate.now();
    	
		this.reservation_ID = incID;
		this.reservation_code = "R" + incID + dtf.format(localDate);
		this.guest = guest;
		this.statusList = statusList;
		this.numChildren = noChild;
		this.numAdults = noAdult;
		this.status = status;
		incID++;
	}
	
	public Reservation(Guest guest, int noChild, int noAdult, String status) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
    	LocalDate localDate = LocalDate.now();
    	
		this.reservation_ID = incID;
		this.reservation_code = "R" + incID + dtf.format(localDate);
		this.guest = guest;
		this.numChildren = noChild;
		this.numAdults = noAdult;
		this.status = status;
		incID++;
	}
	
	public int getReservationID() { return this.reservation_ID; }
	
	public static int getIncID() { return Reservation.incID; }
	
	public static void setIncID(int ID) { Reservation.incID = ID; }
	
	public String getReservationCode() { return reservation_code; }
	
	public void setReservationCode(String reserveCode) { this.reservation_code = reserveCode; }
	
	public Guest getGuest() { return guest; }
	
	public ArrayList<Room_Status> getStatusList(){ return statusList; }

	public int getNumberOfChildren() { return numChildren; }

	public void setNumberOfChildren(int numChildren) { this.numChildren = numChildren; }

	public int getNumberOfAdults() { return numAdults; }

	public void setNumberOfAdults(int numAdults) { this.numAdults = numAdults; }

	public String getStatus() { return status; }

	public void setStatus(String status) { this.status = status; }
	
}
