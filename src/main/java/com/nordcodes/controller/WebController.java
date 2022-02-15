package com.nordcodes.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import com.nordcodes.entities.ShortURL;
import com.nordcodes.entities.User;
import com.nordcodes.services.ShortURLService;
import com.nordcodes.services.URLService;
import com.nordcodes.services.UserService;

/**
 * 
 * @author hanza
 *  Класс, реализующий работу веб-контроллера
 *
 */

@Controller
public class WebController {
	@Autowired
	private UserService userService;
	@Autowired
	private ShortURLService shortURLService;
	@Value("${localhostURL}")
	private String localhostURL;
	private static final Logger logger = LogManager.getLogger("WebController");
	
	/**
	 * Метод, добавляет пустой объект shortURL для его заполнения на странице index
	 * @param model - объект, содержащий атрибуты модели
	 * @return страницу index
	 */
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String getIndexPage(Model model) {
		ShortURL shortURL = new ShortURL();
		model.addAttribute("shortURL", shortURL);
		return "index";
	}
	
	/**
	 * Метод получает со страницы index объект shortURL с длинной ссылкой для создания и сохранения в БД короткой ссылки
	 * @param model - объект, содержащий атрибуты модели
	 * @param shortURL - заполненный объект ShortURL
	 * @return страницу индекс с короткой ссылкой
	 */
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.POST)
	public String addShortURLSave(Model model, @ModelAttribute("shortURL") ShortURL shortURL) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		
		if(principal instanceof User) {
		    user = userService.findUserById(((User) principal).getId());
		}

		logger.log(Level.INFO, "Creating new short URL for " + shortURL.getLongURL());
		
		if(URLService.checkURL(shortURL.getLongURL())) {
			shortURL.setShortURL(URLService.genShortURL(shortURL.getLongURL()));
			shortURL.setTransitionCounter(Long.valueOf(0));
			String europeanDatePattern = "dd.MM.yyyy";
			DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
			LocalDate lifetime = LocalDate.now().plusYears(1);
			shortURL.setLifetime(europeanDateFormatter.format(lifetime));
			logger.log(Level.INFO, "Create new short URL '" + shortURL.getShortURL() + "'");
			
			if(user != null) {
				logger.log(Level.INFO, "Add url to URLs of user named " + user.getUsername() + " and update user information in the database");
				shortURL.setUserId(user.getId());
				//user.getShortURLs().clear();
				Collections.addAll(user.getShortURLs(), shortURL);
				user.getShortURLs().forEach(e -> logger.log(Level.INFO, e.getShortURL()));
				userService.updateUser(user);
			} else {
				shortURL.setUserId(Long.valueOf(0));
				shortURLService.saveShortURL(shortURL);
				logger.log(Level.INFO, "Save URL '" + shortURL.getShortURL() + "'");
			}
			model.addAttribute("message", localhostURL + shortURL.getShortURL());
		} else {
			logger.log(Level.ERROR, "You entered the wrong URL");
			model.addAttribute("errorMessage", "You entered the wrong URL");
		}
		
		return "index";
	}
	
	/**
	 * Метод по короткой ссылке производит редирект на оригинальную длинную ссылку 
	 * @param model - объект, содержащий атрибуты модели
	 * @param shortURLString - короткая ссылка
	 * @return страницу index  с ошибкой, если редирект не получился
	 */
	@RequestMapping(value = { "/{shortURLString}" }, method = RequestMethod.GET)
	public String redirection(Model model, @PathVariable(required = false)String shortURLString) {
		ShortURL shortURL = shortURLService.findShortURL(shortURLString);
		if(shortURL == null) {
			shortURL = new ShortURL();
			model.addAttribute("shortURL", shortURL);
			model.addAttribute("errorMessage", "ERROR 404. ShortURL NOT FOUND");
			logger.log(Level.ERROR, "Short URL '" + shortURLString + "' not found");
			return "index";
		}
		
		logger.log(Level.INFO, "Short URL '" + localhostURL + shortURL.getShortURL() + "' found by user request");
		String europeanDatePattern = "dd.MM.yyyy";
		DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
		LocalDate shortURLLifetime = LocalDate.parse(shortURL.getLifetime(), europeanDateFormatter);
		
		if(shortURL != null) {
			if(shortURLLifetime.isAfter(LocalDate.now())) {
				shortURL.setTransitionCounter(shortURL.getTransitionCounter() + 1);
				shortURLService.updateShortURL(shortURL);
				logger.log(Level.INFO, "Redirect to " + shortURL.getLongURL());
				return "redirect:" + shortURL.getLongURL();
			} else {
				shortURL = new ShortURL();
				model.addAttribute("shortURL", shortURL);
				model.addAttribute("errorMessage", "ERROR 401. Short URL lifetime expired");
				logger.log(Level.ERROR, "Short URL lifetime expired");
				
			}
		}
		
		return "index";
	}
	
	/**
	 * Метод добавляет пустой объект user на страницу login для прохождения авторизации
	 * @param model - объект, содержащий атрибуты модели
	 * @return страницу login
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String getLogIn(Model model) {
		User user = new User();
		model.addAttribute("userLogin", user);
		return "login";
	}
	
	/**
	 * Метод добавляет пустой объект user на страницу signup для регистрации новых пользователей
	 * @param model - объект, содержащий атрибуты модели
	 * @return страницу signup
	 */
	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userSignupForm", new User());
		return "signup";
    }

	/**
	 * Метод регистрирует нового пользователя
	 * @param userSignupForm - заполненный объект User со страницы signup
	 * @param bindingResult - объект BindingResult с возможными ошибками валидации
	 * @param model - объект, содержащий атрибуты модели
	 * @return или страницу signup с ошибкой или страницу login для прохождения аутентификации
	 */
	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
	public String addUser(@ModelAttribute("userSignupForm") @Valid User userSignupForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", bindingResult.hasErrors());
			return "signup";
		}
		if (!userSignupForm.getPassword().equals(userSignupForm.getPasswordConfirm())){
			model.addAttribute("errorMessage", "Password mismatch");
			return "signup";
        }
		if (userService.saveUser(userSignupForm) == null){
			model.addAttribute("errorMessage", "A user with the same name already exists");
			return "signup";
		}
		
		return "redirect:/login";
    }
	
	/**
	 * Метод возвращает страницу menu со всеми короткими ссылками пользователя, для администратора возвращаются все ссылки
	 * @param model - объект, содержащий атрибуты модели
	 * @return страницу menu
	 */
	@RequestMapping(value = { "/menu" }, method = RequestMethod.GET)
	public String getMenu(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		List<ShortURL> shortURLs = new ArrayList<>();
		
		if(principal instanceof User) {
		    user = (User) principal;
		    if(user.getRole().equals("ROLE_ADMIN")) {
		    	shortURLs = shortURLService.findAllShortURL();
		    } else {
		    	shortURLs.addAll(user.getShortURLs());
			}
		}
		
		model.addAttribute("shortURLs", shortURLs);
		Date lifetime = new Date();
		model.addAttribute("lifetime", lifetime);
		return "menu";
	}
	
	/**
	 * Метод позволяет обновить время жизни выбранной короткой ссылки
	 * @param model - объект, содержащий атрибуты модели
	 * @param shortURLId - id короткой ссылки, время жизни которой необходимо обновить
	 * @param lifetime - новое время жизни короткой ссылки
	 * @return страницу menu с ссылками с обновленным временем жизни
	 */
	@RequestMapping(value = { "/menu/update/{shortURLId}"}, method = RequestMethod.POST)
	public String menuUpdateShortURL(Model model, @PathVariable Long shortURLId, @ModelAttribute("lifetime") String lifetime) {
		if(shortURLId == null || shortURLId < 0) {
			return "redirect:/menu";
		}
		
		ShortURL shortURL = shortURLService.findByIdShortURL(shortURLId);
		
		if(lifetime == null || lifetime.isBlank() || lifetime.isEmpty()) {
			lifetime = shortURL.getLifetime();
		}
		
			
		String datePatternFromModel = "yyyy-MM-dd";
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePatternFromModel);
		LocalDate ldt = LocalDate.parse(lifetime, dateFormatter);
		
		String europeanDatePattern = "dd.MM.yyyy";
		dateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern);
		
		shortURL.setLifetime(dateFormatter.format(ldt));
		shortURLService.updateShortURL(shortURL);
		logger.log(Level.INFO, "Update URL with ID '" + shortURLId + "' by setting lifetime '" + lifetime + "'");
		return "redirect:/menu";
	}
	
	/**
	 * Метод удаляет выбранную короткую ссылку
	 * @param model - объект, содержащий атрибуты модели
	 * @param shortURLId - id короткой ссылки, которую необходимо удалить
	 * @return страницу menu с обновленным списком ссылок
	 */
	@RequestMapping(value = { "/menu/delete/{shortURLId}"}, method = RequestMethod.POST)
	public String menuDeleteShortURL(Model model, @PathVariable(required = false) Long shortURLId) {
		ShortURL shortURL = shortURLService.findByIdShortURL(shortURLId);
		if(shortURL.getUserId() == 0) {
			shortURLService.deleteShortURL(shortURLId);
		} else {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = null;
						
			if(principal instanceof User) {
			    user = userService.findUserById(((User) principal).getId());
			    user.getShortURLs().forEach(sUrls -> logger.log(Level.INFO, "DELETE: " + sUrls.getShortURL()));
			    user.getShortURLs().removeIf(sUrl -> sUrl.getShortURL().equals(shortURL.getShortURL()));
			    userService.updateUser(user);
			}
		}
		logger.log(Level.INFO, "Delete URL with ID '" + shortURLId + "'");
		return "redirect:/menu";
	}
}