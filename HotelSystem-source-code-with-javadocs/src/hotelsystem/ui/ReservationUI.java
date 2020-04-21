package hotelsystem.ui;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import hotelsystem.controller.Reservation_Manager;
import hotelsystem.controller.Room_Manager;
import hotelsystem.controller.Room_Status_Manager;
import hotelsystem.controller.Room_Type_Manager;
import hotelsystem.entity.Guest;
import hotelsystem.entity.Reservation;
import hotelsystem.entity.Room;
import hotelsystem.entity.Room_Status;
import hotelsystem.entity.Room_Type;

public class ReservationUI {
	private static ReservationUI instance = null;
	private Scanner sc;
	
	private ReservationUI() {
		sc = new Scanner(System.in);
	}
	
	public static ReservationUI getInstance() {
		if (instance == null) {
            instance = new ReservationUI();
        }
        return instance;
	}
	
	private static void creationComplete(Reservation r) {
		System.out.print("Reservation :"  + r.getReservationCode()  + " has been CREATED.\n");  
    }

	private static void updateComplete(Reservation reservation) {
    	System.out.println("Reservation :" + reservation.getReservationCode() +  " has been UPDATED.");  
    }
	
	public void displayOptions() {
		Reservation_Manager.getInstance().checkExpiredRoom();
        int choice;
        do {
			System.out.println("__________ RESERVATION MENU __________");
			System.out.println("");
            System.out.println("1. Create Reservation");
            System.out.println("2. Update Reservation Details");
            System.out.println("3. Remove Reservations");
            System.out.println("4. Print Reservation");
            System.out.println("5. Retrieve all Wait List");
            System.out.println("0. Back");
            System.out.println("______________________________________");
            System.out.print("Pick a choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1: createReservationDetails();
                		break;
                case 2: retrieveUpdateReservation();
                    	break;
                case 3: removeReservation();
                    	break;
                case 4: printReservationMenu();
                		break;
                case 5: retrieveWaitList();
                		break;
                case 0: break;
                default:System.out.println("Invalid Choice");
                    	break;
            }
        } while (choice > 0);
    }
	
