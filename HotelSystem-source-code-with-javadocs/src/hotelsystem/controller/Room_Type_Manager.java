package hotelsystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import hotelsystem.entity.Room_Type;


public class Room_Type_Manager extends Database_Manager{
	private static final String DB_PATH = "DB/RoomType.dat";
	private static Room_Type_Manager instance = null;
	private final ArrayList<Room_Type> roomTypeList;
	
	
	private Room_Type_Manager() {
		roomTypeList = new ArrayList<>();
    }
	
	
	public static Room_Type_Manager getInstance() {
        if (instance == null) {
            instance = new Room_Type_Manager();
        }
        return instance;
    }
	
	
	public Room_Type getRoom(int roomTypeID) {
		for (Room_Type rT : roomTypeList) {
            if (rT.getTypeSerial()==roomTypeID)
                return rT;
        }
        return null;
	}
	
	
	public ArrayList<Room_Type> getAllTypes() {
		ArrayList<Room_Type> rTList = new ArrayList<>();
		for(Room_Type rT : roomTypeList) {
			rTList.add(rT);
		}
		return rTList;
	}
	
	
	public void displayAllRoomType() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ ROOM TYPES ~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(String.format("%5s %15s %8s %18s", "RoomTypeID", "RoomType", "WeekdayPrice($)", "WeekendPrice($)"));
        for (Room_Type rT : roomTypeList) {
             System.out.println(String.format("%5s %18s %10s %20s",rT.getTypeSerial(),rT.getRoomType(),rT.getWeekDayRate(),rT.getWeekEndRate()));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
	
	
	public int getAllRoomType() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ ROOM TYPES ~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(String.format("%5s %15s %8s %18s", "RoomTypeID", "RoomType", "WeekdayPrice($)", "WeekendPrice($)"));
        for (Room_Type rT : roomTypeList) {
             System.out.println(String.format("%5s %18s %10s %20s",rT.getTypeSerial(),rT.getRoomType(),rT.getWeekDayRate(),rT.getWeekEndRate()));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return roomTypeList.size();
    }
	
	
	@Override
	public boolean LoadDB() {
		roomTypeList.clear();
        if (checkFileExist(DB_PATH)) {
            try {
                // read String from text file
                ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

                for (String st : stringArray) {
                    // get individual 'fields' of the string separated by SEPARATOR
                    StringTokenizer token = new StringTokenizer(st, SEPARATOR);  //pass in the string to the string tokenizer using delimiter ","
                    int id = Integer.parseInt(token.nextToken().trim());         //ID                    
                    String roomType = token.nextToken().trim();
                    double weekdayRate = Double.parseDouble(token.nextToken().trim());
                    double weekendRate = Double.parseDouble(token.nextToken().trim());

                    // create Room object from file data
                    Room_Type rT = new Room_Type(id, roomType, weekdayRate, weekendRate);
                    // add to Room list
                    roomTypeList.add(rT);
                }

                System.out.printf("RoomTypeController: %,d Entries Loaded.\n", roomTypeList.size());
                return true;

            } catch (IOException | NumberFormatException ex) {
                System.out.println("[ERROR] Read Error! Database for RoomType is not loaded!");
                //Logger.getLogger(PromoController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        } else {
            System.out.println("[ERROR] File not found! Database for RoomType is not loaded!");
            return false;
        }
	}

	
	@Override
	public void SaveDB() {
		List<String> output = new ArrayList<>();
        StringBuilder st = new StringBuilder();
        if (checkFileExist(DB_PATH)) {
            // Parse Content to Write
            for (Room_Type rT : roomTypeList) {
                st.setLength(0); 					// Clear Buffer
                st.append(rT.getTypeSerial()); 		// ID
                st.append(SEPARATOR);
                st.append(rT.getRoomType()); 		// Room Floor No
                st.append(SEPARATOR);
                st.append(rT.getWeekDayRate()); 	
                st.append(SEPARATOR);
                st.append(rT.getWeekEndRate()); 		
                st.append(SEPARATOR);

                output.add(st.toString());
            }

            // Attempt to save to file
            try {
                write(DB_PATH, output);
                //System.out.printf("RoomTypeController: %,d Entries Saved.\n",
                        //output.size());
            } catch (Exception ex) {
                System.out.println("[Error] Write Error! Changes not saved!");
            }
        } else {
            System.out.println("[ERROR] File not found! Changes not Saved!");
        }
	}
}
