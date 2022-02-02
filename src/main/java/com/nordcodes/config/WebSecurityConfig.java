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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private static final Logger logger = LogManager.getLogger("WebSecurityConfig");

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		logger.log(Level.INFO, "Determine if the page is available to the user.");
		httpSecurity
			.csrf().disable()
			.authorizeRequests()
				//Доступ разрешен всем пользователей
				.antMatchers("/", "/index", "/login", "/signup").permitAll()
				//Все остальные страницы требуют аутентификации
				.anyRequest().authenticated()
			.and()
				//Настройка для входа в систему
				.formLogin()
				.loginPage("/login")
				//Перенарпавление на главную страницу после успешного входа
				.defaultSuccessUrl("/")
				.permitAll()
             .and()
             	.logout()
             	.permitAll()
             	.logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	logger.log(Level.INFO, "Determine if the user has been authenticated.");
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}