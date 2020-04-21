package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.CheckInCheckOut;
import hotelsystem.entity.Guest;
import hotelsystem.entity.RoomStatus;

@SuppressWarnings("serial")
public class CheckInCheckOutController implements Serializable{
	private static CheckInCheckOutController instance = null;
	private final ArrayList<CheckInCheckOut> checkInList = new ArrayList<>();
	
	private CheckInCheckOutController() {}
	
	public static CheckInCheckOutController getInstance() {
        if (instance == null) {
            instance = new CheckInCheckOutController();
        }
        return instance;
    }
	
	public void updateCheckInCheckOut(CheckInCheckOut cico) {
		checkInList.remove(cico);
		checkInList.add(cico);
        storeData();
    }
	
	public CheckInCheckOut getGuest(int ID) {
        for (CheckInCheckOut cico : checkInList) {
            if (cico.getCheckInCheckOut_ID() == ID)
                return cico;
        }
        return null;
    }
	
	public CheckInCheckOut getGuestOut(int ID) {
        for (CheckInCheckOut cico2 : checkInList) {
            if (cico2.getGuest().getGuest_ID() == ID) {
            	if(cico2.getStatus().equals("Checked-In")) {
            		return cico2; }
            }
        }
        return null;
    }
	
	public boolean getGuestFullOut(int ID) {
		boolean check = false;
        for (CheckInCheckOut cico2 : checkInList) {
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
	
    public void removeCheckIn(CheckInCheckOut cico)  {
    	checkInList.remove(cico);
        storeData();
    }

    public void addCheckIn(CheckInCheckOut cico) {
    	checkInList.add(cico);
        storeData();
    }
    
    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/CheckInCheckOut.ser"));
            out.writeInt(checkInList.size());
            out.writeInt(CheckInCheckOut.getMaxID());
            for (CheckInCheckOut cico : checkInList)
                out.writeObject(cico);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadData () {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/CheckInCheckOut.ser"));

            int noOfOrdRecords = ois.readInt();
            Guest.setMaxID(ois.readInt());
            System.out.println("CheckInController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                checkInList.add((CheckInCheckOut) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

}
