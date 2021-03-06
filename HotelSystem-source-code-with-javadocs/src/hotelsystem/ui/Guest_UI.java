package hotelsystem.ui;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import hotelsystem.controller.Guest_Manager;
import hotelsystem.entity.Card;
import hotelsystem.entity.Guest;

public class Guest_UI {
	private static Guest_UI instance = null;
    private Scanner sc;

    private Guest_UI() {
        sc = new Scanner(System.in);
    }

    public static Guest_UI getInstance() {
        if (instance == null) {
            instance = new Guest_UI();
        }
        return instance;
    }

    public void displayOptions() {
        int choice;
	        do {
	            System.out.println("_________________________ GUEST MENU ___________________________\n"
						 + "|                                                              |\n"
						 + "|  1. Create Guest                                             |\n"
						 + "|  2. Obtain Guest Details                                     |\n"
						 + "|  3. Update Guest Details                                     |\n"
						 + "|  4. Remove Guest Details                                     |\n"
						 + "|  0. Go Back                                                  |\n"
						 + "|______________________________________________________________|\n");
	            choice = sc.nextInt();
	            switch (choice) {
	                case 1:
	                    createGuestDetails();
	                	break;
	                case 2:
	                    retrieveGuestDetails();
	                    break;
	                case 3:
	                	updateGuestDetails();
	                    break;
	                case 4:
	                	removeGuestDetails();
	                	break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Invalid Choice");
	                    break;
	            }
	        } while (choice > 0);
        
    }
    
    private void createGuestDetails() {
        sc = new Scanner(System.in);
        String guestName;
        String nric;
        String address;
        long phoneNo;
        String country;
        char sex;
        String nationality;
        String ccName = null,ccDate =null;
        long ccNum = 0;
        int ccCVV= 0;
        
	        System.out.println("Enter Guest Name: ");
	        guestName = sc.nextLine();
		    System.out.println("Enter Guest NRIC: ");
		    nric = sc.nextLine();
		   if (checkExistingGuestByIC(nric)!=null) {
				System.out.println("NRIC already exists in the system. Please Try Again.");
				return;
		   }
		   else {
		        System.out.println("Enter Address:");
		        address = sc.nextLine();
		        System.out.println("Enter Phone Number:");
		        phoneNo = sc.nextLong();
		        sc.nextLine();
		        System.out.println("Enter Country:");
		        country = sc.nextLine();
		        System.out.println("Enter Sex (M-Male, F-Female)");
		        sex = sc.nextLine().toUpperCase().charAt(0);
		        if (sex!='M') {
		        	if (sex!='F') {
		        		System.out.println("Invaild Input! Please insert again.");
		        		return;
		        	}
		       }
		        System.out.println("Enter Nationality:");
		        nationality = sc.nextLine();
		        
		        System.out.println("Do you want to add credit card details for guest? (Y-Yes, N-No)");
		        char reply = sc.next().charAt(0);
		        sc.nextLine();
		    	if (reply=='Y' || reply=='y') {
			        System.out.println("Enter Card Full Name:");
			        ccName = sc.nextLine();
			        System.out.println("Enter Card Number:");
			        ccNum = sc.nextLong();
			        sc.nextLine();
			        System.out.println("Enter Expiry Date (MM/YY):");
			        ccDate = sc.nextLine();
			        System.out.println("Enter CVV:");
			        ccCVV = sc.nextInt();
		    	}
		        
		        Card newcc = new Card(ccName,ccNum,ccDate,ccCVV);
		        Guest guest = new Guest(nric, guestName, address, phoneNo, country, sex, nationality,newcc);
		        Guest_Manager.getInstance().addGuest(guest);
		        System.out.println("Guest Name " + guest.getName() +  " has been created.");  
	        }
        
    }

