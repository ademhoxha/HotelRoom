package it.watchmefly.hotelroomapp.room.openroom.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import it.watchmefly.hotelroomapp.room.openroom.repository.OpenRoomRepository;
import it.watchmefly.hotelroomapp.room.openroom.service.RoomBusiness;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomBusinessImpl implements RoomBusiness {

	/*@Autowired*/
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
	
	public RoomBusinessImpl() {
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
	public  Mono<ResponseEntity<List<OpenRoom>>> getRoom(OpenRoom room) {
		return this.repository.findByRoomNumberAndReservationIdAndHotelId(room.getRoomNumber(), room.getReservationId() , room.getHotelId())
				.filter( x -> x.getEndDate() == null || x.getEndDate().after(this.today))
				.collectList()
				.flatMap( list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
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
	public Mono<ResponseEntity<List<OpenRoom>>> openMultipleRooms(List<OpenRoom> roomList) {
	   return Flux.fromIterable(roomList)
	   			.map(this.openDateRoomFunc::apply)
	   			.flatMap(this.repository::insert)
	   			.collectList()
	   			.flatMap(  list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

}
