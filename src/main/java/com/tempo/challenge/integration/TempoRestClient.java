package com.tempo.challenge.integration;

import java.util.UUID;

import com.tempo.challenge.model.Team;
import com.tempo.challenge.model.User;

public interface TempoRestClient {
	
	public User findUser(UUID userId);
	
	public Team findTeam(UUID teamId);

}
