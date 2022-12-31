package com.tempo.challenge.integration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tempo.challenge.model.Team;
import com.tempo.challenge.model.User;

@Component
public class TempoRestClientImpl implements TempoRestClient {
	
	@Value("${tempo.rest.url}")
	private String TEMPO_REST_URL;

	@Override
	public User findUser(UUID userId) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(TEMPO_REST_URL +"users/"+ userId,
				User.class);
	}

	@Override
	public Team findTeam(UUID teamId) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(TEMPO_REST_URL +"teams/"+ teamId,
				Team.class);
	}

}
