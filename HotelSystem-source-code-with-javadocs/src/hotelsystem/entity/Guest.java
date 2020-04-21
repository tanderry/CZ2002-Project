package hotelsystem.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Guest implements Serializable{
	private static int maxID = 1;
	private int guest_ID;
	private String nric;
	private String name;
	private String address;
	private long phone;
	private String country;
	private char sex;
	private String nationality;
	private Card cardDetails;
	
	public Guest(String nric, String name, String address, long phone, String country,
			char sex, String nationality, Card cardDetails) {
		this.guest_ID = maxID;
		this.nric = nric;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.country = country;
		this.sex = sex;
		this.nationality = nationality;
		this.cardDetails = cardDetails;
		maxID++;
	}
	
	public Guest(String name) { this.name = name; }
	
	public static int getMaxID() { return maxID; }

    public static void setMaxID(int ID) { maxID = ID; }

	public int getGuest_ID() { return guest_ID; }

	public void setGuest_ID(int guest_ID) { this.guest_ID = guest_ID; }

	public String getIdentity_no() { return nric; }

	public void setIdentity_no(String nric) { this.nric = nric; }

	public String getName() { return name;}

	public void setName(String name) { this.name = name; }

	public String getAddress() { return address; }

	public void setAddress(String address) { this.address = address; }

	public long getContact_no() { return phone; }

	public void setContact_no(long phone) { this.phone = phone; }

	public String getCountry() { return country; }

	public void setCountry(String country) { this.country = country; }

	public char getGender() { return sex; }

	public void setGender(char sex) { this.sex = sex; }

	public String getNationality() { return nationality; }

	public void setNationality(String nationality) { this.nationality = nationality; }

	public Card getCardDetails() { return cardDetails; }

	public void setCardDetails(Card cardDetails) { this.cardDetails = cardDetails; }

}




