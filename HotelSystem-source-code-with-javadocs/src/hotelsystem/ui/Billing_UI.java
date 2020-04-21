package hotelsystem.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import hotelsystem.controller.Billing_Manager;
import hotelsystem.controller.Promotion_Manager;
import hotelsystem.controller.Room_Manager;
import hotelsystem.controller.Room_Service_Manager;
import hotelsystem.controller.Room_Type_Manager;
import hotelsystem.entity.Billing;
import hotelsystem.entity.Card;
import hotelsystem.entity.Cico;
import hotelsystem.entity.Food;
import hotelsystem.entity.Promo;
import hotelsystem.entity.Room;
import hotelsystem.entity.Room_Service;
import hotelsystem.entity.Room_Status;
import hotelsystem.entity.Room_Type;


public class Billing_UI {
	private static Billing_UI instance = null;
    private Scanner sc;

    
    private Billing_UI() {
        sc = new Scanner(System.in);
    }
    
    
    public static Billing_UI getInstance() {
        if (instance == null) {
            instance = new Billing_UI();
        }
        return instance;
    }
    
    
    public void generateBill(Cico cico) {
    	int days=0;
    	double roomTotal = 0;
    	double roomSerTotal = 0;
    	double totalPrice = 0;
    	double roomCost= 0;
    	double discountAmt = 0;
    	double discountTotalAmt = 0;
    	double taxAmt = 0;
    	double grandTotal = 0;
    	String paymentMode = null;
    	String paymentStatus = "Pending Payment";
    	ArrayList<Room_Service> rSerList = new ArrayList<>();
    	Card card = null;
    	
    	System.out.println("=================================== \n"
    			+"------------Hotel System------------\n"
				+"_______________ Rooms_______________\n"
				+"");
        SimpleDateFormat dfS = new SimpleDateFormat("EEE");
        for(Room_Status rS : cico.getRoomStatus()) {
        	days = (int) ((rS.getDate_to().getTime() - rS.getDate_from().getTime()) / (1000 * 60 * 60 * 24));
        	Date dateCheck = rS.getDate_from();
        	for(int i = 0; i < days ; i++) {
            	Room rm = Room_Manager.getInstance().getRoom(rS.getRoomFloor_No());
            	Room_Type rT = Room_Type_Manager.getInstance().getRoom(rm.getRoomType());
            	if(dateCheck.equals(rS.getDate_from()) || dateCheck.equals(rS.getDate_to()) || (dateCheck.after(rS.getDate_from()) && dateCheck.before(rS.getDate_to()))) {
            		String dateCheckS = dfS.format(dateCheck);
            		if(dateCheckS.equals("Sat") || dateCheckS.equals("Sun")) {
            			roomCost += rT.getWeekEndRate();
            			roomTotal += rT.getWeekEndRate();
            		}
            		else {
            			roomCost += rT.getWeekDayRate();
            			roomTotal += rT.getWeekDayRate();
            		}
            	}
            	dateCheck = new Date(dateCheck.getTime() + TimeUnit.DAYS.toMillis(1));
        	}
        	System.out.println("- " + rS.getRoomFloor_No() + ":	 " + days +" Days		" + roomCost);

        	Room rm = Room_Manager.getInstance().getRoom(rS.getRoomFloor_No());
        	Promo p = Promotion_Manager.getInstance().findPromo(rm.getRoomType(), rS.getDate_from(), rS.getDate_to());
        	if(p!=null) {
        		discountAmt = (p.getDiscount_amt()/100)*roomCost;
        		System.out.println("--- Room Discount	" + p.getDiscount_amt() + "%  	-" + discountAmt);
        		discountTotalAmt +=discountAmt;
        	}
        	
        	roomCost = 0.0;
        }
        System.out.println("-- Room Sub-Total: 		" + roomTotal);
        if(discountTotalAmt!=0.0) {
        	System.out.println("-- Discount: 			-" + discountTotalAmt);
        }
        roomTotal = roomTotal-discountTotalAmt;
        System.out.println("---------------------------------------");
        System.out.println("-- Room Total Cost: 		" + (roomTotal));
        System.out.println("---------------------------------------");
        
        
        for(Room_Status rS : cico.getRoomStatus()) {
    		rSerList.addAll(Room_Service_Manager.getInstance().getRSList(rS.getRoomBookings_ID()));
    	}
    	
        if(!rSerList.isEmpty()) {
        	System.out.println("~~~~~~~~~~~~~Room Service~~~~~~~~~~~~~");
        	for(Room_Service rmS : rSerList) {
        		if(!rmS.getStatus().equals("Cancelled")) {
	        		ArrayList<Food> foodList = rmS.getFoodList();
	        		for(Food f : foodList) {
	        			System.out.println("- " + f.getfood_name() + "			" + f.getfood_price());
	        		}
	        		roomSerTotal += rmS.getTotalPrice();
        		}
        	}
        	System.out.println("=======================================");
        	System.out.println("-- Room Service Sub-Total: 	" + roomSerTotal);
        	System.out.println("=======================================");
        }
        totalPrice= roomTotal+roomSerTotal;
        taxAmt = totalPrice*0.07;
        grandTotal = totalPrice+taxAmt;
        System.out.println("-- Total: 			" + totalPrice);
        System.out.println("-- Tax 7%: 			" + String.format( "%.2f", taxAmt));
        System.out.println("=======================================");
        System.out.println("---- Grand Total: 		" + String.format( "%.2f", grandTotal));
        System.out.println("=======================================");
        
        System.out.println("Select Payment Mode\n"
        		+ "1. Credit/Debit Card\n"
        		+ "2. Cash");
        
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice==1) {
                if (cico.getGuest().getCardDetails().getCcname()!=null) {
                	System.out.print("Card Details Found on Guest's Profile, Do you want use existing card to make payment? (Y-Yes N-No)");
                	char reply = sc.next().charAt(0);
                	sc.nextLine();
				    if (reply=='Y' || reply=='y') {
					    card = cico.getGuest().getCardDetails();
					    paymentMode = "Card";
		                paymentStatus = "Completed";
				    }
				    else if (reply=='N' || reply=='n'){
				    	card = createCard();
				    }
				    else
				    	System.out.println("Invaild Input! Please insert again.");
		         	}
                else {
                	card = createCard();
                }
        }
        else if (choice==2) {
                paymentMode = "Cash";
                paymentStatus = "Completed";
        }
        else {
                System.out.println("Invalid Choice");
        }
        
        Billing bill = new Billing(cico,rSerList,roomTotal,roomSerTotal,totalPrice,discountAmt,taxAmt,grandTotal,paymentMode,card,paymentStatus);
        Billing_Manager.getInstance().insertBill(bill);
        
        System.out.println("-------Payment Successful-------");
        return;
    }
    
    
    private Card createCard() {
    	System.out.println("Enter Card Full Name:");
        String ccName = sc.nextLine();
        System.out.println("Enter Card Number:");
        Long ccNum = sc.nextLong();
        sc.nextLine();
        System.out.println("Enter Expiry Date (MM/YY):");
        String ccDate = sc.nextLine();
        System.out.println("Enter CVV:");
        int ccCVV = sc.nextInt();
        Card newCard = new Card(ccName,ccNum,ccDate,ccCVV);
        return newCard;
    }

}
