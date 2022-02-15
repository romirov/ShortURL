package com.nordcodes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import com.nordcodes.entities.ShortURL;
import com.nordcodes.entities.User;
import com.nordcodes.services.UserService;

@SpringBootTest
@ComponentScan
public class UserServiceTests {
	@Autowired
	private UserService userService;
	@Autowired
	private static User user;
	@Autowired
	private static ShortURL shortURL;
	
	@BeforeAll
	private static void init() {
		user = new User();
		user.setId(Long.valueOf(999));
		user.setUsername("test");
		shortURL = new ShortURL(Long.valueOf(999), "777", "88888", "15.03.2022", Long.valueOf("0"));
		Set<ShortURL> shortURLs = new HashSet<>();
		user.setShortURLs(shortURLs);
		user.setPassword("password");
		user.setRoles("ROLE_USER");
	}
	
	@Test
	public void saveDeleteUserTests() {
		Assertions.assertThat(userService).isNotNull();
		Assertions.assertThat(user).isNotNull();
		User userFromUserDB = userService.saveUser(user);
		Assertions.assertThat(userFromUserDB.getUsername()).isEqualTo(user.getUsername());
		Assertions.assertThat(userService.deleteUser(userFromUserDB.getId())).isTrue();
	}
	
	@Test
	public void updateUserTests() {
		Assertions.assertThat(userService).isNotNull();
		Assertions.assertThat(user).isNotNull();
		User userFromUserDB = userService.saveUser(user);
		Assertions.assertThat(userFromUserDB.getUsername()).isEqualTo(user.getUsername());
		userFromUserDB.setUsername("TEEEST");
		User userFromUserDB1 = userService.updateUser(userFromUserDB);
		Assertions.assertThat(userFromUserDB1.getUsername()).isEqualTo("TEEEST");
		Assertions.assertThat(userService.deleteUser(userFromUserDB1.getId())).isTrue();
	}
	
	@Test
	public void allUsersTests() {
		Assertions.assertThat(userService).isNotNull();
		Assertions.assertThat(user).isNotNull();
		User userFromUserDB = userService.saveUser(user);
		List<User> users = userService.allUsers();
		Assertions.assertThat(users).extracting(User::getUsername).contains(user.getUsername());
		Assertions.assertThat(userService.deleteUser(userFromUserDB.getId())).isTrue();
	}
	
	@Test
	public void findUserByIdTests() {
		Assertions.assertThat(userService).isNotNull();
		Assertions.assertThat(user).isNotNull();
		User userFromUserDB = userService.saveUser(user);
		Assertions.assertThat(userService.findUserById(userFromUserDB.getId()).getUsername()).contains(user.getUsername());
		Assertions.assertThat(userService.deleteUser(userFromUserDB.getId())).isTrue();
	}
	
	@Test
	public void loadUserByUsernameTests() {
		Assertions.assertThat(userService).isNotNull();
		Assertions.assertThat(user).isNotNull();
		User userFromUserDB = userService.saveUser(user);
		User findUserFromUserDB = (User)userService.loadUserByUsername("test");
		Assertions.assertThat(findUserFromUserDB.getId()).isEqualTo(userFromUserDB.getId());
		Assertions.assertThat(userService.deleteUser(userFromUserDB.getId())).isTrue();
	}
}
