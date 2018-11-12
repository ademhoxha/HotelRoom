package it.watchmefly.hotelroomapp.room.hotelroomagg.service;

import org.springframework.web.reactive.function.client.WebClient;

public interface WebClientBusiness {

	WebClient.Builder getWebClientBuilder();

	void setWebClientBuilder(WebClient.Builder webClientBuilder);
}
