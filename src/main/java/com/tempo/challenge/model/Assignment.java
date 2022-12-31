package com.tempo.challenge.model;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "teamId" },name = "userTeamUnique") })
public class Assignment {
	
	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")    
	@Type(type = "uuid-char")
	private UUID id;
	@NotNull
	@ManyToOne
	private Role role;
	@Column(columnDefinition = "VARCHAR(36)",nullable = false) 
	@Type(type = "uuid-char")
	private UUID userId;
	@NotNull
	@Column(columnDefinition = "VARCHAR(36)",nullable = false)
	@Type(type = "uuid-char")
	private UUID teamId;
	
	public Assignment() {
		
	}
	
	public Assignment(Role role,UUID userId,UUID teamId) {
		this.role=role;
		this.userId=userId;
		this.teamId=teamId;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
