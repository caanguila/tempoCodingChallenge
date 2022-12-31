package com.tempo.challenge.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class AssignmentRequest {
	
	@NotNull
	private UUID roleId;
	@NotNull
	private UUID teamId;
	@NotNull
	private UUID userId;
	
	public AssignmentRequest() {
		super();
	}
	public AssignmentRequest(UUID roleId, UUID teamId, UUID userId) {
		super();
		this.roleId = roleId;
		this.teamId = teamId;
		this.userId = userId;
	}
	public UUID getRoleId() {
		return roleId;
	}
	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}
	public UUID getTeamId() {
		return teamId;
	}
	public void setTeamId(UUID teamId) {
		this.teamId = teamId;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}
