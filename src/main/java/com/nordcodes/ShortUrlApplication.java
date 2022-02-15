package com.nordcodes;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * 
 * @author hanza
 * Проект реализует работу сайта, создающего короткие ссылки из длинных (по мотивам: goo.gl, bitly, Ow.ly, и т.п.).
 * Он позволяет регистрироваться новым пользователям. 
 * Пользователи могут видеть статистику по своим ссылкам(количество переходов) и управлять ими(изменять время жизни и удалять)
 *  
 */

@SpringBootApplication
public class ShortUrlApplication {
	
	/**
	 * Конструктор позволяет использовать в проекте библиотеку JSP Tag'ов 
	 * @param freeMarkerConfigurer - конфигуратор регистрирующий загрузчик шаблонов для этого пакета,
	 *  позволяя ссылаться на библиотеку макросов «spring.ftl».
	 */
	public ShortUrlApplication(FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(Collections.singletonList("/META-INF/security.tld"));
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ShortUrlApplication.class, args);
	}
	
}
