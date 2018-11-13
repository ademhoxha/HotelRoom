package it.watchmefly.hotelroomapp.room.hotelroomagg.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import it.watchmefly.hotelroomapp.room.hotelroomagg.document.HotelRoom;
import reactor.core.publisher.Mono;

public interface HotelRoomBusiness extends WebClientBusiness {
	Mono<ResponseEntity<HotelRoom>>  openRoom(HotelRoom room);
	// this two methods are not needed in aggregate form
	Mono<ResponseEntity<List<HotelRoom>>>  closeRoom(HotelRoom room);
	Mono<ResponseEntity<List<HotelRoom>>> findRoom(HotelRoom room);
}
