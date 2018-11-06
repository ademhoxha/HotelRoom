package it.watchmefly.hotelroomapp.room.openroom.service;

import it.watchmefly.hotelroomapp.room.openroom.repository.OpenRoomRepository;

public interface RepositoryBusiness {
	
	OpenRoomRepository getRepository();
	void setRepository(OpenRoomRepository repository);
}
