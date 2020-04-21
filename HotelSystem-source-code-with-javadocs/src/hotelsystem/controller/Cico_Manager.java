package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.Cico;
import hotelsystem.entity.Guest;
import hotelsystem.entity.Room_Status;


@SuppressWarnings("serial")
public class Cico_Manager implements Serializable{
	private static Cico_Manager instance = null;
	private final ArrayList<Cico> checkInList = new ArrayList<>();
	
	
	private Cico_Manager() {}
	
	
	public static Cico_Manager getInstance() {
        if (instance == null) {
            instance = new Cico_Manager();
        }
        return instance;
    }
	
	
	public void updatecico(Cico cico) {
		checkInList.remove(cico);
		checkInList.add(cico);
        storeData();
    }
	
	
	public Cico getGuest(int ID) {
        for (Cico cico : checkInList) {
            if (cico.getCheckInCheckOut_ID() == ID)
                return cico;
        }
        return null;
    }
	
	
	public Cico getGuestOut(int ID) {
        for (Cico cico2 : checkInList) {
            if (cico2.getGuest().getGuest_ID() == ID) {
            	if(cico2.getStatus().equals("Checked-In")) {
            		return cico2; }
            }
        }
        return null;
    }
	
	
	public boolean getGuestFullOut(int ID) {
		boolean check = false;
        for (Cico cico2 : checkInList) {
            if (cico2.getGuest().getGuest_ID() == ID) {
            	ArrayList<Room_Status> rList = cico2.getRoomStatus();
            	for (Room_Status rS : rList) {
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
	
	
    public void deleteCheckIn(Cico cico)  {
    	checkInList.remove(cico);
        storeData();
    }

    
    public void insertCheckIn(Cico cico) {
    	checkInList.add(cico);
        storeData();
    }
    
    
    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/CheckInCheckOut.ser"));
            out.writeInt(checkInList.size());
            out.writeInt(Cico.getMaxID());
            for (Cico cico : checkInList)
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
                checkInList.add((Cico) ois.readObject());
                //orderList.get(i).getTable().setAvailable(false);
            }
        } catch (IOException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
