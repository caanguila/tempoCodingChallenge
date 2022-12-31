package com.tempo.challenge.model;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Role {
	
	@Id
	@GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")    
	@Type(type = "uuid-char")
	private UUID id;
	@NotNull
	@Length(min = 1,max = 50,message = "invalid lenght")
	@Column(unique=true,length = 50,nullable = false)
	private String name;
	private String description;
	
	public Role(UUID id, @NotNull @Length(min = 1, max = 50, message = "") String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
		
	public Role() {
		super();
	}



	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
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
