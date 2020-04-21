package MainSystem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import hotelsystem.controller.Billing_Manager;
import hotelsystem.controller.Cico_Manager;
import hotelsystem.controller.Food_Manager;
import hotelsystem.controller.Guest_Manager;
import hotelsystem.controller.Promotion_Manager;
import hotelsystem.controller.Reservation_Manager;
import hotelsystem.controller.Room_Manager;
import hotelsystem.controller.Room_Service_Manager;
import hotelsystem.controller.Room_Status_Manager;
import hotelsystem.controller.Room_Type_Manager;
import hotelsystem.ui.CheckIn_UI;
import hotelsystem.ui.CheckOut_UI;
import hotelsystem.ui.Food_UI;
import hotelsystem.ui.Guest_UI;
import hotelsystem.ui.Room_Statistic;
import hotelsystem.ui.PromoUI;
import hotelsystem.ui.ReservationUI;
import hotelsystem.ui.Room_Filter;
import hotelsystem.ui.RoomService_UI;
import hotelsystem.ui.Room_UI;

public class HotelApp {
	public static void main(String args[]) {
		SimpleDateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dfTime = new SimpleDateFormat("hh:mm a");
		Guest_Manager.getInstance().loadData();
		Room_Manager.getInstance().LoadDB();
		Room_Type_Manager.getInstance().LoadDB();
		Room_Status_Manager.getInstance().LoadDB();
		Reservation_Manager.getInstance().loadData();
		Cico_Manager.getInstance().loadData();
		Room_Service_Manager.getInstance().loadData();
		Food_Manager.getInstance().LoadDB();
		Promotion_Manager.getInstance().LoadDB();
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
					case 1:	Guest_UI.getInstance().displayOptions();
							break;
					case 2: ReservationUI.getInstance().displayOptions();
							break;
					case 3: Room_UI.getInstance().displayOptions();
							break;
					case 4: RoomService_UI.getInstance().displayOptions();
							break;
					case 5: Food_UI.getInstance().displayOptions();	
							break;
					case 6: Room_Filter.getInstance().displayOptions();
							break;
					case 7: CheckIn_UI.getInstance().displayOptions();
							break;
					case 8: CheckOut_UI.getInstance().displayOptions();
							break;
					case 9: Room_Statistic.getInstance().displayOptions();
							break;
					case 10:PromoUI.getInstance().displayOptions();
							break;
				}
			}while(choice > 0 && choice <= 10);
	    }
		
		catch (InputMismatchException e) {
			sc.close();
        	System.out.println("Invaild Input! Please enter a valid choice!");
        	return;
        }
	
		Guest_Manager.getInstance().storeData();
		Room_Manager.getInstance().SaveDB();
		Room_Type_Manager.getInstance().SaveDB();
		Room_Status_Manager.getInstance().SaveDB();
		Reservation_Manager.getInstance().storeData();
		Cico_Manager.getInstance().storeData();
		Food_Manager.getInstance().SaveDB();
		Room_Service_Manager.getInstance().storeData();
		Promotion_Manager.getInstance().SaveDB();
		Billing_Manager.getInstance().storeData();
		sc.close();
	}
}
