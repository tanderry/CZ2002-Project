package hotelsystem.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import hotelsystem.entity.Room_Status;


public class Room_Status_Manager extends Database_Manager{
	private static final String DB_PATH = "DB/RoomStatus.dat";
	private static Room_Status_Manager instance = null;
	private final ArrayList<Room_Status> roomStatusList;
	
	
	private Room_Status_Manager() {
		roomStatusList = new ArrayList<>();
    }
	
	
	public static Room_Status_Manager getInstance() {
        if (instance == null) {
            instance = new Room_Status_Manager();
        }
        return instance;
    }
	
	
	public void updateStatustoCheckedIn(Room_Status roomStatus) {
		for (Room_Status status : roomStatusList) {
            if (status.getRoomBookings_ID() == roomStatus.getRoomBookings_ID()) {
            	status.setStatus("Checked-In");
            }
        }
        SaveDB();
    }
	
	
	public void updateStatustoCheckedOut(Room_Status roomStatus) {
		for (Room_Status status : roomStatusList) {
            if (status.getRoomBookings_ID() == roomStatus.getRoomBookings_ID()) {
            	status.setStatus("Checked-Out");
            }
        }
        SaveDB();
    }
	
	
	public void updateStatustoCancelled(Room_Status roomStatus) {
		for (Room_Status status : roomStatusList) {
            if (status.getRoomBookings_ID() == roomStatus.getRoomBookings_ID()) {
            	status.setStatus("Cancelled");
            }
        }
        SaveDB();
	}
	
	
	public void updateStatustoExpired(Room_Status roomStatus) {
		for (Room_Status status : roomStatusList) {
            if (status.getRoomBookings_ID() == roomStatus.getRoomBookings_ID()) {
            	status.setStatus("Expired");
            }
        }
        SaveDB();
	}
	
	
	public void updateWaitList(ArrayList<Room_Status> rsList) {
		for (Room_Status status : rsList) {
        	status.setStatus("Confirmed");
        }
        SaveDB();
	}
	
	
	public Boolean checkRoomStatus(Room_Status rs) {
		Boolean check = null;
		for (Room_Status roomStatus : roomStatusList) {
			if(roomStatus.getDate_from().equals(rs.getDate_from()) && roomStatus.getDate_to().equals(rs.getDate_to())) {
				if (roomStatus.getStatus().equals("Under-Maintenance") || roomStatus.getStatus().equals("Reserved") || roomStatus.getStatus().equals("Checked-In")) 
					return check = false;
	            else
	            	return check = true;
			}
        }
		return check;
	}
	
	
	public ArrayList<Room_Status> getRoomStatus(String roomFloorNo) {
		ArrayList<Room_Status> result = new ArrayList<>();
        for (Room_Status roomStatus : roomStatusList) {
            if (roomStatus.getRoomFloor_No().equals(roomFloorNo)) 
            	result.add(roomStatus);
        }
        if(!result.isEmpty())
        	return result;
        else 
        	return null;
        
    }
	
	
	public Room_Status getStatus(String roomFloorNo) {
		Date current = new Date();
        for (Room_Status roomStatus : roomStatusList) {
            if (roomStatus.getRoomFloor_No().equals(roomFloorNo) && roomStatus.getStatus().equals("Checked-In") && current.before(roomStatus.getDate_to())) 
            	return roomStatus;
        }
        return null;
    }
	
	
	public ArrayList<Room_Status> getAllReserveRoom(Date start, Date end){
		ArrayList<Room_Status> reserveList = new ArrayList<>();
		for (Room_Status roomS : roomStatusList) {
				if(start.equals(roomS.getDate_from()) || end.equals(roomS.getDate_to())) {
					reserveList.add(roomS);
				}
		}
        return reserveList;
	}
	
	
	public void addRoomStatus(Room_Status roomStatus) {
		roomStatusList.add(roomStatus);
    	SaveDB();
    }

	
	@Override
	public boolean LoadDB() {
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
		roomStatusList.clear();
        if (checkFileExist(DB_PATH)) {
            try {
                // read String from text file
                ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

                for (String st : stringArray) {
                    // get individual 'fields' of the string separated by SEPARATOR
                    StringTokenizer token = new StringTokenizer(st, SEPARATOR);  //pass in the string to the string tokenizer using delimiter ","
                    @SuppressWarnings("unused")
					int id = Integer.parseInt(token.nextToken().trim());         //ID  
                    String room_ID = token.nextToken().trim();
                    int guest_ID = Integer.parseInt(token.nextToken().trim());
                    String status = token.nextToken().trim();
                    Date dateFrom = null;
                    Date dateTo = null;
					try {
						dateFrom = df.parse(token.nextToken().trim());
						dateTo = df.parse(token.nextToken().trim());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                    // create Room object from file data
                    Room_Status roomStatus = new Room_Status(room_ID,guest_ID,status,dateFrom,dateTo);
                    // add to Room list
                    roomStatusList.add(roomStatus);
                }

                System.out.printf("RoomStatusController: %,d Entries Loaded.\n", roomStatusList.size());
                return true;

            } catch (IOException | NumberFormatException ex) {
                System.out.println("[ERROR] Read Error! Database for RoomStatus is not loaded!");
                //Logger.getLogger(PromoController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

        } else {
            System.out.println("[ERROR] File not found! Database for RoomStatus is not loaded!");
            return false;
        }
	}

	
	@Override
	public void SaveDB() {
		// TODO Auto-generated method stub
		List<String> output = new ArrayList<>();
        StringBuilder st = new StringBuilder();
        if (checkFileExist(DB_PATH)) {
            // Parse Content to Write
            for (Room_Status roomStatus : roomStatusList) {
                st.setLength(0); 				// Clear Buffer
                st.append(roomStatus.getRoomBookings_ID()); 	// ID
                st.append(SEPARATOR);
                st.append(roomStatus.getRoomFloor_No()); 	// Room_ID
                st.append(SEPARATOR);
                st.append(roomStatus.getGuest_ID()); 	// Room_ID
                st.append(SEPARATOR);
                st.append(roomStatus.getStatus()); // Room Status
                st.append(SEPARATOR);
                st.append(roomStatus.getDate_from()); 	// Date From
                st.append(SEPARATOR);
                st.append(roomStatus.getDate_to()); 	// Date To
                st.append(SEPARATOR);

                output.add(st.toString());
            }

            // Attempt to save to file
            try {
                write(DB_PATH, output);
                //System.out.printf("RoomStatusController: %,d Entries Saved.\n",
                        //output.size());
            } catch (Exception ex) {
                System.out.println("[Error] Write Error! Changes not saved!");
            }
        } else {
            System.out.println("[ERROR] File not found! Changes not Saved!");
        }
	}
}
