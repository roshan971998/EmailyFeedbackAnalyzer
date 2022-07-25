package com.cts.emaily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.emaily.entity.Role;


/**
 * Repository class for storing, fetching and manipulating role data
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRoleName(String string);
}

