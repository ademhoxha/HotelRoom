package it.watchmefly.hotelroomapp.room.openroom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import reactor.core.publisher.Mono;

public interface ReservationRoomsBusiness {
		
	// manage reservation rooms 
	Mono<ResponseEntity<List<OpenRoom>>> getReservationRooms(String hotelId, String reservationId);
	Mono<ResponseEntity<List<OpenRoom>>> closeReservationRooms(String hotelId, String reservationId);
	
}
