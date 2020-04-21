package hotelsystem.entity;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Room_Type implements Serializable{
	private int type_serial;
	private String room_type;
	private double weekday_rate;
	private double weekend_rate;
	
	public Room_Type() {}
	
	public Room_Type(int typeSerial, String roomType, double weekday_rate, double weekend_rate) {
		this.type_serial = typeSerial;
		this.room_type = roomType;
		this.weekday_rate = weekday_rate;
		this.weekend_rate = weekend_rate;
	}
	
	public int getTypeSerial() { return this.type_serial; }
	
	public String getRoomType() { return room_type; }
	
	public void setRoomType(String roomType) { this.room_type = roomType; }
	
	public double getWeekDayRate() { return weekday_rate; }
	
	public void setWeekDayRate(double weekday_rate) { this.weekday_rate = weekday_rate; }
	
	public double getWeekEndRate() { return weekend_rate; }
	
	public void setWeekEndRate(double weekend_rate) { this.weekend_rate = weekend_rate; }
}
