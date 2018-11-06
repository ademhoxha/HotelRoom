package it.watchmefly.hotelroomapp.room.hotel.document;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Hotel")
public class Hotel {

	@Id
	private String id;
	private String hotelId;
	private String hotelName;
	private Location hotelLocation;
	private Calendar closingDate;
	
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public Location getHotelLocation() {
		return hotelLocation;
	}
	public void setHotelLocation(Location hotelLocation) {
		this.hotelLocation = hotelLocation;
	}
	public Calendar getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(Calendar closingDate) {
		this.closingDate = closingDate;
	}
	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", hotelName=" + hotelName + ", hotelLocation=" + hotelLocation
				+ ", closingDate=" + closingDate + "]";
	}
	public Hotel(String hotelId, String hotelName, Location hotelLocation, Calendar closingDate) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.hotelLocation = hotelLocation;
		this.closingDate = closingDate;
	}
	public Hotel() {
		super();
	}

}
