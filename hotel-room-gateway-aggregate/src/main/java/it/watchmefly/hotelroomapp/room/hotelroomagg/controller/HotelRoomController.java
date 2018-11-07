package it.watchmefly.hotelroomapp.room.hotelroomagg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotelroom")
public class HotelRoomController {
	
	@Autowired
    private WebClient.Builder webClientBuilder;
	
	@GetMapping
	private Mono<ResponseEntity> getHotel(@RequestParam("hotelId") String hoteld) {
		/*Mono<ResponseEntity<List<Object>>>*/ Mono<ResponseEntity> hotelSearch =  webClientBuilder.build().get().uri("http://hotel/hotel/{hoteld}", hoteld).retrieve().bodyToMono(ResponseEntity.class);		
		return hotelSearch;
	}
	
	@GetMapping("/test")
	private String test() {
		return "app is running";
	}

}
