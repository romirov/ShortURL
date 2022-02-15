package com.nordcodes.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.nordcodes.services.UserService;

/**
 * 
 * @author hanza
 * Класс позволяет проходить аутентификацию и авторизацию пользователям, а также контролировать их права 
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private static final Logger logger = LogManager.getLogger("WebSecurityConfig");

	/**
	 * Метод позволяет настроить доступ к различным ресурсам сайта
	 * @param httpSecurity - позволяет настраивать веб-безопасность для определенных HTTP-запросов.
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		logger.log(Level.INFO, "Determine if the page is available to the user.");
		httpSecurity
			.csrf().disable()
			.authorizeRequests()
				//Menu page requires authorization
				.antMatchers("/menu").authenticated()
				//Access allowed to all users
				.anyRequest().permitAll()
			.and()
				//Login Setting
				.formLogin()
				.loginPage("/login")
				//Redirect to homepage after successful login
				.defaultSuccessUrl("/")
				.permitAll()
             .and()
             	.logout()
             	.permitAll()
             	.logoutSuccessUrl("/");
    }

	/**
	 * Метод позволяет проходить аутентификацию зарегистрированным пользователям
	 * @param auth - для настройки данных пользователя в памяти, JDBC или LDAP 
	 * или для добавления пользовательской службы UserDetailsService. 
	 * @throws Exception - ошибки аутентификации
	 */
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	logger.log(Level.INFO, "Determine if the user has been authenticated.");
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}