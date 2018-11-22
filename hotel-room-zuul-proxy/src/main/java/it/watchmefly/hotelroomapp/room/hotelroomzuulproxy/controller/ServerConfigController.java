package it.watchmefly.hotelroomapp.room.hotelroomzuulproxy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("config")
public class ServerConfigController {
	
	@Value("${config.version: Not Versioned}")
	private String version;

	@GetMapping("/version")
	private String getVersion(){ // mono is not needed at all
		return this.version;
	}
}
