package com.tempo.challenge.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tempo.challenge.model.Role;

public interface RoleRepository extends JpaRepository<Role, UUID>{

	Optional<Role> findByName(String name);

}
