package it.watchmefly.hotelroomapp.room.openroom.document;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document( collection = "OpenRoom")
public class OpenRoom {
	
	@Id
	private String id;
	
	private Integer roomNumber;
	private String hotelId;
	private String reservationId;
	
	private String password;
	
	private Calendar startDate;
	private Calendar endDate;

	
	public OpenRoom(Integer roomNumber, String hotelId, String reservationId, String password, Calendar startDate,
			Calendar endDate) {
		super();
		this.roomNumber = roomNumber;
		this.hotelId = hotelId;
		this.reservationId = reservationId;
		this.password = password;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public OpenRoom() {
		super();
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "OpenRoom [roomNumber=" + roomNumber + ", hotelId=" + hotelId + ", reservationId=" + reservationId
				+ ", password=" + password + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
