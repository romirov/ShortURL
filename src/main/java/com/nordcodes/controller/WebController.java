package com.nordcodes.controller;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nordcodes.entities.Role;
import com.nordcodes.entities.ShortURL;
import com.nordcodes.entities.User;
import com.nordcodes.services.ShortURLService;
import com.nordcodes.services.URLService;
import com.nordcodes.services.UserService;

@Controller
public class WebController {
	@Autowired
	private UserService userService;
	@Autowired
	private ShortURLService shortURLService;
	@Value("${localhostURL}")
	private String localhostURL;
	private static final Logger logger = LogManager.getLogger("WebController");
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String getIndexPage(Model model) {
		ShortURL shortURL = new ShortURL();
		model.addAttribute("shortURL", shortURL);
		return "index";
	}
	
	@RequestMapping(value = { "/{shortURLString}" }, method = RequestMethod.GET)
	public String redirection(Model model, @PathVariable(required = false)String shortURLString) {
		ShortURL shortURL = shortURLService.findShortURL(shortURLString);
		if(shortURL != null && shortURL.getLifetime().isBefore(LocalDateTime.now())) {
			return "redirect:" + shortURL.getLongURL();
		}
		
		model.addAttribute("errorMessage", "ERROR 404. NOT FOUND");
		return "NOT FOUND";
	}
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.POST)
	public String addShortURLSave(Model model, @ModelAttribute("shortURL") ShortURL shortURL) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		
		if(principal instanceof User) {
		    user = (User) principal;
		}

		logger.log(Level.INFO, "Creating new short URL for " + shortURL.getLongURL());
		
		if(URLService.checkURL(shortURL.getLongURL())) {
			shortURL.setShortURL(localhostURL + URLService.genShortURL(shortURL.getLongURL()));
			
			if(user != null) {
				user.getShortURLs().add(shortURL);
				userService.updateUser(user);
				shortURL.setUserId(user.getId());
			} else {
				Role role = new Role();
				role.setName("ROLE_ADMIN");
				user = userService.findUserByRole(new Set<Role>().add(role));
				shortURL.setUserId(user.getId());
			}
			
			shortURL.setTransitionCounter(Long.valueOf(0));
			shortURL.setLifetime(LocalDateTime.now().plusYears(1));
			
			logger.log(Level.INFO, "Create new short URL '" + shortURL.toString() + "'");
			logger.log(Level.INFO, "Updating user's URLs");
			
			user.getShortURLs().add(shortURL);
			userService.updateUser(user);
			
			model.addAttribute("message", shortURL.getShortURL());
		} else {
			logger.log(Level.ERROR, "You entered the wrong URL");
			model.addAttribute("errorMessage", "You entered the wrong URL");
		}
		
		return "index";
	}
	
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String getLogIn(Model model) {
		User user = new User();
		model.addAttribute("userLogin", user);
		return "login";
	}
	
	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userSignupForm", new User());
		return "signup";
    }

	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
	public String addUser(@ModelAttribute("userSignupForm") @Valid User userSignupForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "signup";
		}
		if (!userSignupForm.getPassword().equals(userSignupForm.getPasswordConfirm())){
			model.addAttribute("passwordError", "Пароли не совпадают");
			return "signup";
        }
		if (!userService.saveUser(userSignupForm)){
			model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
			return "signup";
		}

		return "login";
    }
	
	@RequestMapping(value = { "/menu" }, method = RequestMethod.GET)
	public String getMenu(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		
		if(principal instanceof User) {
		    user = (User) principal;
		}
		
		model.addAttribute("user", user);
		return "menu";
	}
	
	@RequestMapping(value = { "/menu"}, method = RequestMethod.POST)
	public String searchShortURL(Model model, @ModelAttribute("updateShortURL") ShortURL shortURL) {
		return "menu";
	}
}
