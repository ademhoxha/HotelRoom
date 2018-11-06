package it.watchmefly.hotelroomapp.room.hotel.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.watchmefly.hotelroomapp.room.hotel.document.Hotel;
import it.watchmefly.hotelroomapp.room.hotel.repository.HotelRepository;
import it.watchmefly.hotelroomapp.room.hotel.service.HotelBusiness;
import reactor.core.publisher.Mono;

@Service
public class HotelBusinessImpl implements HotelBusiness {

	HotelRepository repository;
	private Calendar today;
	private Function<Hotel, Hotel> closeHotelLambda = x -> {
		x.setClosingDate(this.today); return x;
	};
	
	private Function<Hotel, Boolean> isValidHotelLambda =  x -> x.getClosingDate() == null || x.getClosingDate().after(this.today);
	
	public HotelBusinessImpl() {
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
	public Mono<ResponseEntity<Hotel>> insertHotel(Hotel hotel) {
		return this.repository.findByHotelId(hotel.getHotelId())
				.filter(this.isValidHotelLambda::apply)
				.hasElements()
				.filter(x -> !x.booleanValue())
				.flatMap(x ->  Mono.just(hotel))
				.flatMap(this.repository::insert)
				.flatMap( x -> Mono.just(ResponseEntity.ok().body(x)))
				.switchIfEmpty(  Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(null) ))
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
	}

	@Override
	public Mono<ResponseEntity<List<Hotel>>> getHotel(String hoteld) {
		return this.repository.findByHotelId(hoteld)
				.filter(this.isValidHotelLambda::apply)
				.collectList()
				.flatMap( x -> Mono.just(ResponseEntity.ok().body(x)))
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

	@Override
	public Mono<ResponseEntity<List<Hotel>>> updateHotel(Hotel hotel) {
		return this.repository.findByHotelId(hotel.getHotelId())
				.filter(this.isValidHotelLambda::apply)
				.flatMap(this.repository::save)
	   			.collectList()
	   			.flatMap( list -> Mono.just(ResponseEntity.ok().body(list)))
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

	@Override
	public Mono<ResponseEntity<List<Hotel>>> closeHotel(String hoteld) {
		return this.repository.findByHotelId(hoteld)
				.filter(this.isValidHotelLambda::apply)
				.map(this.closeHotelLambda::apply)
				.flatMap(this.repository::save)
				.collectList()
				.flatMap( x -> Mono.just(ResponseEntity.ok().body(x)))
				.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)))
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)));
	}

	@Override
	public Mono<ResponseEntity<List<Hotel>>> getAllHotels() {
		return this.repository.findAll()
				.filter(this.isValidHotelLambda::apply)
				.collectList()
				.flatMap( list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

	@Override
	public Mono<ResponseEntity<List<Hotel>>> closeAllHotel() {
		return this.repository.findAll()
				.filter(this.isValidHotelLambda::apply)
				.map(this.closeHotelLambda::apply)
				.flatMap(this.repository::save)
				.collectList()
				.flatMap( list -> Mono.just(ResponseEntity.ok().body(list) ) )
				.switchIfEmpty( Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) ) )
				.doOnError(x -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)) );
	}

}
