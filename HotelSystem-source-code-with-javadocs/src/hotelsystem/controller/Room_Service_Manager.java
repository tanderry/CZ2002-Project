package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.Room_Service;
import hotelsystem.ui.RoomService_UI;

@SuppressWarnings("serial")
public class Room_Service_Manager implements Serializable{
	private static Room_Service_Manager instance = null;
	private final ArrayList<Room_Service> roomServiceList;
	
	private Room_Service_Manager() {
		roomServiceList = new ArrayList<>();
    }
	
	public static Room_Service_Manager getInstance() {
        if (instance == null) {
            instance = new Room_Service_Manager();
        }
        return instance;
    }
	
	public void addRoomService(Room_Service roomService) {
		roomServiceList.add(roomService);
		storeData();
	}
	
	public Room_Service getRoomService(int roomStatusID) {
		for (Room_Service rS : roomServiceList) {
            if (rS.getRoomStatusID() == roomStatusID)
                return rS;
        }
        return null;
	}
	
	public void updateRoomService(int roomStatusID, String status) {
		Room_Service rs = getRoomService(roomStatusID);
		rs.setStatus(status);
		RoomService_UI.updateComplete(rs);
		storeData();
	}
	
	public ArrayList<Room_Service> getRSList(int roomStatusID) {
    	ArrayList<Room_Service> result = new ArrayList<>();
    	for (Room_Service rS : roomServiceList) {
    		if (rS.getRoomStatusID() == roomStatusID) {
    			result.add(rS);
            }
        }
    	return result;
    }
	
	public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/RoomService.ser"));
            out.writeInt(roomServiceList.size());
            out.writeInt(Room_Service.getIncID());
            for (Room_Service roomService : roomServiceList)
                out.writeObject(roomService);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadData () {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/RoomService.ser"));

            int noOfOrdRecords = ois.readInt();
            Room_Service.setIncID(ois.readInt());
            System.out.println("RoomServiceController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
            	roomServiceList.add((Room_Service) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
