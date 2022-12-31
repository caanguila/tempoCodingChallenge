package com.tempo.challenge.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tempo.challenge.model.Assignment;
import com.tempo.challenge.model.Role;

public interface AssignmentRepository  extends JpaRepository<Assignment, UUID> {

	Optional<Assignment> getByRoleAndUserIdAndTeamId(Role searchRole, UUID userId, UUID teamId);

	@Query("from Assignment where role=:role")
	List<Assignment> findMembershipsByRole(@Param("role") Role role);
	
	Optional<Assignment> findByTeamIdAndUserId(UUID teamId,UUID userId);

}
