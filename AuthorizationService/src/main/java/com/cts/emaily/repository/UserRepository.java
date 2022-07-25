package com.cts.emaily.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.emaily.entity.User;

/**
 * Repository class for storing, fetching and manipulating user data
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByUserName(String username);

	public Optional<User> findById(Integer id);

	@Modifying
	@Transactional
	@Query("update User set credits=:amount where userName=:username")
	Integer UpdateUserCredit(@Param("username") String userName, Integer amount);

}
