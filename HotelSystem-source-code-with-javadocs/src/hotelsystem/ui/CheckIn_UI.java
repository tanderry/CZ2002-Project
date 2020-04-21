package hotelsystem.ui;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import hotelsystem.controller.Cico_Manager;
import hotelsystem.controller.Reservation_Manager;
import hotelsystem.controller.Room_Manager;
import hotelsystem.controller.Room_Status_Manager;
import hotelsystem.controller.Room_Type_Manager;
import hotelsystem.entity.Cico;
import hotelsystem.entity.Guest;
import hotelsystem.entity.Reservation;
import hotelsystem.entity.Room;
import hotelsystem.entity.Room_Status;
import hotelsystem.entity.Room_Type;


public class CheckIn_UI {
	private static CheckIn_UI instance = null;
    private Scanner sc;

    
    private CheckIn_UI() {
        sc = new Scanner(System.in);
    }
    
    
    public static CheckIn_UI getInstance() {
        if (instance == null) {
            instance = new CheckIn_UI();
        }
        return instance;
    }

    
    public void displayOptions() {
        int choice;
        try {
	        do {
				System.out.println("_____________ CHECK-IN MENU _____________");
				System.out.println("");
	            System.out.println("1. New Check-In         ");
	            System.out.println("2. Reservation Check-In ");
	            System.out.println("0. Back to previous menu");
	            System.out.println("_________________________________________");
	            System.out.print("Pick a choice: ");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                    createWalkInCheckIn();
	                	break;
	                case 2:
	                    createReservationCheckIn();
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
    
    
    public void createWalkInCheckIn() {
    	Guest guest = null;
    	int choice;
    	do {
    		System.out.println("Select an option:");
            System.out.println("1.New Customer");
            System.out.println("2.Existing Customer");
    		System.out.println("0.Back to previous menu");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                	guest = Guest_UI.getInstance().createnewGuest();
	                	if(guest!=null)
	                	createWalkinCheckIn(guest);
	                	break;
	                case 2:
	         	       String guestName;
	         	       System.out.println("Enter Guest Name: ");
	         	       guestName = sc.next();
	         	       guest = Guest_UI.getInstance().searchGuest(guestName);
	                	if(guest!=null)
	                		createWalkinCheckIn(guest);
	                    break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Invalid Choice");
	                    break;
	            }
	        } while (choice > 0);
    }
    
    
    public void createReservationCheckIn() {
    	ArrayList<Room_Status> statusList = new ArrayList<>();
    	sc = new Scanner(System.in);
    	System.out.println("Please enter your reservation code");
        String rcode = sc.nextLine();
        Reservation rdetails = Reservation_Manager.getInstance().getReservation(rcode);
        if (rdetails != null) {
	        for (Room_Status roomStatus : rdetails.getStatusList()) {
	        	Room_Status roomS = new Room_Status(roomStatus.getRoomBookings_ID(),roomStatus.getRoomFloor_No(), rdetails.getGuest().getGuest_ID() , "Checked-In", roomStatus.getDate_from(), roomStatus.getDate_to());
	        	Room_Status_Manager.getInstance().updateStatustoCheckedIn(roomS);
	        	statusList.add(roomS);
	        }
	        Cico cico = new Cico(rdetails.getGuest(),rdetails.getNumberOfChildren(),rdetails.getNumberOfAdults(),statusList,"Checked-In",null);
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
		        printConfirmation(cico, total);
		        Cico_Manager.getInstance().insertCheckIn(cico);
		        rdetails.setStatus("Checked-In");
		        Reservation_Manager.getInstance().updateReservation(rdetails);
	        }
        }
        else {
        	System.out.println("Invaild code. Please try again.");
        }
    }
    
    
    public void createWalkinCheckIn(Guest guest) {
    	sc = new Scanner(System.in);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	ArrayList<Room_Status> statusList = new ArrayList<>();
        String dateTo;
        Date startDate = new Date();
        Date storeDate = new Date();
        String sD = df.format(startDate);
        try {
        	startDate = df.parse(sD);
        	storeDate = df2.parse(sD + " 12:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
    	int noChild;
    	int noAdult;
    	int roomtype;
    	String wifi, smoke, view;
        
        
    	System.out.print("Enter Number of Adults: ");
        noAdult = sc.nextInt();
        System.out.print("Enter Number of Childrens: ");
        noChild = sc.nextInt();
        System.out.print("Enter Date To (dd/MM/yyyy): ");
        dateTo = sc.next();
        try {
            endDate = df.parse(dateTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startDate.equals(endDate)) {
        	System.out.println("You have to stay for at least one night. Please enter a new date.");
        	return;
        }
        else {
        	try {
                endDate = df2.parse(dateTo + " 12:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        Boolean addRoomB = null;
        ArrayList<String> roomNoList = new ArrayList<>();
        String roomNo = null;
        Boolean noroom = false;
        
        do {
        	ArrayList<Room> tempRoomList = new ArrayList<>();
             do {
            	 Room_Type_Manager.getInstance().displayAllRoomType();
            	 System.out.println("Please enter your choice of room");
                 roomtype = sc.nextInt();
                 Room_Type_Manager.getInstance().getRoom(roomtype);
                 
                tempRoomList = checkExisting(storeDate, endDate, roomtype, roomNoList);
                
                if(tempRoomList == null) { 
					System.out.println("_________________________ ROOMS AVAILABLE _________________________");
					System.out.println("");
                	System.out.println("No available rooms for your chosen dates");
               		System.out.println("___________________________________________________________________");
               		noroom=true;
                }
                else
                	noroom=false;
            } while(noroom);
            System.out.print("Would you like to filter your search? (Y/N) :");
            char filterChoice = sc.next().charAt(0);
            if(filterChoice=='Y' || filterChoice=='y') {
            	ArrayList<Room> filterList = filterRoom(tempRoomList, roomNoList, roomtype);
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
               		System.out.println("No available rooms for your chosen dates");
               		System.out.println("_________________________________________________________________");
               		return;
               	}
            		System.out.println("_________________________________________________________________");
               }
            else if(filterChoice=='N' || filterChoice=='n') {}
            else {
            	System.out.println("Invalid Input!");
            }
            Boolean check = null;
            do {
	            System.out.print("Please Select a Room Number(xx-xx): ");
	            Boolean proceed = null;
	            roomNo = sc.next();
	            for (Room tList : tempRoomList) {
	            	if(tList.getRoomFloorNo().equals(roomNo)) {
	            		proceed=true;
	            		check=true;
	            		break;
	            	}
	            	else {
	            		proceed=false;
	            	}
	            }
	            if(!proceed){
	            	System.out.println("Invalid Input! Please Try Again.");
	            	check=false;
	            }
            }while(!check);
            
            roomNoList.add(roomNo);
            Room_Status roomStatus = new Room_Status(roomNo, guest.getGuest_ID() , "Checked-In", storeDate, endDate);
            statusList.add(roomStatus);
            
        	System.out.print("Do you wish to add additional rooms? (Y/N): ");
        	char addRoom = sc.next().charAt(0);    	
        	if(addRoom == 'Y' || addRoom == 'y')
        		addRoomB = true;
        	else if(addRoom == 'N' || addRoom == 'n')
        		addRoomB = false;
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
        
	        Cico cico = new Cico(guest,noChild,noAdult,statusList,"Checked-In",null);
	        printConfirmation(cico, total);
	        Cico_Manager.getInstance().insertCheckIn(cico);
	        
	        for(Room_Status s : statusList) {
	        	Room_Status_Manager.getInstance().addRoomStatus(s);
        	}
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
		System.out.println("________________________ ROOMS AVAILABLE ________________________");
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
			System.out.println("No available rooms for your chosen dates");
			System.out.println("_________________________________________________________________");
			return null;
		}
		System.out.println("_________________________________________________________________");
        return checkRoom;
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
    
    
    private void printConfirmation(Cico cico, double total) {
    	Date from = null;
    	Date to = null;
		System.out.println("_________________ CONFIRMATION _________________");
		System.out.println("");
		System.out.println("GUEST: " + cico.getGuest().getName());
		System.out.print("ROOMS CHECKED-IN: ");
        for (Room_Status status : cico.getRoomStatus()) {
        	System.out.print("#"+status.getRoomFloor_No() + " ");
        	from = status.getDate_from();
        	to = status.getDate_to();
        }
        System.out.println("");
        System.out.println("DATE FROM: " + from);
        System.out.println("DATE END: " + to);
        System.out.println("NO OF CHILDREN: " + cico.getnumChildren());
        System.out.println("NO OF ADULTS: " + cico.getnumAdults());
        DecimalFormat df = new DecimalFormat("#.00"); 
        System.out.println("TOTAL CHARGE: $" + df.format(total));
        System.out.println("________________________________________________");
	}
}
