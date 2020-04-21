package hotelsystem.entity;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class BillPayment implements Serializable{
	private static int maxID = 1;
	private int billPayment_ID;
	private Check_In_Out check_in_out;
	private ArrayList<RoomService> statusList = new ArrayList<>();
	private double price;
	private double roomservicePrice;
	private double totalPrice;
	private double discount;
	private double taxAmt;
	private double finalTotal;
	private String mode;
	private Card card;
	private String status;
	
	public BillPayment(int billPayment_ID, Check_In_Out check_in_out, ArrayList<RoomService> statusList, double price,
			double roomservicePrice, double totalPrice, double discount, double taxAmt, double finalTotal,
			String mode, Card card, String status) {
		super();
		this.billPayment_ID = billPayment_ID;
		this.check_in_out = check_in_out;
		this.statusList = statusList;
		this.price = price;
		this.roomservicePrice = roomservicePrice;
		this.totalPrice = totalPrice;
		this.discount = discount;
		this.taxAmt = taxAmt;
		this.finalTotal = finalTotal;
		this.mode = mode;
		this.card = card;
		this.status = status;
	}
	
	
	public BillPayment(Check_In_Out check_in_out, ArrayList<RoomService> statusList, double price,
			double roomservicePrice, double totalPrice, double discount, double taxAmt, double finalTotal,
			String mode, Card card, String status) {
		super();
		this.billPayment_ID = maxID;
		this.check_in_out = check_in_out;
		this.statusList = statusList;
		this.price = price;
		this.roomservicePrice = roomservicePrice;
		this.totalPrice = totalPrice;
		this.discount = discount;
		this.taxAmt = taxAmt;
		this.finalTotal = finalTotal;
		this.mode = mode;
		this.card = card;
		this.status = status;
		maxID++;
	}


	public static int getMaxID() {
		return maxID;
	}


	public static void setMaxID(int maxID) {
		BillPayment.maxID = maxID;
	}


	public int getBillPayment_ID() {
		return billPayment_ID;
	}


	public void setBillPayment_ID(int billPayment_ID) {
		this.billPayment_ID = billPayment_ID;
	}


	public Check_In_Out getCheck_in_out() {
		return check_in_out;
	}


	public void setCheck_in_out(Check_In_Out check_in_out) {
		this.check_in_out = check_in_out;
	}


	public ArrayList<RoomService> getStatusList() {
		return statusList;
	}


	public void setStatusList(ArrayList<RoomService> statusList) {
		this.statusList = statusList;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public double getRoomservicePrice() {
		return roomservicePrice;
	}


	public void setRoomservicePrice(double roomservicePrice) {
		this.roomservicePrice = roomservicePrice;
	}


	public double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public double getDiscount() {
		return discount;
	}


	public void setDiscount(double discount) {
		this.discount = discount;
	}


	public double getTaxAmt() {
		return taxAmt;
	}


	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}


	public double getFinalTotal() {
		return finalTotal;
	}


	public void setFinalTotal(double finalTotal) {
		this.finalTotal = finalTotal;
	}


	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public Card getCard() {
		return card;
	}


	public void setCard(Card card) {
		this.card = card;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

}
