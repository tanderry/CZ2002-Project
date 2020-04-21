package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.RoomService;
import hotelsystem.ui.RoomServiceUI;

@SuppressWarnings("serial")
public class RoomServiceController implements Serializable{
	private static RoomServiceController instance = null;
	private final ArrayList<RoomService> roomServiceList;
	
	private RoomServiceController() {
		roomServiceList = new ArrayList<>();
    }
	
	public static RoomServiceController getInstance() {
        if (instance == null) {
            instance = new RoomServiceController();
        }
        return instance;
    }
	
	public void addRoomService(RoomService roomService) {
		roomServiceList.add(roomService);
		storeData();
	}
	
	public RoomService getRoomService(int roomStatusID) {
		for (RoomService rS : roomServiceList) {
            if (rS.getRoomStatusID() == roomStatusID)
                return rS;
        }
        return null;
	}
	
	public void updateRoomService(int roomStatusID, String status) {
		RoomService rs = getRoomService(roomStatusID);
		rs.setStatus(status);
		RoomServiceUI.updateComplete(rs);
		storeData();
	}
	
	public ArrayList<RoomService> getRSList(int roomStatusID) {
    	ArrayList<RoomService> result = new ArrayList<>();
    	for (RoomService rS : roomServiceList) {
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
            out.writeInt(RoomService.getIncID());
            for (RoomService roomService : roomServiceList)
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
            RoomService.setIncID(ois.readInt());
            System.out.println("RoomServiceController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
            	roomServiceList.add((RoomService) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
