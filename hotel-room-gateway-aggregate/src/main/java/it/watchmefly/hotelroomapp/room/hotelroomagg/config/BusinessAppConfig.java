package it.watchmefly.hotelroomapp.room.hotelroomagg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.watchmefly.hotelroomapp.room.hotelroomagg.service.HotelRoomBusiness;
import it.watchmefly.hotelroomapp.room.hotelroomagg.service.impl.HotelRoomBusinessImpl;

@Configuration
public class BusinessAppConfig {
	
	@Bean
	HotelRoomBusiness hotelRoomBusiness() {
		return new HotelRoomBusinessImpl();
	}
	

}
