package it.watchmefly.hotelroomapp.room.hotelroomagg.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import it.watchmefly.hotelroomapp.room.hotelroomagg.document.HotelRoom;
import reactor.core.publisher.Mono;

public interface HotelRoomBusiness extends WebClientBusiness {
	
	Mono<ResponseEntity<Object>>  openRoom(HotelRoom room);
	
	Mono<ResponseEntity<List<Object>>>  closeRoom(HotelRoom room);
	
	Mono<ResponseEntity<List<Object>>> findRoom(HotelRoom room);

}