    public Guest createnewGuest() {
        sc = new Scanner(System.in);
        String guestName;
        String nric;
        String address;
        long phoneNo;
        String country;
        char gender;
        String nationality;
        String ccName = null,ccDate =null;
        long ccNum = 0;
        int ccCVV= 0;
        Guest rguest = null;
        
        try {
	        System.out.println("Enter Guest Name: ");
	        guestName = sc.nextLine();
		        System.out.println("Enter Guest NRIC: ");
		        nric = sc.nextLine();
		   if (checkExistingGuestByIC(nric)!=null) {
		       System.out.println("NRIC exists in the system. Please Try Again.");
		       rguest= null;
		   }
		   else {
		        System.out.println("Enter Address:");
		        address = sc.nextLine();
		        System.out.println("Enter Contact Number:");
		        phoneNo = sc.nextLong();
		        sc.nextLine();
		        System.out.println("Enter Country:");
		        country = sc.nextLine();
		        System.out.println("Enter Gender (M-Male, F-Female)");
		        gender = sc.nextLine().toUpperCase().charAt(0);		       
		       if (gender!='M') {
		        	if (gender!='F') {
		        		System.out.println("Invaild Input! Please insert again.");
		        		rguest= null;
		        	}
		       }
		       else {
		        System.out.println("Enter Nationality:");
		        nationality = sc.nextLine();
		        
		        System.out.println("Do you want to add credit card details for guest? (Y-Yes, N-No)");
		        char reply = sc.next().charAt(0);
		        sc.nextLine();
		    	if (reply=='Y' || reply=='y') {
			        System.out.println("Enter Card Full Name:");
			        ccName = sc.nextLine();
			        System.out.println("Enter Card Number:");
			        ccNum = sc.nextLong();
			        sc.nextLine();
			        System.out.println("Enter Expiry Date (MM/YY):");
			        ccDate = sc.nextLine();
			        System.out.println("Enter CVV:");
			        ccCVV = sc.nextInt();
		    	}
		        
		        Card newcc = new Card(ccName,ccNum,ccDate,ccCVV);
		        Guest guest = new Guest(nric, guestName, address, phoneNo, country, gender, nationality,newcc);
		        rguest = Guest_Manager.getInstance().addGuestReturn(guest);
		        System.out.println("Guest Name " + guest.getName() +  " has been created.");  
		       }
	        }
        }
        catch (InputMismatchException e) {
        	System.out.println("Invaild Input! Please insert again.");
        }
        return rguest;
    }

    private Guest checkExistingGuestByIC(String identity_No) {
        Guest rGuest = Guest_Manager.getInstance().getGuestByIdenNo(identity_No);
        if (rGuest!=null) {
        	return rGuest;
        }
        else {
        	return null;
        }
    }

    protected Guest searchGuest(String guestName) {
    	ArrayList<Guest> sGuest = new ArrayList<>();
    	sGuest = Guest_Manager.getInstance().searchGuestList(guestName);
        if (sGuest.size()!=0) {
        	System.out.println(sGuest.size() + " Guest Found");
        	System.out.println("Guest ID	Guest Name	Guest NRIC");
        	for(Guest guest : sGuest){
        		System.out.println(guest.getGuest_ID() +"		"+ guest.getName() +"		"+ guest.getIdentity_no());
        	}
        	System.out.println("Select a Guest (Enter Guest ID)");
        	try {
	        	sc = new Scanner(System.in);
	        	int sid = sc.nextInt();
	        	for(Guest guest : sGuest){
	        		if(guest.getGuest_ID() == sid) {
	        			return guest;
	        		}
	        	}
	        	System.out.println("Invaild Guest ID");
	        	return null;
        	}
        	catch (InputMismatchException e) {
            	System.out.println("Invaild Input! Please insert again.");
            	return null;
            }
        }
        else {
        	System.out.println("No such guest. Please try again.");
        	return null;
        }
    }
    
    private void retrieveGuestDetails(){
    	Guest guest = null;
    	String gender = null, cc = null;
    	
    	String guestName;
        sc = new Scanner(System.in);
        System.out.println("Enter Guest Name: ");
        guestName = sc.nextLine();
        guest = searchGuest(guestName);
		
		if (guest!=null) {
			char g = guest.getGender();
			if (g=='M') {
				gender ="Male";
			}
			else if (g=='F') {
				gender ="Female";
			}
			if (guest.getCardDetails().getCcname()==null) {
				cc="Not Found";
			}
			else {
				cc ="vaild";
			}
			System.out.println("GUEST DETAILS AS FOLLOWS:\n"
					 + "Guest ID: " + guest.getGuest_ID() +"\n"
					 + "Name: " + guest.getName() +"\n"
					 + "NRIC: " + guest.getIdentity_no() +"\n"
					 + "Address: " + guest.getAddress() +"\n"
					 + "Phone Number: " + guest.getContact_no() +"\n"
					 + "Country: " + guest.getCountry() +"\n"
					 + "Sex: " + gender +"\n"
					 + "Nationality: " + guest.getNationality() +"\n"
					 + "Credit Card: " + cc +"\n");
		}
    }
    
