package com.nordcodes.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nordcodes.entities.User;
import com.nordcodes.entities.Role;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	User findByRoles(Set<Role> role);
}