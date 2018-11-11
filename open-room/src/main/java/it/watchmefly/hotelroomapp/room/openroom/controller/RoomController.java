package it.watchmefly.hotelroomapp.room.openroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.watchmefly.hotelroomapp.room.openroom.config.BusinessAppConfig;
import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import it.watchmefly.hotelroomapp.room.openroom.repository.OpenRoomRepository;
import it.watchmefly.hotelroomapp.room.openroom.service.RoomBusiness;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/room")
public class RoomController {
	
	@Autowired
	private OpenRoomRepository repository;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(BusinessAppConfig.class);
	
	@PostMapping
	private Mono<ResponseEntity<OpenRoom>> insertRoom(@RequestBody OpenRoom room){	
		RoomBusiness business = this.ctx.getBean(RoomBusiness.class);
		business.setRepository(this.repository);
		return business.openRoom(room);
	}

	@GetMapping()
	private Mono<ResponseEntity<List<OpenRoom>>> findRoom(
			@RequestParam("roomNumber") String roomNumber, @RequestParam("hotelId") String hotelId, @RequestParam("reservationId") String reservationId) {
		OpenRoom room = new OpenRoom();
		room.setHotelId(hotelId);
		room.setReservationId(reservationId);
		room.setRoomNumber(new Integer(roomNumber));
		RoomBusiness business = this.ctx.getBean(RoomBusiness.class);
		business.setRepository(this.repository);
		return business.getRoom(room);
	}
	
	@GetMapping("/all")
	private Mono<ResponseEntity<List<OpenRoom>>> findAllRooms() {
		RoomBusiness business = this.ctx.getBean(RoomBusiness.class);
		business.setRepository(this.repository);
		return business.getAllRooms();
	}
	
	@PutMapping()
	private Mono<ResponseEntity<List<OpenRoom>>> updateRoom(@RequestBody OpenRoom room){
		RoomBusiness business = this.ctx.getBean(RoomBusiness.class);
		business.setRepository(this.repository);
		return business.updateRoom(room);
	}
	
	@DeleteMapping()
	private Mono<ResponseEntity<List<OpenRoom>>> closeRoom(@RequestBody OpenRoom room){
		RoomBusiness business = this.ctx.getBean(RoomBusiness.class);
		business.setRepository(this.repository);
		return business.closeRoom(room);
	}

}
