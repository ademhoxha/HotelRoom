package it.watchmefly.hotelroomapp.room.openroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.watchmefly.hotelroomapp.room.openroom.config.BusinessAppConfig;
import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import it.watchmefly.hotelroomapp.room.openroom.repository.OpenRoomRepository;
import it.watchmefly.hotelroomapp.room.openroom.service.ReservationRoomsBusiness;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reservationrooms")
public class ReservationRoomsController {
	
	@Autowired
	private OpenRoomRepository repository;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(BusinessAppConfig.class);
	
	@GetMapping()
	private Mono<ResponseEntity<List<OpenRoom>>> findHotelRooms(@RequestParam("hotelId") String hotelId, @RequestParam("reservationId") String reservationId) {
		ReservationRoomsBusiness business = this.ctx.getBean(ReservationRoomsBusiness.class);
		business.setRepository(this.repository);
		return business.getReservationRooms(hotelId, reservationId);
	}

	@DeleteMapping()
	private Mono<ResponseEntity<List<OpenRoom>>> closeHotelRooms(@RequestParam("hotelId") String hotelId, @RequestParam("reservationId") String reservationId){
		ReservationRoomsBusiness business = this.ctx.getBean(ReservationRoomsBusiness.class);
		business.setRepository(this.repository);
		return business.closeReservationRooms(hotelId, reservationId);
	}

}
