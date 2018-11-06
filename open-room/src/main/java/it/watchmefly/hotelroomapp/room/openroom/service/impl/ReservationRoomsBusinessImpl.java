package it.watchmefly.hotelroomapp.room.openroom.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import it.watchmefly.hotelroomapp.room.openroom.repository.OpenRoomRepository;
import it.watchmefly.hotelroomapp.room.openroom.service.ReservationRoomsBusiness;
import reactor.core.publisher.Mono;

@Service
public class ReservationRoomsBusinessImpl implements ReservationRoomsBusiness {

	private OpenRoomRepository repository;
	private Calendar today;
	private Function<OpenRoom, OpenRoom> closeRoomFunc = x -> {
		x.setEndDate(this.today); return x;
	};
	
	public ReservationRoomsBusinessImpl() {
		this.today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
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
	
	@Override
	public OpenRoomRepository getRepository() {
		return this.repository;
	}

	@Override
	public void setRepository(OpenRoomRepository repository) {
		this.repository = repository;
		
	}
	
}