    private void updateGuestDetails() {
    		int choice2;
    		Guest guest = null;
    		String guestName;
            sc = new Scanner(System.in);
            System.out.println("Enter Guest Name: ");
            guestName = sc.nextLine();
            guest = searchGuest(guestName);
        	
    		try {
	    		do {
	    			System.out.println("-Select Field to Update (Enter 0 to end)-\n"
	    							 + "1. Name\n"
	    							 + "2. NRIC\n"
	    							 + "3. Address\n"
	    							 + "4. Contact Number\n"
	    							 + "5. Country\n"
	    							 + "6. Gender\n"
	    							 + "7. Nationality\n"
	    							 + "8. Credit Card Details\n");
	    			
	    			choice2 = sc.nextInt();
	    			switch(choice2) {
	    				case 1:	
	    					System.out.println("Enter New Name: ");
	    					if (sc.nextLine() != null) {
	    						String name = sc.nextLine();
	    						guest.setName(name);
	    						System.out.println("Name Updated");
	    					}
	    						break;
	    				case 2:
	    					System.out.println("Enter New NRIC: ");
	    					if (sc.nextLine() != null) {
	    						String idNo = sc.nextLine();
	    						 if (checkExistingGuestByIC(idNo)!=null) {
	    						       System.out.println("NRIC exist in the system. Please Try Again.");
	    						       break;
	    						 }
	    						 else {
	    						guest.setIdentity_no(idNo);
	    						System.out.println("NRIC Saved");
	    						 }
	    					}
	    					break;
	    				case 3:
	    					System.out.println("Enter New Address: ");
	    					if (sc.nextLine() != null) {
	    						String address = sc.nextLine();
	    						guest.setAddress(address);
	    						System.out.println("Address Saved");
	    					}
	    					break;
	    				case 4:
	    					System.out.println("Enter New Contact Number: ");
	    					int phoneNo = sc.nextInt();
	    					guest.setContact_no(phoneNo);
	    					System.out.println("Contact Number Saved");
	    					break;
	    				case 5:
	    					System.out.println("Enter New Country: ");
	    					if (sc.nextLine() != null) {
	    						String country = sc.nextLine();
	    						guest.setCountry(country);
	    						System.out.println("Country Saved");
	    					}
	    					break;
	    				case 6:
	    					System.out.println("Enter Gender (M-Male, F-Female)");
	    					char gender = sc.next().charAt(0);
	    					guest.setGender(gender);
	    					System.out.println("Gender Saved");
	    					break;
	    				case 7:
	    					System.out.println("Enter Nationality");
	    					if (sc.nextLine() != null) {
	    						String nationality = sc.nextLine();
	    						guest.setNationality(nationality);
	    						System.out.println("Nationality Saved");
	    					}
	    					break;
	    				case 8:
	    					System.out.println("Enter Card Full Name:");
	    			        String ccName = sc.nextLine();
	    			        sc.nextLine();
	    			        System.out.println("Enter Card Number:");
	    			        Long ccNum = sc.nextLong();
	    			        sc.nextLine();
	    			        System.out.println("Enter Expiry Date (MM/YY):");
	    			        String ccDate = sc.nextLine();
	    			        System.out.println("Enter CVV:");
	    			        int ccCVV = sc.nextInt();
	    			        Card newcc = new Card(ccName,ccNum,ccDate,ccCVV);
	    			        guest.setCardDetails(newcc);
	    			        System.out.println("Credit Card Details Saved");
	    					break;
	    				case 0:
	    					Guest_Manager.getInstance().updateGuest(guest);
	    					System.out.println("Guest Details Updated!");
	    					break;
	    				}
	    			}while(choice2 > 0 && choice2 <= 8);
    		}
    		catch (InputMismatchException e) {
            	System.out.println("Invaild Input! Please insert again.");
            	displayOptions();
            }
    }
    
    public void removeGuestDetails() {
    	String guestName;
        sc = new Scanner(System.in);
        System.out.println("Enter Guest Name: ");
        guestName = sc.nextLine();
        Guest guest = searchGuest(guestName);
        if (guest != null) {
	    	System.out.println("Are you sure you want to delete the guest name " + guest.getName() + " ? (Y-Yes, N-No)");
	    	try{
		    	char reply = sc.next().charAt(0);
		    	if (reply=='Y' || reply=='y') {
			    	Guest_Manager.getInstance().removeGuest(guest);
			    	System.out.println("Guest Removed");
		    	}
		    	else
		    		System.out.println("Invaild Input! Please insert again.");
		    	}
	    	catch (InputMismatchException e) {
	        	System.out.println("Invaild Input! Please insert again.");
	        	displayOptions();
	        }
        }
    }
}
