package com.tempo.challenge.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tempo.challenge.dto.RoleRequest;
import com.tempo.challenge.model.Role;
import com.tempo.challenge.service.IRoleService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private IRoleService roleService;

	/**
	 * Gets all the roles
	 *
	 * @return list containing all roles
	 */
	@Operation(summary = "Get all the roles")
	@GetMapping
	public ResponseEntity<List<Role>> getRoles() {
		return ResponseEntity.ok(roleService.getRoles());
	}

	/**
	 * Get a role by Id
	 *
	 * @param UUID
	 * @return In case of a role is found, it should return a HTTP 200-OK and role
	 *         In case of a role is not found, it should return a HTTP 404-NOT-FOUND
	 */
	@Operation(summary = "Get a role by Id")
	@GetMapping("/{id}")
	public ResponseEntity<Role> getRole(@Validated @PathVariable("id") @NotEmpty UUID id) {
		Optional<Role> role = roleService.getRole(id);
		return ResponseEntity.ok(roleService.getRole(role));

	}

	/**
	 * Save a role
	 *
	 * @param RoleRequest
	 * @return In case of a role is found, it should return a a HTTP 400-BAD REQUEST
	 *         In case of a role is not found, it should return a HTTP 200-OK and new role
	 */
	@Operation(summary = "Save a role")
	@PostMapping
	public ResponseEntity<Role> saveRole(@Validated @RequestBody  RoleRequest role) {
		Role roleSave = new Role();
		roleSave.setName(role.getName());
		roleSave.setDescription(role.getDescription());
		return ResponseEntity.ok(roleService.saveRole(roleSave));
	}

}
