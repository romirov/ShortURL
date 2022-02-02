package com.nordcodes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nordcodes.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	User findByRole(String role);
}