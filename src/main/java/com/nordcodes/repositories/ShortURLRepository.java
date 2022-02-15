package com.nordcodes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nordcodes.entities.ShortURL;

/**
 * 
 * @author hanza
 * Интерфейс взаимодействия с базой данных для работы с короткими ссылками
 *
 */
public interface ShortURLRepository extends JpaRepository<ShortURL, Long> {
	ShortURL findByShortURL(String shortURL);
}
