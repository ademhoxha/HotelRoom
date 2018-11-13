package it.watchmefly.hotelroomapp.room.hotel.controller;

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

import it.watchmefly.hotelroomapp.room.hotel.config.BusinessAppConfig;
import it.watchmefly.hotelroomapp.room.hotel.document.Hotel;
import it.watchmefly.hotelroomapp.room.hotel.repository.HotelRepository;
import it.watchmefly.hotelroomapp.room.hotel.service.HotelBusiness;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotel")
public class HotelController {
	
	@Autowired
	private HotelRepository repository;
	ApplicationContext ctx = new AnnotationConfigApplicationContext(BusinessAppConfig.class);
	
	@PostMapping
	private Mono<ResponseEntity<Hotel>> insertHotel(@RequestBody Mono<Hotel> hotel){
		HotelBusiness business = ctx.getBean(HotelBusiness.class);
		business.setRepository(repository);
		return hotel.flatMap( x -> business.insertHotel(x));
	}
	
	@GetMapping
	private Mono<ResponseEntity<List<Hotel>>> getHotel(@RequestParam("hotelId") String hoteld){
		HotelBusiness business = ctx.getBean(HotelBusiness.class);
		business.setRepository(repository);
		return business.getHotel(hoteld);
	}
	
	@PutMapping
	private Mono<ResponseEntity<List<Hotel>>> updateHotel(@RequestBody Mono<Hotel> hotel){
		HotelBusiness business = ctx.getBean(HotelBusiness.class);
		business.setRepository(repository);
		return hotel.flatMap( x -> business.updateHotel(x));
	}
	
	@DeleteMapping
	private Mono<ResponseEntity<List<Hotel>>> closeHotel(@RequestParam("hotelId") String hoteld){
		HotelBusiness business = ctx.getBean(HotelBusiness.class);
		business.setRepository(repository);
		return business.closeHotel(hoteld);
	}
	
	@GetMapping("/all")
	private Mono<ResponseEntity<List<Hotel>>> getAllHotels(){
		HotelBusiness business = ctx.getBean(HotelBusiness.class);
		business.setRepository(repository);
		return business.getAllHotels();
	}
	
	@DeleteMapping("/all")
	private Mono<ResponseEntity<List<Hotel>>> closeAllHotel(){
		HotelBusiness business = ctx.getBean(HotelBusiness.class);
		business.setRepository(repository);
		return business.closeAllHotel();
	}

}
