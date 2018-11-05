package it.watchmefly.hotelroomapp.room.openroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.watchmefly.hotelroomapp.room.openroom.service.HotelRoomsBusiness;
import it.watchmefly.hotelroomapp.room.openroom.service.ReservationRoomsBusiness;
import it.watchmefly.hotelroomapp.room.openroom.service.RoomBusiness;
import it.watchmefly.hotelroomapp.room.openroom.service.impl.HotelRoomsBusinessImpl;
import it.watchmefly.hotelroomapp.room.openroom.service.impl.ReservationRoomsBusinessImpl;
import it.watchmefly.hotelroomapp.room.openroom.service.impl.RoomBusinessImpl;

@Configuration
public class BusinessAppConfig {
	
	@Bean
	public RoomBusiness roomBusiness() {
		return new RoomBusinessImpl();
	}
	
	@Bean
	public HotelRoomsBusiness rotelRoomsBusiness() {
		return new HotelRoomsBusinessImpl();
	}
	
	@Bean
	public ReservationRoomsBusiness reservationRoomsBusiness() {
		return new ReservationRoomsBusinessImpl();
	}

}
