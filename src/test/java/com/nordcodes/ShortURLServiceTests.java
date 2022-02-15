package com.nordcodes;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import com.nordcodes.entities.ShortURL;
import com.nordcodes.services.ShortURLService;

@SpringBootTest
@ComponentScan
public class ShortURLServiceTests {
	@Autowired
	ShortURLService shortURLService;
	
	@Test
	public void saveDeleteShortURLTest() {
		Assertions.assertThat(shortURLService).isNotNull();
		ShortURL shortURL = new ShortURL(Long.valueOf(1), "7777777", "8888888888", "15.03.2022", Long.valueOf("0"));
		Assertions.assertThat(shortURL).isNotNull();
		ShortURL shortURLFromDBShortURL = shortURLService.saveShortURL(shortURL);
		Assertions.assertThat(shortURLFromDBShortURL).isNotNull();		
		Assertions.assertThat(shortURLService.deleteShortURL(shortURLFromDBShortURL.getId())).isTrue();
	}
	
	@Test
	public void updateShortURLTest() {
		ShortURL shortURL = new ShortURL(Long.valueOf(1), "777", "88888", "15.03.2022", Long.valueOf("0"));
		ShortURL shortURLFromDBShortURL = shortURLService.saveShortURL(shortURL);
		Assertions.assertThat(shortURLFromDBShortURL.getLifetime()).isEqualTo("15.03.2022");
		shortURL.setLifetime("15.03.2222");
		shortURLFromDBShortURL = shortURLService.updateShortURL(shortURL);
		Assertions.assertThat(shortURLFromDBShortURL.getLifetime()).isEqualTo("15.03.2222");
		shortURLService.deleteShortURL(shortURLFromDBShortURL.getId());
	}
	
	@Test
	public void findByIdShortURLTest() {
		ShortURL shortURL = new ShortURL(Long.valueOf(1), "5555", "88866666888", "15.03.2022", Long.valueOf("0"));
		ShortURL shortURLFromDBShortURL = shortURLService.saveShortURL(shortURL);
		Assertions.assertThat(shortURLFromDBShortURL).isNotNull();
		Long idShortURL = shortURLFromDBShortURL.getId();
		ShortURL findShortURLFromDBShortURL = shortURLService.findByIdShortURL(idShortURL);
		Assertions.assertThat(findShortURLFromDBShortURL.getId()).isEqualTo(shortURLFromDBShortURL.getId());
		Assertions.assertThat(findShortURLFromDBShortURL.getShortURL()).isEqualTo(shortURLFromDBShortURL.getShortURL());
		shortURLService.deleteShortURL(idShortURL);
	}
	
	@Test
	public void findShortURLTest() {
		ShortURL shortURL = new ShortURL(Long.valueOf(1), "7777777", "889999999", "15.03.2022", Long.valueOf("0"));
		ShortURL shortURLFromDBShortURL = shortURLService.saveShortURL(shortURL);
		ShortURL findShortURLFromDBShortURL = shortURLService.findShortURL(shortURLFromDBShortURL.getShortURL());
		Assertions.assertThat(findShortURLFromDBShortURL.getLongURL()).isEqualTo(shortURLFromDBShortURL.getLongURL());
		shortURLService.deleteShortURL(shortURLFromDBShortURL.getId());
	}
	
	@Test
	public void findAllShortURLTest() {
		ShortURL shortURL = new ShortURL(Long.valueOf(1), "77111777", "8888111888", "15.03.2022", Long.valueOf("0"));
		ShortURL shortURLFromDBShortURL = shortURLService.saveShortURL(shortURL);
		Iterable<ShortURL> shortURLs = shortURLService.findAllShortURL();
		Assertions.assertThat(shortURLs).extracting(ShortURL::getShortURL).contains(shortURLFromDBShortURL.getShortURL());
		shortURLService.deleteShortURL(shortURLFromDBShortURL.getId());
	}
}
