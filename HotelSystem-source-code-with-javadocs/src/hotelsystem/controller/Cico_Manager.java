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
	
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
	
	public static Cico_Manager getInstance() {
=======
	private CheckInCheckOutController() {}
	
	public static CheckInCheckOutController getInstance() {
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
        if (instance == null) {
            instance = new Cico_Manager();
        }
        return instance;
    }
	
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
	
	public void updatecico(Cico cico) {
=======
	public void updateCheckInCheckOut(CheckInCheckOut cico) {
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
		checkInList.remove(cico);
		checkInList.add(cico);
        storeData();
    }
	
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
	
	public Cico getGuest(int ID) {
        for (Cico cico : checkInList) {
=======
	public CheckInCheckOut getGuest(int ID) {
        for (CheckInCheckOut cico : checkInList) {
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
            if (cico.getCheckInCheckOut_ID() == ID)
                return cico;
        }
        return null;
    }
	
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
	
	public Cico getGuestOut(int ID) {
        for (Cico cico2 : checkInList) {
=======
	public CheckInCheckOut getGuestOut(int ID) {
        for (CheckInCheckOut cico2 : checkInList) {
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
            if (cico2.getGuest().getGuest_ID() == ID) {
            	if(cico2.getStatus().equals("Checked-In")) {
            		return cico2; }
            }
        }
        return null;
    }
	
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
	
=======
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
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
	
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
	
    public void deleteCheckIn(Cico cico)  {
=======
    public void removeCheckIn(CheckInCheckOut cico)  {
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
    	checkInList.remove(cico);
        storeData();
    }

<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
    
    public void insertCheckIn(Cico cico) {
=======
    public void addCheckIn(CheckInCheckOut cico) {
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
    	checkInList.add(cico);
        storeData();
    }
    
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
    
=======
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
    public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/CheckInCheckOut.ser"));
            out.writeInt(checkInList.size());
            out.writeInt(Cico.getMaxID());
            for (Cico cico : checkInList)
                out.writeObject(cico);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
    
=======
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
    public void loadData () {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/CheckInCheckOut.ser"));

            int noOfOrdRecords = ois.readInt();
            Guest.setMaxID(ois.readInt());
            System.out.println("CheckInController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
<<<<<<< HEAD:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/Cico_Manager.java
                checkInList.add((Cico) ois.readObject());
                //orderList.get(i).getTable().setAvailable(false);
=======
                checkInList.add((CheckInCheckOut) ois.readObject());
>>>>>>> 815e9e30f7e2b7359bdf310d93c1c1c1dd138ea6:HotelSystem-source-code-with-javadocs/src/hotelsystem/controller/CheckInCheckOutController.java
            }
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

}
