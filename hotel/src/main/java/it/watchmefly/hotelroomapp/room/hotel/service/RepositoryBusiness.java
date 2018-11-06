package it.watchmefly.hotelroomapp.room.hotel.service;

import it.watchmefly.hotelroomapp.room.hotel.repository.HotelRepository;

public interface RepositoryBusiness {
	
	HotelRepository getRepository();
	void setRepository(HotelRepository repository);
}
