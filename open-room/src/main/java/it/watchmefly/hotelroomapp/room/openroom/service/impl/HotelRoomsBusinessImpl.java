package it.watchmefly.hotelroomapp.room.openroom.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import it.watchmefly.hotelroomapp.room.openroom.repository.OpenRoomRepository;
import it.watchmefly.hotelroomapp.room.openroom.service.HotelRoomsBusiness;
import reactor.core.publisher.Mono;

@Service
public class HotelRoomsBusinessImpl implements HotelRoomsBusiness {

	/*@Autowired*/
	private OpenRoomRepository repository;
	private Calendar today;
	private Function<OpenRoom, OpenRoom> closeRoomFunc = x -> {
		x.setEndDate(this.today); return x;
	};
	
	public HotelRoomsBusinessImpl() {
		this.today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
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
}

