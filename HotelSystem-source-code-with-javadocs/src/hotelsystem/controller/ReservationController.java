package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import hotelsystem.entity.Reservation;
import hotelsystem.entity.RoomStatus;

@SuppressWarnings("serial")
public class ReservationController implements Serializable{
	private static ReservationController instance = null;
	private final ArrayList<Reservation> reservationList = new ArrayList<>();
	
	private ReservationController() {}
	
	public static ReservationController getInstance() {
		if (instance == null) {
            instance = new ReservationController();
        }
        return instance;
	}
	
	public void updateReservation(Reservation reservation) {
		reservationList.remove(reservation);
		reservationList.add(reservation);
		storeData();
	}
	
	public Reservation getReservation(String rCode) {
		for (Reservation reservation : reservationList) {
            if (reservation.getReservationCode().equals(rCode))
                return reservation;
        }
        return null;
	}
	
	public Reservation getReservationByID(int rID) {
		for (Reservation reservation : reservationList) {
            if (reservation.getReservationID() == rID)
                return reservation;
        }
        return null;
	}

	public void addReservation(Reservation reservation) {
		reservationList.add(reservation);
        storeData();
	}
	
	public ArrayList<Reservation> getWaitList() {
		ArrayList<Reservation> waitList = new ArrayList<>();
		for (Reservation r : reservationList) {
			if(r.getStatus().equals("WaitList"))
				waitList.add(r);
        }
		return waitList;
	}
	
	public void checkExpiredRoom(){
		Date current = new Date();
		for (Reservation r : reservationList) {
			ArrayList<RoomStatus> rsList = r.getStatusList();
			for (RoomStatus rs : rsList) {
				Date newDate = new Date(rs.getDate_from().getTime() + TimeUnit.HOURS.toMillis(1));
				if(current.after(newDate) && rs.getStatus().equals("Reserved")) {
					RoomStatusController.getInstance().updateStatustoExpired(rs);
					r.setStatus("Expired");
					updateReservation(r);
					System.out.println("Reservation " + r.getReservationCode() + " is Expired");
				}
			}
		}
	}
	
	public ArrayList<Reservation> getAllReservation() {
		return reservationList;
	}
	
	public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/Reservation.ser"));
            out.writeInt(reservationList.size());
            out.writeInt(Reservation.getIncID());
            for (Reservation reservation : reservationList)
                out.writeObject(reservation);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadData () {
        ObjectInputStream ois;
        try {
        	ois = new ObjectInputStream(new FileInputStream("DB/Reservation.ser"));

            int noOfOrdRecords = ois.readInt();
            Reservation.setIncID(ois.readInt());
            System.out.println("ReservationController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
            	reservationList.add((Reservation) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e1 ) {
            e1.printStackTrace();
        }
    }
}
