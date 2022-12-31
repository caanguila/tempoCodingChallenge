package com.tempo.challenge.model;

import java.util.List;
import java.util.UUID;

public class Team {
	
	private UUID id;
	private String name;
	private UUID teamLeadId;
	private List<UUID> teamMemberIds;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public List<UUID> getTeamMemberIds() {
		return teamMemberIds;
	}
	public void setTeamMemberIds(List<UUID> teamMemberIds) {
		this.teamMemberIds = teamMemberIds;
	}
	public UUID getTeamLeadId() {
		return teamLeadId;
	}
	public void setTeamLeadId(UUID teamLeadId) {
		this.teamLeadId = teamLeadId;
	}
	
	
	

}
