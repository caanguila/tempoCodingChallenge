package com.tempo.challenge.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class RoleRequest {
	
	@NotNull(message = "it should not be null")
	@Length(min = 1,max = 50,message = "invalid lenght")
	private String name;
	private String description;
	
	public RoleRequest() {
		super();
	}
	public RoleRequest(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
