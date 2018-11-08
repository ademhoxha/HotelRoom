package it.watchmefly.hotelroomapp.room.hotelroomagg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotelroom")
public class HotelRoomController {
	
	@Autowired
    private WebClient.Builder webClientBuilder;
	
	@GetMapping
	private Mono<ResponseEntity<List<Object>>> getAllRooms() {
		ParameterizedTypeReference<ResponseEntity<List<Object>>> typeRef = new ParameterizedTypeReference<ResponseEntity<List<Object>>>() {};
		Mono<ResponseEntity<List<Object>>> hotelSearch =  webClientBuilder.build().get().uri("http://open-room/room/all").retrieve().bodyToMono(typeRef);		
		return hotelSearch;
	}

}
