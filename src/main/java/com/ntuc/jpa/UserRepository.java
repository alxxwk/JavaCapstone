package com.ntuc.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {

	@Query("SELECT u FROM Users u WHERE u.username = :username")
	public Users getUserByUsername(@Param("username") String username);
}
