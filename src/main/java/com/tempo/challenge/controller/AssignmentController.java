package com.tempo.challenge.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tempo.challenge.dto.AssignmentRequest;
import com.tempo.challenge.integration.TempoRestClient;
import com.tempo.challenge.model.Assignment;
import com.tempo.challenge.model.Membership;
import com.tempo.challenge.model.Role;
import com.tempo.challenge.model.Team;
import com.tempo.challenge.model.User;
import com.tempo.challenge.service.IAssignmentService;
import com.tempo.challenge.service.IRoleService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/roles")
public class AssignmentController {

	@Autowired
	private IAssignmentService assignmentService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private TempoRestClient tempoRestClient;

	/**
	 * Gets all the assignments
	 *
	 * @return list containing all assignments
	 */
	@Operation(summary = "Get all the assignments")
	@GetMapping("/assignments")
	public ResponseEntity<List<Assignment>> getAssignments() {
		return ResponseEntity.ok(assignmentService.getAssignments());
	}

	/**
	 * Save assignment
	 *
	 * @param AssignmentRequest
	 * @return In case of a role or team or user are not found, it should return a a HTTP 400-BAD REQUEST
	 *         In case of a role or team or user are found, it should return a HTTP 200-OK and new assignment
	 *         
	 */
	@Operation(summary = "Save assignment")
	@PostMapping("/assignments")
	public ResponseEntity<Assignment> saveAssignment(@Validated @RequestBody AssignmentRequest assignmentRequest) {

		Optional<Role> searchRole;
		Assignment searchAssig;
		User searchUser = tempoRestClient.findUser(assignmentRequest.getUserId());
		Team searchTeam = tempoRestClient.findTeam(assignmentRequest.getTeamId());
		Assignment newAssignment;
		
		if(assignmentRequest.getRoleId()==null) {
			searchRole = roleService.findRoleByName("Developer");
		}else {
			searchRole =  roleService.getRole(assignmentRequest.getRoleId());
		}

		searchAssig = assignmentService.findAssigment(searchRole, assignmentRequest.getUserId(),
				assignmentRequest.getTeamId());

		if (assignmentService.validateData(searchRole,searchTeam, searchUser) && searchAssig == null) {
			newAssignment = new Assignment(searchRole.get(), assignmentRequest.getUserId(),
					assignmentRequest.getTeamId());
			return ResponseEntity.ok(assignmentService.save(newAssignment));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	/**
	 * Find memberships by a role
	 *
	 * @param UUID
	 * @return In case of a role is not found, it should return a HTTP 404-NOT-FOUND
	 *         In case of a role is found, it should return a HTTP 200-OK and found assignment
	 *         
	 */
	@Operation(summary = "Find memberships by a role")
	@GetMapping("/findMemberships/{roleId}")
	public ResponseEntity<List<Membership>> findMembershipsByRole(@Validated @PathVariable("roleId") @NotEmpty UUID roleId) {
		Optional<Role> role = roleService.getRole(roleId);
		return ResponseEntity.ok(assignmentService.findMembershipsByRole(role));

	}

	/**
	 * Find role by a membership
	 *
	 * @param UUID
	 * @param UUID
	 * @return In case of a assignment is not found, it should return a HTTP 404-NOT-FOUND
	 *         In case of a assignment is found, it should return a HTTP 200-OK and found membership
	 *         
	 */
	@Operation(summary = "Find role by a membership")
	@GetMapping("/findRole")
	public ResponseEntity<Role> findRoleByMembership(@Validated @RequestParam("teamId")@NotEmpty UUID teamId,
			@Validated  @RequestParam("userId") @NotEmpty UUID userId) {
		return ResponseEntity.ok(assignmentService.findByTeamIdAndUserId(teamId, userId).getRole());

	}
}
