package com.nordcodes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 
 * @author hanza
 * Класс - объект представляющий короткую ссылки
 * 
 */
@Entity
@Table(name = "short_urls")
public class ShortURL {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "user_id")
	private Long userId; 
	@NotBlank
	@Column(name = "short_url")
	private String shortURL;
	@NotBlank
	@Column(name = "long_url")
	private String longURL;
	private String lifetime;
	@Column(name = "transition_counter")
	private Long transitionCounter;

	public ShortURL() {
	}
	
	public ShortURL(Long userId, String shortURL, String longURL, String lifetime, Long transitionCounter) {
		this.userId = userId;
		this.shortURL = shortURL;
		this.longURL = longURL;
		this.lifetime = lifetime;
		this.transitionCounter = transitionCounter;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getShortURL() {
		return shortURL;
	}
	
	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}
	
	public String getLongURL() {
		return longURL;
	}
	
	public void setLongURL(String longURL) {
		this.longURL = longURL;
	}
	
	public String getLifetime() {
		return lifetime;
	}
	
	public void setLifetime(String lifetime) {
		this.lifetime = lifetime;
	}
	
	public Long getTransitionCounter() {
		return transitionCounter;
	}

	public void setTransitionCounter(Long transitionCounter) {
		this.transitionCounter = transitionCounter;
	}
}
