package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.Check_In_Out;
import hotelsystem.entity.Guest;
import hotelsystem.entity.RoomStatus;


@SuppressWarnings("serial")
public class Checkinout_Manager implements Serializable{
	private static Checkinout_Manager instance = null;
	private final ArrayList<Check_In_Out> checkInList = new ArrayList<>();
	
	
	private Checkinout_Manager() {}
	
	
	public static Checkinout_Manager getInstance() {
        if (instance == null) {
            instance = new Checkinout_Manager();
        }
        return instance;
    }
	
	
	public void updateCheckInCheckOut(Check_In_Out cico) {
		checkInList.remove(cico);
		checkInList.add(cico);
        storeData();
    }
	
	
	public Check_In_Out getGuest(int ID) {
        for (Check_In_Out cico : checkInList) {
            if (cico.getCheckInCheckOut_ID() == ID)
                return cico;
        }
        return null;
    }
	
	
	public Check_In_Out getGuestOut(int ID) {
        for (Check_In_Out cico2 : checkInList) {
            if (cico2.getGuest().getGuest_ID() == ID) {
            	if(cico2.getStatus().equals("Checked-In")) {
            		return cico2; }
            }
        }
        return null;
    }
	
	
	public boolean getGuestFullOut(int ID) {
		boolean check = false;
        for (Check_In_Out cico2 : checkInList) {
            if (cico2.getGuest().getGuest_ID() == ID) {
            	ArrayList<RoomStatus> rList = cico2.getRoomStatus();
            	for (RoomStatus rS : rList) {
            		if(rS.getStatus().equals("Checked-In")) {
            			check = true;
            			break;
            		}
            		else {
            			check = false;
            		}
            	}
            }
        }
        return check;
    }
	
	
    public void removeCheckIn(Check_In_Out cico)  {
    	checkInList.remove(cico);
        storeData();
    }

    
    public void addCheckIn(Check_In_Out cico) {
    	checkInList.add(cico);
        storeData();
    }
    
    
    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/CheckInCheckOut.ser"));
            out.writeInt(checkInList.size());
            out.writeInt(Check_In_Out.getMaxID());
            for (Check_In_Out cico : checkInList)
                out.writeObject(cico);
            //System.out.printf("CheckInController: %,d Entries Saved.\n", checkInList.size());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    public void loadData () {
        // create an ObjectInputStream for the file we created before
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/CheckInCheckOut.ser"));

            int noOfOrdRecords = ois.readInt();
            Guest.setMaxID(ois.readInt());
            System.out.println("CheckInController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                checkInList.add((Check_In_Out) ois.readObject());
                //orderList.get(i).getTable().setAvailable(false);
            }
        } catch (IOException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
