package it.watchmefly.hotelroomapp.room.hotel.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.watchmefly.hotelroomapp.room.hotel.document.Hotel;
import it.watchmefly.hotelroomapp.room.hotel.repository.HotelRepository;
import it.watchmefly.hotelroomapp.room.hotel.service.HotelAddressBusiness;
import reactor.core.publisher.Mono;

@Service
public class HotelAddressBusinessImpl implements HotelAddressBusiness{
	
	HotelRepository repository;
	private Calendar today;
	private Function<Hotel, Boolean> isValidHotelLambda =  x -> x.getClosingDate() == null || x.getClosingDate().after(this.today);
	
	public HotelAddressBusinessImpl() {
		this.today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
	}
	
	@Override
	public HotelRepository getRepository() {
		return this.repository;
	}

	@Override
	public void setRepository(HotelRepository repository) {
		this.repository = repository;
	}

	@Override
	public Mono<ResponseEntity<List<Hotel>>> updateHotelAddress(Hotel hotel) {
		return this.repository.findByHotelId(hotel.getHotelId())
				.filter(this.isValidHotelLambda::apply)
				.map( x -> {x.setHotelLocation(hotel.getHotelLocation()); return x;})
				.flatMap(this.repository::save)
	   			.collectList()
	   			.flatMap( list -> Mono.just(ResponseEntity.ok().body(list)))
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}
}
