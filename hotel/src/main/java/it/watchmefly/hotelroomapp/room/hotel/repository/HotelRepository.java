package it.watchmefly.hotelroomapp.room.hotel.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;

import it.watchmefly.hotelroomapp.room.hotel.document.Hotel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface HotelRepository extends ReactiveMongoRepository< Hotel ,String>{
	
	Flux<Hotel> findByHotelId(String hotelId);
	
	Mono<Void> deleteByHotelId(String hotelId);

}
