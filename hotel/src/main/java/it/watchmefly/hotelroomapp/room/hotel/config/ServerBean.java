package it.watchmefly.hotelroomapp.room.hotel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerBean {
		
	@Value("${config.version: Not Versioned}")
	private String version;
	
	public String getServerVersion() {
		return this.version;
	}

}
