package hotelsystem.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotelsystem.controller.Cico_Manager;
import hotelsystem.controller.Room_Status_Manager;
import hotelsystem.entity.Cico;
import hotelsystem.entity.Guest;
import hotelsystem.entity.Room_Status;

<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/ui/CheckOut_UI.java

public class CheckOut_UI {
	private static CheckOut_UI instance = null;
    private Scanner sc;

    
    private CheckOut_UI() {
        sc = new Scanner(System.in);
    }
    
    
    public static CheckOut_UI getInstance() {
=======
public class CheckOutUI {
	private static CheckOutUI instance = null;
    private Scanner sc;

    private CheckOutUI() {
        sc = new Scanner(System.in);
    }
    
    public static CheckOutUI getInstance() {
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/ui/CheckOutUI.java
        if (instance == null) {
            instance = new CheckOut_UI();
        }
        return instance;
    }
	
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/ui/CheckOut_UI.java
    
=======
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/ui/CheckOutUI.java
	public void displayOptions() {
        int choice;
        try {
	        Guest guest = null;
	        String guestName;
	        sc = new Scanner(System.in);
	        do {
		        System.out.println("Enter Guest Name: ");
		        guestName = sc.nextLine();
		        guest = Guest_UI.getInstance().searchGuest(guestName);
	        }
	        while (guest==null);
	        
	        
	        Cico cico = Cico_Manager.getInstance().getGuestOut(guest.getGuest_ID());
	        if (cico!=null) {
	        	System.out.println(cico.getRoomStatus().size() + " Rooms Found");
	        	System.out.println("Room Booking ID		Room Number	Status			Checked-In Date			Check Out Date");
	        	ArrayList<Room_Status> rSlist = cico.getRoomStatus();
	        	for (Room_Status rL : rSlist) {
	        		System.out.println(rL.getRoomBookings_ID() +"			"+ rL.getRoomFloor_No() +"		"+ rL.getStatus() +"		"+ rL.getDate_from() +"	"+ rL.getDate_to());
	             }
	        	
	        	do {
	        	System.out.println("Select an option: ");
	        	System.out.println("1. Check out for all rooms and Print Bill Invoice");
	        	System.out.println("2. Check out for selected rooms");
	        	choice = sc.nextInt();
	            sc.nextLine();
		            switch (choice) {
		                case 1:
		                    checkOutAll(cico);
		                	return;
		                case 2:
		                    checkOutSelectedRooms(cico);
		                    return;
		                case 0:
		                    break;
		                default:
		                    System.out.println("Invalid Choice");
		                    break;
		            }
	        	} while (choice > 0);
	        }
	        else {
	        	System.out.println("No Rooms Found. Please Try Again.");
	        }

        }
        catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please insert again.");
        }
    }
	
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/ui/CheckOut_UI.java
	
	private void checkOutAll(Cico cico) {
		ArrayList<Room_Status> updateList = new ArrayList<Room_Status>();
=======
	private void checkOutAll(CheckInCheckOut cico) {
		ArrayList<RoomStatus> updateList = new ArrayList<RoomStatus>();
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/ui/CheckOutUI.java
		
		for (Room_Status roomStatus : cico.getRoomStatus()) {
			if(roomStatus.getStatus().equals("Checked-In")) {
				Room_Status roomS = new Room_Status(roomStatus.getRoomBookings_ID(),roomStatus.getRoomFloor_No(), roomStatus.getGuest_ID() , "Checked-Out", roomStatus.getDate_from(), roomStatus.getDate_to());
				Room_Status_Manager.getInstance().updateStatustoCheckedOut(roomS);
				updateList.add(roomS);
			}
			else {
        		updateList.add(roomStatus);
        	}
        }
		cico.setRoomStatus(updateList);
		cico.setStatus("Checked-Out");
		Cico_Manager.getInstance().updatecico(cico);
		System.out.println("All rooms has been checked-out successfully. Generating Bill Invoice...");
		Billing_UI.getInstance().generateBill(cico);
	}
	
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/ui/CheckOut_UI.java
	
	private void checkOutSelectedRooms(Cico cico) {
		ArrayList<Room_Status> updateList = new ArrayList<Room_Status>();
=======
	private void checkOutSelectedRooms(CheckInCheckOut cico) {
		ArrayList<RoomStatus> updateList = new ArrayList<RoomStatus>();
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/ui/CheckOutUI.java
		System.out.println("Please Select a Room No(xx-xx): ");
        String roomNo = sc.next();
        for (Room_Status roomStatus : cico.getRoomStatus()) {
        	if(roomStatus.getRoomFloor_No().equals(roomNo)) {
        		Room_Status roomS = new Room_Status(roomStatus.getRoomBookings_ID(),roomStatus.getRoomFloor_No(), roomStatus.getGuest_ID() , "Checked-Out", roomStatus.getDate_from(), roomStatus.getDate_to());
            	Room_Status_Manager.getInstance().updateStatustoCheckedOut(roomS);
            	updateList.add(roomS);
        	}
        	else {
        		updateList.add(roomStatus);
        	}
        }
        cico.setRoomStatus(updateList);
		System.out.println("Room Number: " + roomNo + " has been checked-out successfully.");
		boolean check = Cico_Manager.getInstance().getGuestFullOut(cico.getGuest().getGuest_ID());
		if(check) {
				Cico_Manager.getInstance().updatecico(cico);
		}
		else {
			cico.setStatus("Checked-Out");
			Cico_Manager.getInstance().updatecico(cico);
			System.out.println("All rooms has been checked-out successfully. Generating Bill Invoice...");
			Billing_UI.getInstance().generateBill(cico);
		}
	}
}
