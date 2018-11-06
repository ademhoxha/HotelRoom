package it.watchmefly.hotelroomapp.room.hotel.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import it.watchmefly.hotelroomapp.room.hotel.document.Hotel;
import reactor.core.publisher.Mono;

public interface HotelAddressBusiness extends RepositoryBusiness {

	Mono<ResponseEntity<List<Hotel>>> updateHotelAddress(Hotel hotel);
}
