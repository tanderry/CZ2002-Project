package MainSystem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import hotelsystem.controller.Billing_Manager;
import hotelsystem.controller.Checkinout_Manager;
import hotelsystem.controller.FoodController;
import hotelsystem.controller.GuestController;
import hotelsystem.controller.PromoController;
import hotelsystem.controller.ReservationController;
import hotelsystem.controller.RoomController;
import hotelsystem.controller.RoomServiceController;
import hotelsystem.controller.RoomStatusController;
import hotelsystem.controller.Room_Type_Manager;
import hotelsystem.ui.Check_In_UI;
import hotelsystem.ui.Check_Out_UI;
import hotelsystem.ui.FoodUI;
import hotelsystem.ui.GuestUI;
import hotelsystem.ui.Room_Statistic_UI;
import hotelsystem.ui.PromoUI;
import hotelsystem.ui.ReservationUI;
import hotelsystem.ui.Room_Filter_UI;
import hotelsystem.ui.RoomServiceUI;
import hotelsystem.ui.RoomUI;


public class HotelApp {
	public static void main(String args[]) {
		SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dfTime = new SimpleDateFormat("hh:mm a");
		GuestController.getInstance().loadData();
		RoomController.getInstance().LoadDB();
		Room_Type_Manager.getInstance().LoadDB();
		RoomStatusController.getInstance().LoadDB();
		ReservationController.getInstance().loadData();
		Checkinout_Manager.getInstance().loadData();
		RoomServiceController.getInstance().loadData();
		FoodController.getInstance().LoadDB();
		PromoController.getInstance().LoadDB();
		Billing_Manager.getInstance().loadData();
		int choice;
		Date today = new Date();
		String date = dfDate.format(today);
		String time = dfTime.format(today);
		Scanner sc = new Scanner(System.in);
		
		try {
		do {
			System.out.println("_____________________ HOTEL SYSTEM MENU ________________________\n"
					         + "| Date: "
					         +  date
					         +  "                              Time: "
					         +  time + " |\n"
							 + "|  1. Create/Update/Search Guests Details                      |\n"
							 + "|  2. Create/Update/Cancel/Print Reservation                   |\n"
							 + "|  3. Create/Update Room Details                               |\n"
							 + "|  4. Enter Room Service Orders                                |\n"
							 + "|  5. Create/Update/Remove Room Service Menu Items             |\n"
							 + "|  6. Check Room Availability                                  |\n"
							 + "|  7. Room Check-In                                            |\n"
							 + "|  8. Room Check-Out & Print Bill Invoice                      |\n"
							 + "|  9. Print Room Status Statistic report                       |\n"
							 + "| 10. Create/Remove Promotions                                 |\n"
							 + "|______________________________________________________________|\n");
			
			choice = sc.nextInt();
			sc.nextLine();
				switch(choice) {
					case 1:	GuestUI.getInstance().displayOptions();
							break;
					case 2: ReservationUI.getInstance().displayOptions();
							break;
					case 3: RoomUI.getInstance().displayOptions();
							break;
					case 4: RoomServiceUI.getInstance().displayOptions();
							break;
					case 5: FoodUI.getInstance().displayOptions();	
							break;
					case 6: Room_Filter_UI.getInstance().displayOptions();
							break;
					case 7: Check_In_UI.getInstance().displayOptions();
							break;
					case 8: Check_Out_UI.getInstance().displayOptions();
							break;
					case 9: Room_Statistic_UI.getInstance().displayOptions();
							break;
					case 10:PromoUI.getInstance().displayOptions();
							break;
				}
			}while(choice > 0 && choice <= 10);
	    }
		
		catch (InputMismatchException e) {
			sc.close();
        	System.out.println("Invaild Input! Please re-run program.");
        	return;
        }
	
		GuestController.getInstance().storeData();
		RoomController.getInstance().SaveDB();
		Room_Type_Manager.getInstance().SaveDB();
		RoomStatusController.getInstance().SaveDB();
		ReservationController.getInstance().storeData();
		Checkinout_Manager.getInstance().storeData();
		FoodController.getInstance().SaveDB();
		RoomServiceController.getInstance().storeData();
		PromoController.getInstance().SaveDB();
		Billing_Manager.getInstance().storeData();
		sc.close();
	}
}
