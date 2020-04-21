package hotelsystem.ui;

import java.util.ArrayList;
import java.util.Scanner;

import hotelsystem.controller.Food_Manager;
import hotelsystem.controller.Room_Service_Manager;
import hotelsystem.controller.Room_Status_Manager;
import hotelsystem.entity.Food;
import hotelsystem.entity.Room_Service;
import hotelsystem.entity.Room_Status;

public class RoomService_UI {
	private static RoomService_UI instance = null;
	private Scanner sc;
	
	private RoomService_UI() {
		sc = new Scanner(System.in);
	}
	
	public static RoomService_UI getInstance() {
		if (instance == null) {
            instance = new RoomService_UI();
        }
        return instance;
	}
	
	private static void creationComplete(Room_Service rS) {
		System.out.print("RoomService :" + rS.getRoomServiceID() + "/");
		for (Food food : rS.getFoodList()) {
        	System.out.print(food.getfood_name() + " ");
        }
		System.out.print("/" + rS.getDesc() + "/" + rS.getStatus() + " has been CREATED.\n");  
    }
	
	public static void updateComplete(Room_Service rS) {
    	System.out.println("RoomService :" + rS.getRoomServiceID() + " " 
				+ rS.getRoomStatusID() + " " + rS.getStatus()
				+ " has been UPDATED.");  
    }
	
	public void displayOptions() {
        int choice;
        do {
        	System.out.println("__________ ROOM SERVICE MENU __________");
            System.out.println("1.Create Room Service");
            System.out.println("2.Update Room Service Information");
            System.out.println("0.Back");
            System.out.println("_______________________________________");
            choice = sc.nextInt();
            switch (choice) {
                case 1:	createRoomService();
                		break;
                case 2:	updateRoomService();
                    	break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        } while (choice > 0);
    }
	
	private void createRoomService() {
		sc = new Scanner(System.in);
		double totalPrice = 0;
		Boolean check = null;
		ArrayList<Food> rsList = new ArrayList<>();
		
		System.out.print("Enter Room No: ");
		String roomNo = sc.next();
		
		Room_Status rS = Room_Status_Manager.getInstance().getStatus(roomNo);
		if(rS != null) {
			System.out.println("Room Found!!");
		}
		else {
			System.out.println("Room Not Found!!");
			return;
		}
		
		do {
			displayFoodList();
			int foodChoice = sc.nextInt();
			Food f = Food_Manager.getInstance().getFood(foodChoice);
			rsList.add(f);
			System.out.print("Do you wish to add additional food (Y/N) :");
			char confirm = sc.next().charAt(0);
			sc.nextLine();
			if(confirm == 'Y' || confirm == 'y') {
				check = true;
			} 
			else {
				check = false;
			}
		}while(check);
		
		System.out.println("Do you wish to add any remarks? (Enter NIL if no)");
		String remarks = sc.nextLine();
		
		for(Food f : rsList) {
			totalPrice += f.getfood_price();
		}
		
		Room_Service roomService = new Room_Service(rsList, rS.getRoomBookings_ID(), remarks, totalPrice, "Confirmed");
		Room_Service_Manager.getInstance().addRoomService(roomService);
		creationComplete(roomService);
	}
	
	private void displayFoodList() {
		ArrayList<Food> foodList = Food_Manager.getInstance().getAllFoodList();
		System.out.println("____________________ FOOD MENU ____________________");
		for(Food f : foodList) {
			System.out.println(f.getFood_ID() + "		" + f.getfood_name() + "		" + f.getfood_price() + "		" + f.getfood_description());
		}
		System.out.println("___________________________________________________");
		System.out.println("*Please select the food ID you want to add*");
	}
	
	private void updateRoomService() {
		sc = new Scanner(System.in);
		String status = null;
		
		System.out.print("Enter Room No: ");
		String roomNo = sc.next();
		Room_Status rS = Room_Status_Manager.getInstance().getStatus(roomNo);
		Room_Service rService = Room_Service_Manager.getInstance().getRoomService(rS.getRoomBookings_ID());
		if(rS != null) {
			System.out.println("Room Found!!");
		}
		System.out.println("_____________ ROOM SERVICE DETAILS _____________");
		System.out.println("ROOM NO: " + rS.getRoomFloor_No());
		System.out.println("FOOD ORDERED: ");
		for(Food food : rService.getFoodList()) {
			System.out.print(food.getfood_name() + " ");
		}
		System.out.println("");
		System.out.println("REMARKS: " + rService.getDesc());
		System.out.println("TOTAL PRICE: " + rService.getTotalPrice());
		System.out.println("STATUS: " + rService.getStatus());
		System.out.println("________________________________________________");
		
		System.out.println("Update Room Service Status");
		System.out.println("1. Preparing");
		System.out.println("2. Delivered");
		System.out.println("3. Cancelled");
		int choice = sc.nextInt();
		
		switch(choice) {
			case 1: status = "Preparing";
					break;
			case 2:	status = "Delivered";
					break;
			case 3:	status = "Cancelled";
					break;
			case 0:	break;
		}
		
		Room_Service_Manager.getInstance().updateRoomService(rS.getRoomBookings_ID(), status);
	}
}
