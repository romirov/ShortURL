package com.nordcodes;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.nordcodes.services.URLService;

@SpringBootTest
public class URLServiceTests {

	@Test
	public void checkURLTest() {
		String notUrlString = "ftp://test.com";
		assertThat(URLService.checkURL(notUrlString)).isFalse();
		
		notUrlString = "httpps://test.com";
		assertThat(URLService.checkURL(notUrlString)).isFalse();
		
		String urlString = "http://test.com";
		assertThat(URLService.checkURL(urlString)).isTrue();
		
		urlString = "https://test.com";
		assertThat(URLService.checkURL(urlString)).isTrue();
		
	}
	
	@Test
	public void genShortURL() {
		assertThat(URLService.genShortURL("https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/view.html")).isNotNull();
		
		assertThat(URLService.genShortURL("https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/view.html")).isNotEmpty();
		
		String shotURL1 = URLService.genShortURL("https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/view.html");
		
		String shotURL2 = URLService.genShortURL("https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/view.html");
		
		assertThat(shotURL1).isNotEqualTo(shotURL2);
	}
}
