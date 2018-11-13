package it.watchmefly.hotelroomapp.room.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.watchmefly.hotelroomapp.room.hotel.config.BusinessAppConfig;
import it.watchmefly.hotelroomapp.room.hotel.document.Hotel;
import it.watchmefly.hotelroomapp.room.hotel.repository.HotelRepository;
import it.watchmefly.hotelroomapp.room.hotel.service.HotelAddressBusiness;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotel/address")
public class HotelAddressController {
	
	@Autowired
	HotelRepository repository;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(BusinessAppConfig.class);

	@PostMapping
	private Mono<ResponseEntity<List<Hotel>>> updateHotelAddress(@RequestBody Mono<Hotel> hotel){
		HotelAddressBusiness business = ctx.getBean(HotelAddressBusiness.class);
		business.setRepository(repository);
		return hotel.flatMap( x -> business.updateHotelAddress(x));
	}
}
