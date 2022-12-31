package com.tempo.challenge.model;

import java.util.UUID;

public class Membership {
	
	private UUID userId;
	private UUID teamId;
		
	public Membership(UUID teamId, UUID userId) {
		super();
		this.userId = userId;
		this.teamId = teamId;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public UUID getTeamId() {
		return teamId;
	}
	public void setTeamId(UUID teamId) {
		this.teamId = teamId;
	}
	
	
	

}
