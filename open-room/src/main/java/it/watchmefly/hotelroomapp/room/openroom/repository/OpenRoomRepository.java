package it.watchmefly.hotelroomapp.room.openroom.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Repository;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@EnableReactiveMongoRepositories
public interface OpenRoomRepository extends ReactiveMongoRepository<OpenRoom,String>{

	Flux<OpenRoom> findAllfindByRoomNumber(Integer roomNumber);
	Flux<OpenRoom> findAllByReservationIdOrderByRoomNumber(String reservationId);
	Flux<OpenRoom> findAllByHotelIdOrderByRoomNumber(String hotelId);
	
	/* are the same service  */
	Flux<OpenRoom> findByRoomNumberAndReservationIdAndHotelId( Integer roomNumber, String reservationId, String hotelId);
	@Query("{ 'hotelId' : ?2, 'reservationId' : ?1 }, 'roomNumber' : ?0")
	Flux<OpenRoom> findRoom( Integer roomNumber, String reservationId, String hotelId);
	
	
	Mono<Void> deleteByRoomNumberAndHotelId(Integer roomNumber, String hotelId);
	Mono<Void> deleteAllByReservationId(String reservationId);
	Mono<Void> deleteAllByHotelId(String hotelId);
	

}