	private void createReservationDetails() {
        sc = new Scanner(System.in);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
           
    	Guest guest = null; 						
    	ArrayList<Room_Status> statusList = new ArrayList<Room_Status>();
    	Boolean addRoomB = null;
        ArrayList<String> roomNoList = new ArrayList<>();
        String roomNo = null;
    	int roomType;
    	String dateFrom;
        String dateTo;
        Date startDate = null;
        Date endDate = null;
    	int noChild;									
    	int noAdult;									
    	String status = "Reserved";		
    	String guestName;  
    	String wifi, smoke, view;

    	System.out.println("1. Existing Guest\n"
    					+  "2. New Guest");
    	System.out.print("Are you a/an ? ");
    	int choiceGuest = sc.nextInt();
    	switch(choiceGuest) {
    		case 1:	do {
				        System.out.println("Enter Guest Name: ");
				        guestName = sc.next();
				        guest = Guest_UI.getInstance().searchGuest(guestName);
			        	}
			        while (guest==null);
    				break;
    		case 2:	guest = Guest_UI.getInstance().createnewGuest();
    				if(guest == null) {
    					
    				}
    				break;
    	}
    	System.out.print("Enter Number of Adults: ");
        noAdult = sc.nextInt();
        System.out.print("Enter Number of Childrens: ");
        noChild = sc.nextInt();
        System.out.print("Enter Date From (dd/MM/yyyy): ");
        dateFrom = sc.next();
        System.out.print("Enter Date To (dd/MM/yyyy): ");
        dateTo = sc.next();
        try {
            startDate = df.parse(dateFrom + " 12:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endDate = df.parse(dateTo + " 12:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        do {
            Room_Type_Manager.getInstance().displayAllRoomType();
            System.out.print("Which Room Do You Want? ");
            roomType = sc.nextInt();
            Room_Type_Manager.getInstance().getRoom(roomType);
            
           ArrayList<Room> tempRoomList = new ArrayList<>();
           tempRoomList = checkExisting(startDate, endDate, roomType, roomNoList);
           
           if(tempRoomList == null) {
        	   System.out.println("Do you wish to be put on waitlist? (Y/N)");
        	   char wait = sc.next().charAt(0);
        	   if(wait == 'Y' || wait == 'y') {
        		   status = "WaitList";
        		   addRoomB = false;
        		   Boolean waitListRoom = null;
        		   do {
	        		   tempRoomList = waitListExisting(startDate, endDate, roomType, roomNoList);
	        		   
	        		   System.out.print("Do you want to filter? (Y/N) :");
	                   char filterChoice = sc.next().charAt(0);
	                   if(filterChoice=='Y' || filterChoice=='y') {
	                   	ArrayList<Room> filterList = filterRoom(tempRoomList, roomNoList, roomType);
						System.out.println("__________________________ ROOMS AVAILABLE __________________________");
						System.out.println("");
	            		System.out.println(String.format("%5s %15s %8s %15s %15s", "Room No", "Room Type", "Wifi" , "Smoking Room", "Window View"));
	                   	if(!filterList.isEmpty()) {
	            			for (Room room : filterList) {
	            				if(!room.getRoomFloorNo().equals(roomNo)) {
	            					Room_Type rT = Room_Type_Manager.getInstance().getRoom(room.getRoomType());
	            					if(room.isWifi() == true) {
	            						wifi = "Yes";
	            					}else {
	            						wifi = "No";
	            					}
	            					if(room.isSmoking() == true) {
	            						smoke = "Yes";
	            					}else {
	            						smoke = "No";
	            					}
	            					if(room.isView() == true) {
	            						view = "Yes";
	            					}else {
	            						view = "No";
	            					}
	            					System.out.println(String.format("%5s %15s %9s %12s %14s", room.getRoomFloorNo(), rT.getRoomType(), wifi, smoke, view));
	            				}
	            			}
	            		}
	                   	else {
	                   		System.out.println("No Available Rooms for for your chosen dates");
	                   		System.out.println("_________________________________________________________________");
	                   		return;
	                   	}
	                   		System.out.println("_________________________________________________________________");
	                   }
	                   else if(filterChoice=='N' || filterChoice=='n') {}
	                   else {
	                	   System.out.println("Invalid Input!");
	                   }
	                   
	                   System.out.print("Please Select a Room No(xx-xx): ");
	                   roomNo = sc.next();
	                   roomNoList.add(roomNo);
	                   
	                   Room_Status roomStatus = new Room_Status(roomNo, guest.getGuest_ID() , "WaitList", startDate, endDate);
	                   statusList.add(roomStatus);
	                   
	    	           System.out.print("Do you wish to add additional rooms? (Y/N): ");
	    	           char addRoom = sc.next().charAt(0);    	
	    	           if(addRoom == 'Y' || addRoom == 'y')
	    	        	   waitListRoom = true;
	    	           else if(addRoom == 'N' || addRoom == 'n')
	    	        	   waitListRoom = false;
        		   }while(waitListRoom);
        	   }
        	   else if (wait == 'N' || wait == 'n'){
        		   return;
        	   }
           }
           else {
        	   System.out.print("Do you want to filter? (Y/N) :");
               char filterChoice = sc.next().charAt(0);
               if(filterChoice=='Y' || filterChoice=='y') {
               	ArrayList<Room> filterList = filterRoom(tempRoomList, roomNoList, roomType);
				System.out.println("_________________________ ROOMS AVAILABLE _________________________");
				System.out.println("");   
        		System.out.println(String.format("%5s %15s %8s %15s %15s", "Room No", "Room Type", "Wifi" , "Smoking Room", "Window View"));
            	if(!filterList.isEmpty()) {
        			for (Room room : filterList) {
        				if(!room.getRoomFloorNo().equals(roomNo)) {
        					Room_Type rT = Room_Type_Manager.getInstance().getRoom(room.getRoomType());
        					if(room.isWifi() == true) {
        						wifi = "Yes";
        					}else {
        						wifi = "No";
        					}
        					if(room.isSmoking() == true) {
        						smoke = "Yes";
        					}else {
        						smoke = "No";
        					}
        					if(room.isView() == true) {
        						view = "Yes";
        					}else {
        						view = "No";
        					}
        					System.out.println(String.format("%5s %15s %9s %12s %14s", room.getRoomFloorNo(), rT.getRoomType(), wifi, smoke, view));
        				}
        			}
        		}
               	else {
               		System.out.println("No Available Rooms for for your chosen dates");
               		System.out.println("_________________________________________________________________");
               		return;
               	}
            		System.out.println("_________________________________________________________________");
               }
               else if(filterChoice=='N' || filterChoice=='n') {}
               else {
            	   System.out.println("Invalid Input!");
               }
               
               System.out.print("Please Select a Room No(xx-xx): ");
               roomNo = sc.next();
               roomNoList.add(roomNo);
               
               Room_Status roomStatus = new Room_Status(roomNo, guest.getGuest_ID() , "Reserved", startDate, endDate);
               statusList.add(roomStatus);
               
	           System.out.print("Do you wish to add additional rooms? (Y/N): ");
	           char addRoom = sc.next().charAt(0);    	
	           if(addRoom == 'Y' || addRoom == 'y')
	        	   addRoomB = true;
	           else if(addRoom == 'N' || addRoom == 'n')
	        	   addRoomB = false;
           }
        }while(addRoomB);
        
        if(statusList != null) {
        	 double total = 0;
             SimpleDateFormat dfS = new SimpleDateFormat("EEE");
             for(Room_Status rS : statusList) {
             	int days = (int) ((rS.getDate_to().getTime() - rS.getDate_from().getTime()) / (1000 * 60 * 60 * 24)-1);
             	Date dateCheck = rS.getDate_from();
             	for(int i = 0; i <= days ; i++) {
                 	Room rm = Room_Manager.getInstance().getRoom(rS.getRoomFloor_No());
                 	Room_Type rT = Room_Type_Manager.getInstance().getRoom(rm.getRoomType());
                 	if(dateCheck.equals(rS.getDate_from()) || dateCheck.equals(rS.getDate_to()) || (dateCheck.after(rS.getDate_from()) && dateCheck.before(rS.getDate_to()))) {
                 		String dateCheckS = dfS.format(dateCheck);
                 		if(dateCheckS.equals("Sat") || dateCheckS.equals("Sun"))
                 			total += rT.getWeekEndRate();
                 		else
                 			total += rT.getWeekDayRate();
                 	}
                 	dateCheck = new Date(dateCheck.getTime() + TimeUnit.DAYS.toMillis(1));
             	}
             }
             
             Reservation reservation = new Reservation(guest, statusList, noChild, noAdult, status);
             printConfirmation(reservation, total);
             Reservation_Manager.getInstance().addReservation(reservation);
             for(Room_Status s : statusList) {
             	Room_Status_Manager.getInstance().addRoomStatus(s);
             }
             ReservationUI.creationComplete(reservation);
        }
    }
	
	private void retrieveUpdateReservation() {
		sc = new Scanner(System.in);
		ArrayList<String> roomNoList = new ArrayList<>();
		int adult;
		int child;
		int roomType;
		Date start = null;
		Date end = null;
		String roomNo = null;
		try {
			System.out.print("Please enter Reservation Code: ");
			String rCode = sc.next();
			Reservation r = Reservation_Manager.getInstance().getReservation(rCode);
			System.out.println("_______________ RESERVATION _______________");
			System.out.println("");
			System.out.println("Reservation Code: " + r.getReservationCode());
			System.out.println("Guest Name: " + r.getGuest().getName());
			System.out.print("Rooms Booked: ");
			for (Room_Status status : r.getStatusList()) {
	        	System.out.print("#"+status.getRoomFloor_No() + " ");
	        	start = status.getDate_from();
	        	end = status.getDate_to();
	        }
			System.out.println("");
			System.out.println("Date From: " + start);
			System.out.println("Date To: " + end);
			System.out.println("No. of Adults: " + r.getNumberOfAdults());
			System.out.println("No. of Children: " + r.getNumberOfChildren());
			System.out.println("Reservation ln: " + r.getStatus());
			System.out.println("_________________________________________");
			System.out.println("_______ UPDATE MENU _______");
			System.out.println("1. No. of Adults");
			System.out.println("2. No. of Children");
			System.out.println("3. Add Rooms");
			System.out.println("4. Remove Rooms");
			System.out.println("___________________________");
			int choice = sc.nextInt();
			
			switch(choice) {
				case 1: System.out.print("Update No. of Adults: ");
						adult = sc.nextInt();
						r.setNumberOfAdults(adult);
						break;
				case 2:	System.out.print("Update No. of Childrens: ");
						child = sc.nextInt();
						r.setNumberOfChildren(child);
						break;
				case 3: Room_Type_Manager.getInstance().displayAllRoomType();
			            System.out.print("Which Room Do You Want? ");
			            roomType = sc.nextInt();
			            ArrayList<Room> tempRoomList = new ArrayList<>();
			            tempRoomList = checkExisting(start, end, roomType, roomNoList);
			            System.out.print("Do you want to filter? (Y/N) :");
		                char filterChoice = sc.next().charAt(0);
		                if(filterChoice=='Y' || filterChoice=='y') {
		               	ArrayList<Room> filterList = filterRoom(tempRoomList, roomNoList, roomType);
						System.out.println("_____________ ROOMS AVAILABLE _____________");
						System.out.println("");
		               	if(!filterList.isEmpty()) {
		               		for (Room room : filterList) {
		                   		if(!room.getRoomFloorNo().equals(roomNo)) {
		                   			Room_Type rT = Room_Type_Manager.getInstance().getRoom(room.getRoomType());
		                   			System.out.println(room.getRoomFloorNo() + " " + rT.getRoomType() + " " 
		                   			+ rT.getWeekDayRate() + " " + rT.getWeekEndRate());
		                   		}
		               		}
		               	}
		               	else {
		               		System.out.println("No Available Rooms for for your chosen dates");
		               		System.out.println("_____________________________________");
		               		return;
		               	}
		               		System.out.println("_____________________________________");
		                }
		                else if(filterChoice=='N' || filterChoice=='n') {}
		                else {
		            	   System.out.println("Invalid Input!");
		                }
			               
		                System.out.print("Please Select a Room No(xx-xx): ");
		                roomNo = sc.next();
		                roomNoList.add(roomNo);
		               
		                Room_Status roomStatus = new Room_Status(roomNo, r.getGuest().getGuest_ID() , "Reserved", start, end);
		                Room_Status_Manager.getInstance().addRoomStatus(roomStatus);
		                r.getStatusList().add(roomStatus);
						break;
				case 4: System.out.print("Enter room to remove:");
						String room = sc.next();
						for (Room_Status status : r.getStatusList()) {
							if(status.getRoomFloor_No().equals(room)) {
					        	Room_Status_Manager.getInstance().updateStatustoCancelled(status);
					        	r.getStatusList().remove(status);
							}
				        }
						break;
				case 0: break;
			}
			
			Reservation_Manager.getInstance().updateReservation(r);
			ReservationUI.updateComplete(r);
		}
		catch(NullPointerException e) {
			System.out.println("Invaild Input! Please insert again.");
		}
	}
	
	private ArrayList<Room> checkExisting(Date start, Date end, int roomtype, ArrayList<String> roomNo) {
		String wifi, smoke, view;
		ArrayList<Room> checkRoom = new ArrayList<>();
		ArrayList<Room> roomList = Room_Manager.getInstance().getAllRoom(start, end);
		if(!roomList.isEmpty()) {
			for (Room room : roomList) {
				if(!roomNo.contains(room.getRoomFloorNo())) {
					if(room.getRoomType() == roomtype) {
						checkRoom.add(room);
					}
				}
	        }
		}
		System.out.println("_________________________ ROOMS AVAILABLE _________________________");
		System.out.println("");
		System.out.println(String.format("%5s %15s %8s %15s %15s", "Room No", "Room Type", "Wifi" , "Smoking Room", "Window View"));
		if(!checkRoom.isEmpty()) {
			for (Room room : checkRoom) {
				if(room.getRoomType() == roomtype) {
					Room_Type rT = Room_Type_Manager.getInstance().getRoom(room.getRoomType());
					if(room.isWifi() == true) {
						wifi = "Yes";
					}else {
						wifi = "No";
					}
					if(room.isSmoking() == true) {
						smoke = "Yes";
					}else {
						smoke = "No";
					}
					if(room.isView() == true) {
						view = "Yes";
					}else {
						view = "No";
					}
					System.out.println(String.format("%5s %15s %9s %12s %14s", room.getRoomFloorNo(), rT.getRoomType(), wifi, smoke, view));
				}
			}
		}
		else if(checkRoom.isEmpty()){
			System.out.println("No Available Rooms for for your chosen dates");
			System.out.println("__________________________________________________________________");
			return null;
		}
		System.out.println("__________________________________________________________________");
        return roomList;
    }
	
	private ArrayList<Room> waitListExisting(Date start, Date end, int roomtype, ArrayList<String> roomNo) {
		String wifi, smoke, view;
		ArrayList<Room> checkRoom = new ArrayList<>();
		ArrayList<Room> roomList = Room_Manager.getInstance().getAllWaitListRoom(start, end);
		if(!roomList.isEmpty()) {
			for (Room room : roomList) {
				if(!roomNo.contains(room.getRoomFloorNo())) {
					if(room.getRoomType() == roomtype) {
						checkRoom.add(room);
					}
				}
	        }
		}
		System.out.println("_________________________ ROOMS AVAILABLE _________________________");
		System.out.println(String.format("%5s %15s %8s %15s %15s", "Room No", "Room Type", "Wifi" , "Smoking Room", "Window View"));
		if(!checkRoom.isEmpty()) {
			for (Room room : checkRoom) {
				if(room.getRoomType() == roomtype) {
					Room_Type rT = Room_Type_Manager.getInstance().getRoom(room.getRoomType());
					if(room.isWifi() == true) {
						wifi = "Yes";
					}else {
						wifi = "No";
					}
					if(room.isSmoking() == true) {
						smoke = "Yes";
					}else {
						smoke = "No";
					}
					if(room.isView() == true) {
						view = "Yes";
					}else {
						view = "No";
					}
					System.out.println(String.format("%5s %15s %9s %12s %14s", room.getRoomFloorNo(), rT.getRoomType(), wifi, smoke, view));
				}
			}
		}
		System.out.println("__________________________________________________________________");
        return roomList;
    }
	
	private ArrayList<Room> filterRoom(ArrayList<Room> roomList, ArrayList<String> roomNo, int roomtype){
		ArrayList<Room> filterRoomList = new ArrayList<>();
		Boolean wifiB = null;
		Boolean smokeB = null;
		Boolean viewB = null;
    	System.out.print("Wifi (Y/N) :");
    	char wifiFilter = sc.next().charAt(0);
    	if(wifiFilter =='Y' || wifiFilter =='y')
    		wifiB = true;
    	else if(wifiFilter == 'N' || wifiFilter == 'n')
    		wifiB = false;
    	System.out.print("Smoking Room (Y/N) :");
    	char smokeFilter = sc.next().charAt(0);
    	if(smokeFilter =='Y' || smokeFilter =='y')
    		smokeB = true;
    	else if(smokeFilter == 'N' || smokeFilter == 'n')
    		smokeB = false;
    	System.out.print("Scenery View (Y/N) :");
    	char viewFilter = sc.next().charAt(0);
    	if(viewFilter =='Y' || viewFilter =='y')
    		viewB = true;
    	else if(viewFilter == 'N' || viewFilter == 'n')
    		viewB = false;
    	for(Room room : roomList) {
    		if(!roomNo.contains(room.getRoomFloorNo()) && room.getRoomType() == roomtype) {
    			if(wifiB==room.isWifi() && smokeB==room.isSmoking() && viewB==room.isView())
        			filterRoomList.add(room);
    		}
    	}
    	return filterRoomList;
	}
	
	private void printConfirmation(Reservation r, double total) {
		Date start = null;
		Date end = null;
		System.out.println("_______________ CONFIRMATION _______________");
		System.out.println("RESERVATION CODE: " + r.getReservationCode());
		System.out.println("GUEST: " + r.getGuest().getName());
		System.out.print("ROOMS RESERVED: ");
        for (Room_Status status : r.getStatusList()) {
        	System.out.print("#"+status.getRoomFloor_No() + " ");
        	start = status.getDate_from();
			end = status.getDate_to();
        }
        System.out.println("");
        System.out.println("DATE FROM: " + start);
        System.out.println("DATE END: " + end);
        System.out.println("NO OF CHILDRENS: " + r.getNumberOfChildren());
        System.out.println("NO OF ADULTS: " + r.getNumberOfAdults());
        DecimalFormat df = new DecimalFormat("#.00"); 
        System.out.println("TOTAL CHARGE: $" + df.format(total));
        System.out.println("_____________________________________________");
	}
	
	private void removeReservation() {
		sc = new Scanner(System.in);
		
		System.out.print("Enter Reservation Code to be removed: ");
		String rCode = sc.next();
		Date start = null;
		Date end = null;
		
		Reservation r = Reservation_Manager.getInstance().getReservation(rCode);
		ArrayList<Room_Status> statusList = new ArrayList<>();
		statusList = r.getStatusList();
		
		System.out.println("______________ RESERVATION ______________");
		System.out.println("");
		System.out.println("Reservation Code: " + r.getReservationCode());
		System.out.println("Guest Name: " + r.getGuest().getName());
		System.out.print("Rooms Booked: ");
		for(Room_Status status : statusList) {
			System.out.print("#" + status.getRoomFloor_No() + " ");
			start = status.getDate_from();
			end = status.getDate_to();
		}
		System.out.println("");
		System.out.println("Date From: " + start);
        System.out.println("Date End: " + end);
		System.out.println("No. of Adults: " + r.getNumberOfAdults());
		System.out.println("No. of Children: " + r.getNumberOfChildren());
		System.out.println("Reservation ln: " + r.getStatus());
		System.out.println("__________________________________________");
		System.out.println("Do you wish to remove " + r.getReservationCode() + " (Y/N)");
		try{
	    	char confirm = sc.next().charAt(0);
	    	if (confirm=='Y' || confirm=='y') {
	    		for(Room_Status rS : statusList) {
	    			Room_Status_Manager.getInstance().updateStatustoCancelled(rS);
	    		}
	    		r.setStatus("Cancelled");
		    	Reservation_Manager.getInstance().updateReservation(r);
		    	System.out.println("Reservation Removed");
	    	}
	    	else if(confirm == 'N' || confirm == 'n') {}
	    }
    	catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please insert again.");
        }
	}
	
	private void printReservationMenu() {
		sc = new Scanner(System.in);
		System.out.println("1. Check Reservation\n"
						+  "2. Print All Reservations\n"
						+  "0. Back");
		System.out.print("Choose a menu: ");
		int choice = sc.nextInt();
		
		switch(choice) {
			case 1: checkReservation();
					break;
			case 2: printAllReservations();
					break;
			case 0: break;
	        default:System.out.println("Invalid Choice");
	            	break;
		}
	}
	
	private void checkReservation() {
		sc = new Scanner(System.in);
		Date start = null;
		Date end = null;
		try {
		System.out.print("Enter Reservation Code: ");
		String rCode = sc.next();
		
		Reservation r = Reservation_Manager.getInstance().getReservation(rCode);
		ArrayList<Room_Status> statusList = new ArrayList<>();
		statusList = r.getStatusList();
		
		System.out.println("______________ RESERVATION ______________");
		System.out.println("Reservation Code: " + r.getReservationCode());
		System.out.println("Guest Name: " + r.getGuest().getName());
		System.out.print("Rooms Booked: ");
		for(Room_Status status : statusList) {
			System.out.print("#" + status.getRoomFloor_No() + " ");
			start = status.getDate_from();
			end = status.getDate_to();
		}
		System.out.println("");
		System.out.println("Date From: " + start);
		System.out.println("Date To: " + end);
		System.out.println("No. of Adults: " + r.getNumberOfAdults());
		System.out.println("No. of Children: " + r.getNumberOfChildren());
		System.out.println("Reservation ln: " + r.getStatus());
		System.out.println("__________________________________________");
		System.out.println("");
		}
		catch(NullPointerException e) {
			System.out.println("Invalid Input!");
			System.out.println();
		}
	}
	
	private void printAllReservations() {
		ArrayList<Reservation> allRList = Reservation_Manager.getInstance().getAllReservation();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		ArrayList<Room_Status> statusList = null;
		Date start = null;
		Date end = null;
		
		System.out.println("________________________________________ RESERVATION LIST ________________________________________");
		System.out.println("");
		if(!allRList.isEmpty()) {
			System.out.println("ReservationID/ReservationCode/GuestName/RoomNo/StartDate/EndDate");
			for(Reservation r :allRList) {
				r = Reservation_Manager.getInstance().getReservation(r.getReservationCode());
				statusList = r.getStatusList();
				System.out.print(String.format("%1s %16s %12s %5s", r.getReservationID(), r.getReservationCode(), r.getGuest().getName(),""));
				for(Room_Status rS : statusList) {
					System.out.print("#" + rS.getRoomFloor_No() + " ");
					start = rS.getDate_from();
					end = rS.getDate_to();
				}
				String stringStart = df.format(start);
				String stringEnd = df.format(end);
				System.out.println(String.format("%15s %12s", stringStart , stringEnd));
				
			}
		}
		else {
			System.out.println("No Reservations currently at the moment");
		}
		System.out.println("_________________________________________________________________________________________________");
		try {
			System.out.print("Please enter Reservation ID: ");
			int id = sc.nextInt();
			Reservation view = Reservation_Manager.getInstance().getReservationByID(id);
			System.out.println();
			System.out.println("_______________ RESERVATION _______________");
			System.out.println("Reservation Code: " + view.getReservationCode());
			System.out.println("Guest Name: " + view.getGuest().getName());
			System.out.print("Rooms Booked: ");
			for(Room_Status status : statusList) {
				System.out.print("#" + status.getRoomFloor_No() + " ");
				start = status.getDate_from();
				end = status.getDate_to();
			}
			System.out.println("");
			System.out.println("Date From: " + start);
			System.out.println("Date To: " + end);
			System.out.println("No. of Adults: " + view.getNumberOfAdults());
			System.out.println("No. of Children: " + view.getNumberOfChildren());
			System.out.println("Reservation ln: " + view.getStatus());
			System.out.println("___________________________________________");
			System.out.println();
		}
		catch (NullPointerException e) {
			System.out.println("Invalid Input!");
			System.out.println("___________________________________________");
			System.out.println();
		}
	}
	
	private void retrieveWaitList() {
		ArrayList<Reservation> waitList = Reservation_Manager.getInstance().getWaitList();
		String status = null;
		int id;
		System.out.println("________________________ WAITLIST ________________________");
		System.out.println("");
		System.out.println(String.format("%1s %16s %12s %10s", "ReservationID", "ReservationCode", "GuestName" , "Status"));
		if(!waitList.isEmpty()) {
			for(Reservation re : waitList) {
	    		Guest g = re.getGuest();
	    		ArrayList<Room_Status> rsList = re.getStatusList();
	    		for(Room_Status rs : rsList) {
	    			if(Room_Status_Manager.getInstance().checkRoomStatus(rs)) {
	    				status = "Available";
	    			}
	    			else {
	    				status = "Not Available at the moment";
	    			}
	    		}
	    		System.out.println(String.format("%1s %16s %12s %10s" , re.getReservationID(), re.getReservationCode(), g.getName() , status));
	    	}
		}
		else {
			System.out.println("No Guest currently in Waiting List.");
		}
    	System.out.println("_________________________________________________________");
    	System.out.print("Do you want to update? (Y/N) :");
    	char check = sc.next().charAt(0);
    	if(check == 'y' || check == 'Y') {
    		System.out.print("Reservation ID to update: ");
    		id = sc.nextInt();
    		Reservation r = Reservation_Manager.getInstance().getReservationByID(id);
    		ArrayList<Room_Status> statusList = r.getStatusList();
    		r.setStatus("Reserved");
    		Room_Status_Manager.getInstance().updateWaitList(statusList);
    		Reservation_Manager.getInstance().updateReservation(r);
    	}
	}
}
