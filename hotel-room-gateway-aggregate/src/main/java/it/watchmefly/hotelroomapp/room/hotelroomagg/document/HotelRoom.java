package it.watchmefly.hotelroomapp.room.hotelroomagg.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoom {
	
	private String hotelId;
	private Integer roomNumber;
	
}
