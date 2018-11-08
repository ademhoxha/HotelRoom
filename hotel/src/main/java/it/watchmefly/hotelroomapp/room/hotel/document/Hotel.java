package it.watchmefly.hotelroomapp.room.hotel.document;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("Hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

	@Id
	private String id;
	private String hotelId;
	private String hotelName;
	private Location hotelLocation;
	private Calendar closingDate;

}
