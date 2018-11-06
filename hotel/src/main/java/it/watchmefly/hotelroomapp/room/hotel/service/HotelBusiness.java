package it.watchmefly.hotelroomapp.room.hotel.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import it.watchmefly.hotelroomapp.room.hotel.document.Hotel;
import reactor.core.publisher.Mono;

public interface HotelBusiness extends RepositoryBusiness {

	
	Mono<ResponseEntity<Hotel>> insertHotel(Hotel hotel);
	Mono<ResponseEntity<List<Hotel>>> getHotel(String hoteld);
	Mono<ResponseEntity<List<Hotel>>> updateHotel(Hotel hotel); 
	Mono<ResponseEntity<List<Hotel>>> closeHotel(String hoteld);
	Mono<ResponseEntity<List<Hotel>>> getAllHotels();
	Mono<ResponseEntity<List<Hotel>>> closeAllHotel();
}
