package it.watchmefly.hotelroomapp.room.openroom.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("config")
public class ServerConfigController {
	
	@Value("${config.version: Not Versioned}")
	private String version;

	@GetMapping("/version")
	private Mono<String> getVersion(){ // mono is not needed at all
		return Mono.just(this.version);
	}
	
}
