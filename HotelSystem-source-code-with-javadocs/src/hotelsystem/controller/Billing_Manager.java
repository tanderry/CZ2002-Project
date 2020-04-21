package hotelsystem.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import hotelsystem.entity.Billing;


@SuppressWarnings("serial")
public class Billing_Manager implements Serializable{
	private static Billing_Manager instance = null;
	private final ArrayList<Billing> billList = new ArrayList<>();
	
	
	private Billing_Manager() {}
	
	
	public static Billing_Manager getInstance() {
        if (instance == null) {
            instance = new Billing_Manager();
        }
        return instance;
    }
	
	
	public Billing getBill(int ID) {
		for (Billing bill : billList) {
            if (bill.getBillPayment_ID() == ID)
                return bill;
        }
        return null;
    }

    
    public void addBillPayment(Billing bill) {
		billList.add(bill);
        storeData();
    }
	
    
	public void storeData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DB/BillPayment.ser"));
            out.writeInt(billList.size());
            out.writeInt(Billing.getMaxID());
            for (Billing bill : billList)
                out.writeObject(bill);
            //System.out.printf("BillPaymentController: %,d Entries Saved.\n", billList.size());
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
            ois = new ObjectInputStream(new FileInputStream("DB/BillPayment.ser"));

            int noOfOrdRecords = ois.readInt();
            Billing.setMaxID(ois.readInt());
            System.out.println("BillPaymentController: " + noOfOrdRecords + " Entries Loaded");
            for (int i = 0; i < noOfOrdRecords; i++) {
                billList.add((Billing) ois.readObject());
                //orderList.get(i).getTable().setAvailable(false);
            }
        } catch (IOException | ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
