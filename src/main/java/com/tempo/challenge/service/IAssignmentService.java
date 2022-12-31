package com.tempo.challenge.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.tempo.challenge.model.Assignment;
import com.tempo.challenge.model.Membership;
import com.tempo.challenge.model.Role;
import com.tempo.challenge.model.Team;
import com.tempo.challenge.model.User;

public interface IAssignmentService {
	
	public List<Assignment> getAssignments();
	
	public Assignment save(Assignment assignment);
	
	public Assignment findAssigment(Optional<Role> searchRole, UUID userId, UUID teamId);
	
	public List<Membership> findMembershipsByRole(Optional<Role> role);
	
	public Assignment findByTeamIdAndUserId(UUID teamId,UUID userId);
	
	public boolean validateData(Optional<Role> role,Team team,User user);

}
