package it.watchmefly.hotelroomapp.room.openroom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import reactor.core.publisher.Mono;

public interface OpenRoomBusiness {
	
	// manage single room 
	Mono<ResponseEntity<OpenRoom>> openRoom(OpenRoom room);
	Mono<ResponseEntity<List<OpenRoom>>> getRoom(OpenRoom room);
	Mono<ResponseEntity<List<OpenRoom>>> updateRoom(OpenRoom room);
	Mono<ResponseEntity<List<OpenRoom>>> closeRoom(OpenRoom room);
	
	// manage all rooms 
	Mono<ResponseEntity<List<OpenRoom>>> getAllRooms();
	Mono<ResponseEntity<List<OpenRoom>>> openMultipleRooms(List<OpenRoom> roomList);
	
	// manage hotel rooms 
	Mono<ResponseEntity<List<OpenRoom>>> getHotelRooms(String hotelId);
	Mono<ResponseEntity<List<OpenRoom>>> closeHotelsRoom(String hotelId);
		
	// manage reservation rooms 
	Mono<ResponseEntity<List<OpenRoom>>> getReservationRooms(String hotelId, String reservationId);
	Mono<ResponseEntity<List<OpenRoom>>> closeReservationRooms(String hotelId, String reservationId);
	
	
}
