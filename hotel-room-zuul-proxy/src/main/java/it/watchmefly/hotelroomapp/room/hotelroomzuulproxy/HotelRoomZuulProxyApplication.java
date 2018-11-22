package it.watchmefly.hotelroomapp.room.hotelroomzuulproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy // enable zuul proxy
@EnableDiscoveryClient // needed to discovery the configuration service (see bootstrap.properties)
@RefreshScope // refresh configuration
public class HotelRoomZuulProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelRoomZuulProxyApplication.class, args);
	}
}
