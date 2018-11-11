package it.watchmefly.hotelroomapp.room.hotelroomagg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import it.watchmefly.hotelroomapp.room.hotelroomagg.document.HotelRoom;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotelroom")
public class HotelRoomController {
	
	@Autowired
    private WebClient.Builder webClientBuilder;
	
	 @PostMapping
	 Mono<ResponseEntity<HotelRoom>> insertNewRoom( @RequestBody HotelRoom room) {

		BodyInserter<Mono<HotelRoom>, ReactiveHttpOutputMessage> inserterBody = BodyInserters.fromPublisher(Mono.just(room), HotelRoom.class);
							
		return webClientBuilder.build().get().uri("http://hotel/hotel?hotelId="+room.getHotelId()).retrieve()
				.onStatus(x -> x == HttpStatus.NOT_FOUND, x -> Mono.just(new Exception("Hotel Not Found")))
				.onStatus(x -> x !=HttpStatus.OK, x -> Mono.just(new Exception(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())))
				.bodyToMono(new ParameterizedTypeReference<List<HotelRoom>>() {})
				.flatMap( x -> webClientBuilder.build().post().uri("http://open-room/room")
				.body(inserterBody)
				.retrieve()
				.onStatus(y -> y !=HttpStatus.OK, z-> Mono.just(new Exception("Room Not Created")))
				.bodyToMono(new ParameterizedTypeReference<HotelRoom>() {})  )
				.flatMap( x -> Mono.just(ResponseEntity.ok().body(x)))
				.switchIfEmpty(  Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(null) ))
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );	
	}
	 
	/* @DeleteMapping
	 Mono<ResponseEntity<HotelRoom>> closeRoom( @RequestBody HotelRoom room) {

		BodyInserter<Mono<HotelRoom>, ReactiveHttpOutputMessage> inserterBody = BodyInserters.fromPublisher(Mono.just(room), HotelRoom.class);
							
		return webClientBuilder.build().get().uri("http://hotel/hotel?hotelId="+room.getHotelId()).retrieve()
				.onStatus(x -> x == HttpStatus.NOT_FOUND, x -> Mono.just(new Exception("Hotel Not Found")))
				.onStatus(x -> x !=HttpStatus.OK, x -> Mono.just(new Exception(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())))
				.bodyToMono(new ParameterizedTypeReference<List<HotelRoom>>() {})
				.flatMap( x -> webClientBuilder.build().put().uri("http://open-room/room")
				.retrieve()
				.onStatus(y -> y !=HttpStatus.OK, z-> Mono.just(new Exception("Room Not Created")))
				.bodyToMono(new ParameterizedTypeReference<HotelRoom>() {})  )
				.flatMap( x -> Mono.just(ResponseEntity.ok().body(x)))
				.switchIfEmpty(  Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(null) ))
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );	
	}*/
	 
	 
	 // old code
	/* private exe() {
		 WebClient client = WebClient.create("http://localhost:8080");
		 Mono<ClientResponse> result = client.delete().uri("").exchange();
		 
	 }*/

}
