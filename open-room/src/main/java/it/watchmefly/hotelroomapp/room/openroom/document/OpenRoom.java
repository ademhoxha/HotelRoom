package it.watchmefly.hotelroomapp.room.openroom.document;

import java.util.Calendar;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document( collection = "OpenRoom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenRoom {
	
	@Id
	private String id;
	private Integer roomNumber;
	private String hotelId;
	private String reservationId;
	private String password;
	private Calendar startDate;
	private Calendar endDate;
}
