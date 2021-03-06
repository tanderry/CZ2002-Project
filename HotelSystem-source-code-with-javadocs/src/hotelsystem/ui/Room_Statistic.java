package hotelsystem.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import hotelsystem.controller.Room_Manager;
import hotelsystem.controller.Room_Type_Manager;
import hotelsystem.entity.Room;
import hotelsystem.entity.Room_Type;


public class Room_Statistic {
	private static Room_Statistic instance = null;
	private final Scanner sc;
	private Date today = new Date( );
    private Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));

    
    private Room_Statistic() { 
        sc = new Scanner(System.in);
    }
    
    
    public static Room_Statistic getInstance() {
        if (instance == null) {
            instance = new Room_Statistic();
        }
        return instance;
    }
    
    
    public void displayOptions() {
        int choice;
        try {
	        do {
	            System.out.println("Print Room Status statistic report by:");
	            System.out.println("  1.Room type occupancy rate");
	            System.out.println("  2.Room status");
	            System.out.println("  0.Back to previous level");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                	    printRoomTypeOccupancyRate();
	                	    break;
	                case 2:
	                    	printRoomStatus();
	                    	break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Invalid Choice");
	                    break;
	            }
	        } while (choice > 0);
        }
        catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please insert again.");
        }
    } 
    
    
    private void printRoomTypeOccupancyRate() {
    		    
	    ArrayList<Room_Type> roomTList = Room_Type_Manager.getInstance().getAllTypes();
	    ArrayList<Room> roomList = Room_Manager.getInstance().getAllRoom(today,tomorrow);
	    ArrayList<Room> vacantRooms = new ArrayList<>();
	    
	    for (Room_Type rtList : roomTList) {
	    	for (Room rList : roomList) {
	    		if (rList.getRoomType() == rtList.getTypeSerial()) {
	    			vacantRooms.add(rList);
	    		}
		    }
	    	int totalRoomNo = Room_Manager.getInstance().getRoomTypeQty(rtList.getTypeSerial());
	    	
	    	System.out.println(rtList.getRoomType() +" : Number : "+ vacantRooms.size()  +" out of " + totalRoomNo);
	    	System.out.print("           Rooms: ");
	    	for (Room printRList : vacantRooms) {
	    		System.out.print(printRList.getRoomFloorNo() + " ");
	    	}
	    	System.out.println("");
	    	
	    	vacantRooms.clear();
	    }
    }
    
    
    private void printRoomStatus() {
	    System.out.println("Vacant:");
	    ArrayList<Room> vacantroomList = Room_Manager.getInstance().getAllRoom(today,tomorrow);
    	System.out.print("		Rooms: ");
    	int i = 0;
    	for (Room printRList : vacantroomList) {
    		if (i<10) {
    			System.out.print(printRList.getRoomFloorNo() + " ");
    			i++;
    		}
    		else {
    			System.out.println(printRList.getRoomFloorNo() + " ");
    			i=0;
    		}
    	}
	    System.out.println();
	    ArrayList<Room> occupiedroomList = Room_Manager.getInstance().getAllRooms();
	    ArrayList<Room> mroomList = Room_Manager.getInstance().getAllMaintenanceRoom();
	    System.out.println("Occupied:");
	    System.out.print("		Rooms:");
	    	for (Room vRList : vacantroomList) {
	    			occupiedroomList.remove(vRList);
	    	}
	    	for (Room mRList : mroomList) {
    			occupiedroomList.remove(mRList);
	    	}
	    
	    
	    for (Room printRList : occupiedroomList) {
    		if (i<10) {
    			System.out.print(printRList.getRoomFloorNo() + " ");
    			i++;
    		}
    		else {
    			System.out.println();
    			i=0;
    		}
    	}
	    System.out.println();
	    System.out.println("Maintenance:");
	    System.out.print("		Rooms:");
	    for (Room printRList : mroomList) {
    		if (i<10) {
    			System.out.print(printRList.getRoomFloorNo() + " ");
    			i++;
    		}
    		else {
    			System.out.println();
    			i=0;
    		}
    	}

	    System.out.println();
	    
	    vacantroomList.clear();
	    occupiedroomList.clear();
	    mroomList.clear();
    }
}