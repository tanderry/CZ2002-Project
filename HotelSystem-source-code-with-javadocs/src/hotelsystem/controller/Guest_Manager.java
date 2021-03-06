package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import hotelsystem.entity.Guest;

@SuppressWarnings("serial")
public class Guest_Manager implements Serializable{
	private static Guest_Manager instance = null;
	private final ArrayList<Guest> guestList = new ArrayList<>();
	
	private Guest_Manager() {}

	public static Guest_Manager getInstance() {
        if (instance == null) {
            instance = new Guest_Manager();
        }
        return instance;
    }

	public void updateGuest(Guest guest) {
        guestList.remove(guest);
        guestList.add(guest);
        storeData();
    }

	public Guest getGuest(int guest_ID) {
        for (Guest guest : guestList) {
            if (guest.getGuest_ID() == guest_ID)
                return guest;
        }
        return null;
    }

	public Guest getGuest(String name) {
		String checkName = name.toUpperCase();
        for (Guest guest : guestList) {
            if (guest.getName().toUpperCase().equals(checkName)){
                return guest;
            }
        }
        return null;
    }
	
	public Guest getGuestByIdenNo(String identityID) {
        for (Guest guest : guestList) {
            if (guest.getIdentity_no().toUpperCase().equals(identityID)){
                return guest;
            }
        }
        return null;
    }
	
    public void removeGuest(Guest guest) {
        guestList.remove(guest);
        storeData();
    }

    public void addGuest(Guest guest) {
        guestList.add(guest);
        storeData();
    }
    
    public Guest addGuestReturn(Guest guest) {
        guestList.add(guest);
        storeData();
        return guest;
    }

    public ArrayList<Guest> searchGuestList(String name) {
    	String checkName = name.toUpperCase();
    	ArrayList<Guest> result = new ArrayList<>();
    	for(Guest guest : guestList){
            if(guest.getName().toUpperCase() != null && guest.getName().toUpperCase().contains(checkName)) {
            	result.add(guest);
            }
        }
    	return result;
    }

    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/Guest.ser"));
            out.writeInt(guestList.size());
            out.writeInt(Guest.getMaxID());
            for (Guest guest : guestList)
                out.writeObject(guest);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadData () {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/Guest.ser"));

            int noOfOrdRecords = ois.readInt();
            Guest.setMaxID(ois.readInt());
            System.out.println("GuestController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                guestList.add((Guest) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
