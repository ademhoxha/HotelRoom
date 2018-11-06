package it.watchmefly.hotelroomapp.room.openroom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import reactor.core.publisher.Mono;

public interface HotelRoomsBusiness extends RepositoryBusiness {
	
	// manage hotel rooms 
	Mono<ResponseEntity<List<OpenRoom>>> getHotelRooms(String hotelId);
	Mono<ResponseEntity<List<OpenRoom>>> closeHotelsRoom(String hotelId);
	
}
