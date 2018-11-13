package it.watchmefly.hotelroomapp.room.hotelroomagg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import it.watchmefly.hotelroomapp.room.hotelroomagg.config.BusinessAppConfig;
import it.watchmefly.hotelroomapp.room.hotelroomagg.document.HotelRoom;
import it.watchmefly.hotelroomapp.room.hotelroomagg.service.HotelRoomBusiness;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotelroom")
public class HotelRoomController {
	
	@Autowired
    private WebClient.Builder webClientBuilder;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(BusinessAppConfig.class);
	
	@PostMapping
	 Mono<ResponseEntity<HotelRoom>> insertNewRoom(@RequestBody HotelRoom room) {
		HotelRoomBusiness business = ctx.getBean(HotelRoomBusiness.class);
		business.setWebClientBuilder(webClientBuilder);
		return business.openRoom(room);
	}
	
	@DeleteMapping
	public Mono<ResponseEntity<List<HotelRoom>>> closeRoom(@RequestParam("roomNumber") String roomNumber, @RequestParam("hotelId") String hotelId, @RequestParam("reservationId") String reservationId) {
		HotelRoom room = new HotelRoom();
		room.setHotelId(hotelId);
		room.setReservationId(reservationId);
		room.setRoomNumber(new Integer(roomNumber));
		HotelRoomBusiness business = ctx.getBean(HotelRoomBusiness.class);
		business.setWebClientBuilder(webClientBuilder);
		return business.closeRoom(room);
	}

	@GetMapping
	public Mono<ResponseEntity<List<HotelRoom>>> findRoom(ServerHttpRequest req) { // just to use ServerHttpRequest
		HotelRoom room = new HotelRoom();
		room.setHotelId(req.getQueryParams().getFirst("hotelId"));
		room.setReservationId(req.getQueryParams().getFirst("reservationId"));
		room.setRoomNumber(new Integer( req.getQueryParams().getFirst("roomNumber")));
		HotelRoomBusiness business = ctx.getBean(HotelRoomBusiness.class);
		business.setWebClientBuilder(webClientBuilder);
		return business.findRoom(room);
	}
	
}
