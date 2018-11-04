package it.watchmefly.hotelroomapp.room.openroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.watchmefly.hotelroomapp.room.openroom.document.OpenRoom;
import it.watchmefly.hotelroomapp.room.openroom.repository.OpenRoomRepository;
import it.watchmefly.hotelroomapp.room.openroom.service.OpenRoomBusiness;
import it.watchmefly.hotelroomapp.room.openroom.service.impl.OpenRoomBusinessImpl;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotelrooms")
public class HotelRoomsController {
	
	@Autowired
	private OpenRoomRepository repository;
	

	@GetMapping()
	private Mono<ResponseEntity<List<OpenRoom>>> findHotelRooms(@RequestParam("hotelId") String hotelId) {
		OpenRoomBusiness business = new OpenRoomBusinessImpl(repository);
		return business.getHotelRooms(hotelId);
	}

	
	@DeleteMapping()
	private Mono<ResponseEntity<List<OpenRoom>>> closeHotelRooms(@RequestParam("hotelId") String hotelId){
		OpenRoomBusiness business = new OpenRoomBusinessImpl(repository);
		return business.closeHotelsRoom(hotelId);
	}

}
