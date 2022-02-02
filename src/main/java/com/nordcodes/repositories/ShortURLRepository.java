package com.nordcodes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nordcodes.entities.ShortURL;

public interface ShortURLRepository extends JpaRepository<ShortURL, Long> {
	ShortURL findByShortURL(String shortURL);
}
