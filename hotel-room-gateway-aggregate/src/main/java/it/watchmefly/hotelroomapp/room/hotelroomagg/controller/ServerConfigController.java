package it.watchmefly.hotelroomapp.room.hotelroomagg.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/config")
@RefreshScope // to refresh use http://ip:port/actuator/refresh
public class ServerConfigController {

	@GetMapping("/version")
	private Mono<String> getVersion(@Value("${config.version: Not Versioned}") String version){ // mono is not needed at all
		return Mono.just(version);
	}
	
	@GetMapping("/test")
	private Mono<String> getTest(){ // mono is not needed at all
		return Mono.just("Test");
	}
	
}
