package com.nordcodes.services;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nordcodes.entities.User;
import com.nordcodes.repositories.UserRepository;

/**
 * 
 * @author hanza
 * Класс, реализующий интерфейс взаимодействия с базой данных для работы с пользователями
 * 
 */
@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	private static final Logger logger = LogManager.getLogger("UserService");
    
	@Bean
	private PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.log(Level.INFO, "Looking for a user by name '" + username + "'..");
		User user = userRepository.findByUsername(username);

		if (user == null) {
			logger.log(Level.ERROR, "User by name '" + username + "' not found.");
			return null;
		}

		logger.log(Level.INFO, "User by name '" + username + "' found");
		return user;
    }

	public User findUserById(Long userId) {
		logger.log(Level.INFO, "Looking for a user by ID '" + userId + "'.");
		Optional<User> userFromDb = userRepository.findById(userId);
		return userFromDb.orElse(new User());
	}

	public List<User> allUsers() {
		logger.log(Level.INFO, "Looking for all users.");
		return userRepository.findAll();
	}

	public User saveUser(User user) {
		logger.log(Level.INFO, "Storing user data in the database..");
		User userFromDB = userRepository.findByUsername(user.getUsername());

		if (userFromDB != null) {
			logger.log(Level.ERROR, "The same user is worn in the database.");
			return null;
		}

		user.setRoles("ROLE_USER");
		user.setPassword(encoder().encode(user.getPassword()));
		user = userRepository.save(user);
		logger.log(Level.INFO, "User '" + user.getUsername() + "' data saved.");
		return user;
	}
	
	public User updateUser(User user) {
		User userFromDBUser = userRepository.save(user);
		if(userFromDBUser != null) {
			logger.log(Level.INFO, "User '" + user.getUsername() + "' data updated.");
			return userFromDBUser;
		}
		
		logger.log(Level.ERROR, "User '" + user.getUsername() + "' data not updated.");
		return null;
	}

	public boolean deleteUser(Long userId) {
		logger.log(Level.INFO, "Removing a user from an ID '" + userId + "'..");
		
		if (userRepository.findById(userId).isPresent()) {
			logger.log(Level.INFO, "User with ID '" + userId + "' deleted.");
			userRepository.deleteById(userId);
			return true;
		}
		
		logger.log(Level.ERROR, "User with ID '" + userId + "' not deleted.");
		return false;
	}
}