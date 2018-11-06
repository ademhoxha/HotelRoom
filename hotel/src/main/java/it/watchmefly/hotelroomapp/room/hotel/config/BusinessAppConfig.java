package it.watchmefly.hotelroomapp.room.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.watchmefly.hotelroomapp.room.hotel.service.HotelAddressBusiness;
import it.watchmefly.hotelroomapp.room.hotel.service.HotelBusiness;
import it.watchmefly.hotelroomapp.room.hotel.service.impl.HotelAddressBusinessImpl;
import it.watchmefly.hotelroomapp.room.hotel.service.impl.HotelBusinessImpl;


@Configuration
public class BusinessAppConfig {
	
	@Bean
	public HotelBusiness hotelBusiness() {
		return new HotelBusinessImpl();
	}
	
	@Bean
	public HotelAddressBusiness hotelAddressBusiness() {
		return new HotelAddressBusinessImpl();
	}

}
