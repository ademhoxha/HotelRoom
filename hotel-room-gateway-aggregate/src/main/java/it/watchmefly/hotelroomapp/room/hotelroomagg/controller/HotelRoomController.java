package it.watchmefly.hotelroomapp.room.hotelroomagg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import it.watchmefly.hotelroomapp.room.hotelroomagg.document.HotelRoom;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotelroom")
public class HotelRoomController {
	
	@Autowired
    private WebClient.Builder webClientBuilder;
	
	/*@GetMapping
	private Mono<ResponseEntity<List<Object>>> getAllRooms() {
		ParameterizedTypeReference<ResponseEntity<List<Object>>> typeRef = new ParameterizedTypeReference<ResponseEntity<List<Object>>>() {};
		Mono<ResponseEntity<List<Object>>> hotelSearch =  webClientBuilder.build().get().uri("http://open-room/room/all").retrieve().bodyToMono(typeRef);		
		return hotelSearch;
	}*/
	
	@PostMapping
	 Mono<ResponseEntity<List<HotelRoom>>> insertNewRoom(@RequestBody HotelRoom room) {
		return  webClientBuilder.build().get().uri("http://hotel/hotel").attribute("hotelId", room.getHotelId() ).retrieve()
				.bodyToMono( new ParameterizedTypeReference<List<HotelRoom>>() {})
				.flatMap( x -> Mono.just(ResponseEntity.ok().body(x)))
				.switchIfEmpty(  Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(null) ))
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
	}

}
