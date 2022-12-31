package com.tempo.challenge.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tempo.challenge.exception.BadRequestException;
import com.tempo.challenge.exception.ResourceNotFoundException;
import com.tempo.challenge.model.Role;
import com.tempo.challenge.repo.RoleRepository;

@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Optional<Role> getRole(UUID id) {
		return roleRepository.findById(id);	
	}

	@Override
	public Role saveRole(Role role) {
		
		Optional<Role> findRole= findRoleByName(role.getName());
		
		if(findRole.isEmpty()) {
			return roleRepository.save(role);
		}else {
			throw new BadRequestException("Role could no be created, name already exists");
		}
		
	}

	@Override
	public Optional<Role> findRoleByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Role getRole(Optional<Role> role) {
		if(role.isPresent()) {
			return role.get();
		}else {
			throw new ResourceNotFoundException("Role", "id", role);
		}
	}



}
