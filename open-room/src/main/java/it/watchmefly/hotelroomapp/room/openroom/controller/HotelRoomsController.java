package it.watchmefly.hotelroomapp.room.openroom.controller;

import java.util.List;

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
import it.watchmefly.hotelroomapp.room.openroom.service.HotelRoomsBusiness;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotelrooms")
public class HotelRoomsController {
	
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(BusinessAppConfig.class);

	@GetMapping()
	private Mono<ResponseEntity<List<OpenRoom>>> findHotelRooms(@RequestParam("hotelId") String hotelId) {
		/*HotelRoomsBusiness business = this.ctx.getBean(HotelRoomsBusiness.class);
		return business.getHotelRooms(hotelId);*/
		return this.ctx.getBean(HotelRoomsBusiness.class).getHotelRooms(hotelId);
	}

	@DeleteMapping()
	private Mono<ResponseEntity<List<OpenRoom>>> closeHotelRooms(@RequestParam("hotelId") String hotelId){
		return this.ctx.getBean(HotelRoomsBusiness.class).closeHotelsRoom(hotelId);
	}

}
