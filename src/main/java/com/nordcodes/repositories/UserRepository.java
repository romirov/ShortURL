package com.nordcodes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nordcodes.entities.User;

/**
 * 
 * @author hanza
 * Интерфейс взаимодействия с базой данных для работы с пользователями
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	User findByRole(String role);
}