package com.nordcodes.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 
 * @author hanza
 * Класс, реализует распознование и определяет местоположение страниц ftl
 * 
 */

@EnableWebMvc
@Configuration
public class ViewResolverConfig {
	private static final Logger logger = LogManager.getLogger("ViewResolverConfig");
	
	/**
	 * Метод создает FreeMarkerViewResolver для распознавания ftl страниц 
	 * @return объект FreeMarkerViewResolver
	 */
	@Bean(name = "freeMarkerViewResolver")
	public FreeMarkerViewResolver getViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setCache(true);
		viewResolver.setPrefix("");
		viewResolver.setSuffix(".ftl");
		logger.log(Level.INFO, "Create FreeMarkerViewResolver view resolver for '.ftl' pages.");
		return viewResolver;
	}

	/**
	 * Метод создает FreeMarkerConfigurer для определения местоположения ftl страниц 
	 * @return объект FreeMarkerConfigurer
	 */
	@Bean(name = "freemarkerConfig")
	public FreeMarkerConfigurer getFreemarkerConfig() {
		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		config.setTemplateLoaderPath("classpath:/templates");
		logger.log(Level.INFO, "Defined the path of the '.ftl' pages.");
		return config;
	}
}
