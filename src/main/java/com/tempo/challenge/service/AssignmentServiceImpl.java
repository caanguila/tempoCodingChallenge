package com.tempo.challenge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tempo.challenge.exception.BadRequestException;
import com.tempo.challenge.exception.ResourceNotFoundException;
import com.tempo.challenge.model.Assignment;
import com.tempo.challenge.model.Membership;
import com.tempo.challenge.model.Role;
import com.tempo.challenge.model.Team;
import com.tempo.challenge.model.User;
import com.tempo.challenge.repo.AssignmentRepository;

@Service
public class AssignmentServiceImpl implements IAssignmentService {

	@Autowired
	private AssignmentRepository assignmentRepository;

	@Override
	public List<Assignment> getAssignments() {
		return assignmentRepository.findAll();
	}

	@Override
	public Assignment findAssigment(Optional<Role> searchRole, UUID userId, UUID teamId) {

		if (searchRole.isPresent()) {
			Optional<Assignment> assignment = assignmentRepository.getByRoleAndUserIdAndTeamId(searchRole.get(), userId,
					teamId);

			if (assignment.isPresent()) {
				return assignment.get();
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	@Override
	public List<Membership> findMembershipsByRole(Optional<Role> role) {
		if(role.isPresent()) {
			List<Assignment> lAssignment = assignmentRepository.findMembershipsByRole(role.get());
			List<Membership> lMembership = new ArrayList<>();

			if (!lAssignment.isEmpty()) {
				lAssignment.stream().forEach(x -> lMembership.add(new Membership(x.getTeamId(), x.getUserId())));
				return lMembership;
			} else {
				throw new ResourceNotFoundException("Assignment", "Role", role.get().getId());
			}	
		}else {
			throw new ResourceNotFoundException("Assignment", "Role", role.get().getId());
		}		
	}

	@Override
	public Assignment findByTeamIdAndUserId(UUID teamId, UUID userId) {
		Optional<Assignment> assignment = assignmentRepository.findByTeamIdAndUserId(teamId, userId);

		if (assignment.isPresent()) {
			return assignment.get();
		} else {
			throw new ResourceNotFoundException("Assignment", "Team Id, User Id", teamId + " " + userId);
		}
	}

	@Override
	public Assignment save(Assignment assignment) {
		return assignmentRepository.save(assignment);
	}

	@Override
	public boolean validateData(Optional<Role> role, Team team, User user) {

		if (role.isEmpty() || team == null || user == null) {
			throw new BadRequestException("Assignment could no be created");
		} else {
			return true;
		}

	}

}
