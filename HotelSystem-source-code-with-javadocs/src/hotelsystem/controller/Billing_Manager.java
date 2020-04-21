package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.BillPayment;


@SuppressWarnings("serial")
public class Billing_Manager implements Serializable{
	private static Billing_Manager instance = null;
	private final ArrayList<BillPayment> billList = new ArrayList<>();
	
	// Constructor
	private Billing_Manager() {}
	
	
	public static Billing_Manager getInstance() {
        if (instance == null) {
            instance = new Billing_Manager();
        }
        return instance;
    }
	
	BillPayment getBill(int ID) {
		for (BillPayment bill : billList) {
            if (bill.getBillPayment_ID() == ID)
                return bill;
        }
        return null;
    }

	
    public void insertbill(BillPayment bill) {
		billList.add(bill);
        storeData();
    }
	
  
	public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/BillPayment.ser"));
            out.writeInt(billList.size());
            out.writeInt(BillPayment.getMaxID());
            for (BillPayment bill : billList)
                out.writeObject(bill);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	
    public void loadData () {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("DB/BillPayment.ser"));

            int noOfOrdRecords = ois.readInt();
            BillPayment.setMaxID(ois.readInt());
            System.out.println("BillPaymentController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                billList.add((BillPayment) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

}
