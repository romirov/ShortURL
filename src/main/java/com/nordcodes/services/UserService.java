package com.nordcodes.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.nordcodes.entities.Role;
import com.nordcodes.entities.User;
import com.nordcodes.repositories.UserRepository;

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
	
	public User findUserByRole(Set<Role> roles) {
		logger.log(Level.INFO, "Looking for a user by role '" + roles.toString() + "'.");
		User user = userRepository.findByRoles(roles);
		if (user == null) {
			logger.log(Level.ERROR, "User by role '" + roles.toString() + "' not found.");
			return null;
		}

		logger.log(Level.INFO, "User by role '" + roles.toString() + "' found -- " + user.getUsername());
		return user;
	}

	public List<User> allUsers() {
		logger.log(Level.INFO, "Looking for all users.");
		return userRepository.findAll();
	}

	public boolean saveUser(User user) {
		logger.log(Level.INFO, "Storing user data in the database..");
		User userFromDB = userRepository.findByUsername(user.getUsername());

		if (userFromDB != null) {
			logger.log(Level.ERROR, "The same user is worn in the database.");
			return false;
		}

		user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
		user.setPassword(encoder().encode(user.getPassword()));
		userRepository.save(user);
		logger.log(Level.INFO, "User '" + user.getUsername() + "' data saved.");
		return true;
	}
	
	public boolean updateUser(User user) {
		if(userRepository.save(user) != null) {
			logger.log(Level.INFO, "User '" + user.getUsername() + "' data updated.");
			return true;
		}
		
		logger.log(Level.ERROR, "User '" + user.getUsername() + "' data not updated.");
		return false;
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