package it.watchmefly.hotelroomapp.room.openroom.controller;

import java.util.Calendar;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import it.watchmefly.hotelroomapp.room.openroom.repository.OpenRoomRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/openroom")
public class OpenRoomController {
	
	@Autowired
	private OpenRoomRepository repository;
	
	
	@PostMapping
	private Flux<OpenRoom> insert( @RequestBody OpenRoom room){	
		if(room.getStartDate() == null) {
			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);
			room.setStartDate(today);
		}
		
		return this.repository.findByRoomNumberAndReservationIdAndHotelId(
				room.getRoomNumber(), room.getReservationId() , room.getHotelId())
		.switchIfEmpty(repository.insert(room).flux());
	}
	
	@GetMapping("/{roomNumber}")
	private Flux<OpenRoom> find(@PathVariable Integer roomNumber){
		Function<OpenRoom,OpenRoom> hidePasswordFunc = x -> { x.setPassword(""); return x;};
		return this.repository.findAllfindByRoomNumber(roomNumber).map(hidePasswordFunc::apply);
	}

	@GetMapping()
	private Flux<OpenRoom> findAll() {
		return this.repository.findAll();
	}
	
	@PutMapping()
	private Mono<OpenRoom> update(@RequestBody OpenRoom room){
		return this.repository.save(room);
	}
	
	@DeleteMapping()
	private Mono<OpenRoom> closeRoom(@RequestBody OpenRoom room){
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		Function<OpenRoom, OpenRoom> closeRoomFunc = x -> {
			x.setEndDate(today); return x;
		};
		
		return this.repository.findByRoomNumberAndReservationIdAndHotelId(
				room.getRoomNumber(), room.getReservationId() , room.getHotelId())
		.map(closeRoomFunc::apply)
		.flatMap(this.repository::save).switchIfEmpty(Flux.just(new OpenRoom())).last();
	}

}
