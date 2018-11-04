package it.watchmefly.hotelroomapp.room.openroom.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import it.watchmefly.hotelroomapp.room.openroom.repository.OpenRoomRepository;
import it.watchmefly.hotelroomapp.room.openroom.service.OpenRoomBusiness;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OpenRoomBusinessImpl implements OpenRoomBusiness {

	private OpenRoomRepository repository;
	private Calendar today;
	private Function<OpenRoom, OpenRoom> closeRoomFunc = x -> {
		x.setEndDate(this.today); return x;
	};
	private Function<OpenRoom, OpenRoom> openDateRoomFunc = x -> {
		if(x.getStartDate() == null)
			x.setStartDate(this.today); 
		return x;
	};
	
	
	public OpenRoomBusinessImpl(OpenRoomRepository repository) {
		this.repository = repository;
		this.today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
	}

	@Override
	public Mono<ResponseEntity<OpenRoom>> openRoom(OpenRoom room) {
		return this.repository.findByRoomNumberAndReservationIdAndHotelId(room.getRoomNumber(), room.getReservationId() , room.getHotelId())
				.filter( x -> x.getEndDate() == null || x.getEndDate().after(this.today))
				.hasElements()
				.filter( x -> !x.booleanValue())
				.flatMap(x ->  Mono.just(room))
				.map(this.openDateRoomFunc::apply)
				.flatMap(this.repository::insert)
				.flatMap( x -> Mono.just(ResponseEntity.ok().body(x)))
				.switchIfEmpty(  Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(null) ))
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
	}

	@Override
	public Mono<ResponseEntity<List<OpenRoom>>> closeRoom(OpenRoom room) {
		return this.repository.findByRoomNumberAndReservationIdAndHotelId(room.getRoomNumber(), room.getReservationId() , room.getHotelId())
			.filter( x -> x.getEndDate() == null || x.getEndDate().after(this.today))
			.map(this.closeRoomFunc::apply)
			.flatMap(this.repository::save)
			.collectList()
			.flatMap( x -> Mono.just(ResponseEntity.ok().body(x)))
			.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)))
			.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
	}

	@Override
	public Mono<ResponseEntity<List<OpenRoom>>> updateRoom(OpenRoom room) {
		return this.repository.findByRoomNumberAndReservationIdAndHotelId(room.getRoomNumber(), room.getReservationId() , room.getHotelId())
				.filter( x -> x.getEndDate() == null || x.getEndDate().after(this.today))
				.flatMap(this.repository::save)
	   			.collectList()
	   			.flatMap( list -> Mono.just(ResponseEntity.ok().body(list)))
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

	@Override
	public Mono<ResponseEntity<List<OpenRoom>>> getAllRooms() {
		return this.repository.findAll()
				.filter( x -> x.getEndDate() == null || x.getEndDate().after(this.today))
				.collectList()
				.flatMap( list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

	@Override
	public  Mono<ResponseEntity<List<OpenRoom>>> getRoom(OpenRoom room) {
		return this.repository.findByRoomNumberAndReservationIdAndHotelId(room.getRoomNumber(), room.getReservationId() , room.getHotelId())
				.filter( x -> x.getEndDate() == null || x.getEndDate().after(this.today))
				.collectList()
				.flatMap( list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

	@Override
	public Mono<ResponseEntity<List<OpenRoom>>> getHotelRooms(String hotelId) {
		return this.repository.findAllByHotelIdOrderByRoomNumber(hotelId)
				.filter( x -> x.getEndDate() == null || x.getEndDate().after(this.today))
				.collectList()
				.flatMap( list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );		
	}

	@Override
	public Mono<ResponseEntity<List<OpenRoom>>> getReservationRooms(String hotelId, String reservationId) {
		return this.repository.findAllByReservationIdAndHotelIdOrderByRoomNumber(reservationId, hotelId)
				.filter( x -> x.getEndDate() == null || x.getEndDate().after(this.today))
				.collectList()
				.flatMap( list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );	
	}

	@Override
	public Mono<ResponseEntity<List<OpenRoom>>> openMultipleRooms(List<OpenRoom> roomList) {
	   return Flux.fromIterable(roomList)
	   			.map(this.openDateRoomFunc::apply)
	   			.flatMap(this.repository::insert)
	   			.collectList()
	   			.flatMap(  list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

	@Override
	public Mono<ResponseEntity<List<OpenRoom>>> closeHotelsRoom(String hotelId) {
		return this.repository.findAllByHotelIdOrderByRoomNumber(hotelId)
				.filter( x ->  x.getEndDate() == null || x.getEndDate().after(this.today))
				.map(this.closeRoomFunc::apply)
				.flatMap(this.repository::save)
				.collectList()
				.flatMap(  list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

	@Override
	public Mono<ResponseEntity<List<OpenRoom>>> closeReservationRooms(String hotelId, String reservationId) {
		return this.repository.findAllByReservationIdAndHotelIdOrderByRoomNumber(reservationId, hotelId)
				.filter( x ->  x.getEndDate() == null || x.getEndDate().after(this.today))
				.map(this.closeRoomFunc::apply)
				.flatMap(this.repository::save)
				.collectList()
				.flatMap(  list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );	
	}
	
	/* OLD IMPLEMENTATIONS
		@Override
		public Mono<ResponseEntity<Object>> openRoom(OpenRoom room) {
			if(room.getStartDate() == null) {
				room.setStartDate(this.today);
			}
			return this.repository.findByRoomNumberAndReservationIdAndHotelId(room.getRoomNumber(), room.getReservationId() , room.getHotelId())
					.hasElements()
					.filter( x ->  x.equals(Boolean.TRUE))
					.flatMap( x -> Mono.just(ResponseEntity.badRequest().body( (Object) "Room Already Opened")))
					.switchIfEmpty(
							this.repository.insert(room)
							.flatMap( x -> Mono.just(ResponseEntity.ok().body( (Object) room)))
							.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null)))
					)
					.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
		}
	
		@Override
		public Mono<ResponseEntity<Object>> closeRoom(OpenRoom room) {
			return this.repository.findByRoomNumberAndReservationIdAndHotelId(room.getRoomNumber(), room.getReservationId() , room.getHotelId())
			.filter( x -> x.getEndDate() == null || x.getEndDate().after(this.today))
			.map(this.closeRoomFunc::apply)
			.flatMap(this.repository::save)
			.hasElements()
			.filter( x ->  x.equals(Boolean.TRUE))
			.flatMap( x -> Mono.just(ResponseEntity.ok().body( (Object) "Room Closed")))
			.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room Not Found")))
			.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
		}
	*/

}
