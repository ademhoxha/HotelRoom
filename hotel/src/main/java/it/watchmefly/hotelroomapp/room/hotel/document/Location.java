package it.watchmefly.hotelroomapp.room.hotel.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
	
	private String city;
	private String state;
	private String coordinates;
	
}
