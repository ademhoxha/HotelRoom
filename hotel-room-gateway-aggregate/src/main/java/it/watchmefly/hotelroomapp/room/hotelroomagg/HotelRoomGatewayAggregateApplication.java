package it.watchmefly.hotelroomapp.room.hotelroomagg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@RefreshScope // refresh configuration
public class HotelRoomGatewayAggregateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelRoomGatewayAggregateApplication.class, args);
	}
	
	@Bean
	@LoadBalanced // enable ribbon load balance => ribbon will save locally the microservies dns and will update it periodically (round robin algorithm is default)
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
}
