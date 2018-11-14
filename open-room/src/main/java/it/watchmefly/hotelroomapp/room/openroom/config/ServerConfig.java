package it.watchmefly.hotelroomapp.room.openroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
	
	@Bean
	ServerConfig serverConfig() {
		return new ServerConfig();
	}

}
