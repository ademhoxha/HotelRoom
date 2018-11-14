package it.watchmefly.hotelroomapp.room.hotel.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.watchmefly.hotelroomapp.room.hotel.config.ServerBean;
import it.watchmefly.hotelroomapp.room.hotel.config.ServerConfig;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("config")
public class ServerConfigController {
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(ServerConfig.class);

	@GetMapping("/version")
	private Mono<String> getVersion(){ // mono is not needed at all
		return Mono.just( ((ServerBean) ctx.getBean(ServerBean.class)).getServerVersion());
	}
	
}
