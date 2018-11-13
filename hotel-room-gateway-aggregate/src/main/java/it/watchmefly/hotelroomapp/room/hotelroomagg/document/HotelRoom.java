package it.watchmefly.hotelroomapp.room.hotelroomagg.document;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoom {
	
	private String hotelId;
	private String reservationId;
	private Integer roomNumber;
	private String password;
	private Calendar startDate;
	private Calendar endDate;
	
}
