package com.nordcodes;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@SpringBootApplication
public class ShortUrlApplication {

	public ShortUrlApplication(FreeMarkerConfigurer freeMarkerConfigurer) {
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(Collections.singletonList("/META-INF/security.tld"));
    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(ShortUrlApplication.class, args);
	}
	
}
