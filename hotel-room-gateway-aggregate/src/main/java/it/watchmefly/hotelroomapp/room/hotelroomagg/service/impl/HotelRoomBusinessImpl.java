package it.watchmefly.hotelroomapp.room.hotelroomagg.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
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
	public Mono<ResponseEntity<HotelRoom>> openRoom(HotelRoom room) {
		BodyInserter<Mono<HotelRoom>, ReactiveHttpOutputMessage> inserterBody = BodyInserters.fromPublisher(Mono.just(room), HotelRoom.class);
		
		String getHotelUri = "http://hotel/hotel?hotelId="+room.getHotelId();
		String addRoomUri = "http://open-room/room";
		
		return this.webClientBuilder.build().get().uri(getHotelUri).exchange()
				.filter( x -> x.statusCode() != HttpStatus.OK)
				.flatMap( x -> Mono.just( ResponseEntity.status( x.statusCode()).body( (HotelRoom) null) ) )
				.switchIfEmpty( 
						this.webClientBuilder.build().post().uri(addRoomUri).body(inserterBody).exchange()
						//.flatMap(x -> Mono.just( ResponseEntity.status( x.statusCode()).body(x.statusCode() == HttpStatus.OK ? x.bodyToMono(HotelRoom.class) : room) ))
						.flatMap(
								x -> x.statusCode() != HttpStatus.OK ? Mono.just( ResponseEntity.status( x.statusCode()).body( (HotelRoom) null) )	
								: x.bodyToMono(HotelRoom.class).flatMap(y -> Mono.just(ResponseEntity.status(HttpStatus.OK).body(y)) )
						)
				)
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((HotelRoom) null)));
	}

	@Override
	public Mono<ResponseEntity<List<HotelRoom>>> closeRoom(HotelRoom room) {
		
		ParameterizedTypeReference<List<HotelRoom>> type = new ParameterizedTypeReference<List<HotelRoom>> () {};
		
		String getHotelUri = "http://hotel/hotel?hotelId="+room.getHotelId();
		String deleteRoomUri = "http://open-room/room?hotelId="+room.getHotelId()+"&reservationId="+room.getReservationId()+"&roomNumber="+room.getRoomNumber().toString() ;
		
		return this.webClientBuilder.build().get().uri(getHotelUri).exchange()
				.filter( x -> x.statusCode() != HttpStatus.OK)
				.flatMap( x -> Mono.just( ResponseEntity.status( x.statusCode()).body( (List<HotelRoom>) null) ) )
				.switchIfEmpty( 
						this.webClientBuilder.build().delete().uri(deleteRoomUri).exchange()
						.flatMap(
								x -> x.statusCode() != HttpStatus.OK ? Mono.just( ResponseEntity.status( x.statusCode()).body( (List<HotelRoom>) null) )	
								: x.bodyToMono(type).flatMap(y -> Mono.just(ResponseEntity.status(HttpStatus.OK).body(y)) )
						)
				)
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((List<HotelRoom>) null)));
	}

	@Override
	public Mono<ResponseEntity<List<HotelRoom>>> findRoom(HotelRoom room) {
		
		ParameterizedTypeReference<List<HotelRoom>> type = new ParameterizedTypeReference<List<HotelRoom>> () {};
		
		String getHotelUri = "http://hotel/hotel?hotelId="+room.getHotelId();
		String findRoomUri = "http://open-room/room?hotelId="+room.getHotelId()+"&reservationId="+room.getReservationId()+"&roomNumber="+room.getRoomNumber().toString() ;
		
		return this.webClientBuilder.build().get().uri(getHotelUri).exchange()
				.filter( x -> x.statusCode() != HttpStatus.OK)
				.flatMap( x -> Mono.just( ResponseEntity.status( x.statusCode()).body( (List<HotelRoom>) null) ) )
				.switchIfEmpty( 
						this.webClientBuilder.build().get().uri(findRoomUri).exchange()
						.flatMap(
								x -> x.statusCode() != HttpStatus.OK ? Mono.just( ResponseEntity.status( x.statusCode()).body( (List<HotelRoom>) null) )	
								: x.bodyToMono(type).flatMap(y -> Mono.just(ResponseEntity.status(HttpStatus.OK).body(y)) )
						)
				)
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((List<HotelRoom>) null)));
	}

}
