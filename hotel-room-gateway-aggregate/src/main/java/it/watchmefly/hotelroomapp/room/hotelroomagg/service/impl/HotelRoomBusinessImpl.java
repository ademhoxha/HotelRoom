package it.watchmefly.hotelroomapp.room.hotelroomagg.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import it.watchmefly.hotelroomapp.room.hotelroomagg.document.HotelRoom;
import it.watchmefly.hotelroomapp.room.hotelroomagg.service.HotelRoomBusiness;
import reactor.core.publisher.Mono;

public class HotelRoomBusinessImpl implements HotelRoomBusiness{
	
	private WebClient.Builder webClientBuilder;
	
	@Override
	public WebClient.Builder getWebClientBuilder() {
		return webClientBuilder;
	}

	@Override
	public void setWebClientBuilder(WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}
	
	@Override
	public Mono<ResponseEntity<Object>> openRoom(HotelRoom room) {
		BodyInserter<Mono<HotelRoom>, ReactiveHttpOutputMessage> inserterBody = BodyInserters.fromPublisher(Mono.just(room), HotelRoom.class);
		
		return webClientBuilder.build().get().uri("http://hotel/hotel?hotelId="+room.getHotelId()).exchange()
				.filter( x -> x.statusCode() != HttpStatus.OK)
				.flatMap( x -> Mono.just( ResponseEntity.status( x.statusCode()).body((Object) room) ) )
				.switchIfEmpty( 
						webClientBuilder.build().post().uri("http://open-room/room").body(inserterBody).exchange()
						.flatMap(x -> Mono.just( ResponseEntity.status( x.statusCode()).body(room) ))
				)
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(room)));
	}

	@Override
	public Mono<ResponseEntity<List<Object>>> closeRoom(HotelRoom room) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ResponseEntity<List<Object>>> findRoom(HotelRoom room) {
		// TODO Auto-generated method stub
		return null;
	}

}
