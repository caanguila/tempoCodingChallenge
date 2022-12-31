package com.tempo.challenge.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tempo.challenge.model.Role;

public interface IRoleService {
	
	public List<Role> getRoles();
	
    public Optional<Role> getRole(UUID id);
    
    public Role getRole(Optional<Role> role);
    
    public Role saveRole(Role role);
    
    public Optional<Role> findRoleByName(String name);
       
}
