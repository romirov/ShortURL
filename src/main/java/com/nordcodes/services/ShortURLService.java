package com.nordcodes.services;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nordcodes.entities.ShortURL;
import com.nordcodes.repositories.ShortURLRepository;

/**
 * 
 * @author hanza
 * Класс, реализующий интерфейс взаимодействия с базой данных для работы с короткими ссылками
 * 
 */
@Service
public class ShortURLService {
	@Autowired
	private ShortURLRepository shortURLRepository;
	private static final Logger logger = LogManager.getLogger("ShortURLService");

	public ShortURL findByIdShortURL(Long shortURLId) {
		logger.log(Level.INFO, "Looking for a URL by ID '" + shortURLId + "'..");
		Optional<ShortURL> shortURLFromDb = shortURLRepository.findById(shortURLId);
		return shortURLFromDb.orElse(new ShortURL());
	}
	
	public ShortURL findShortURL(String shortURLString) {
		logger.log(Level.INFO, "Looking for a URL '" + shortURLString + "'..");
		ShortURL shortURL = shortURLRepository.findByShortURL(shortURLString);

		if(shortURL == null) {
			logger.log(Level.ERROR, "URL '" + shortURLString + "' not found.");
			return null;
		}
		
		logger.log(Level.INFO, "URL '" + shortURLString + "' found");
		return shortURL;
	}

	public List<ShortURL> findAllShortURL(){
		logger.log(Level.INFO, "Looking for all URLs.");
		return shortURLRepository.findAll();
	}

	public ShortURL saveShortURL(ShortURL shortURL) {
		logger.log(Level.INFO, "Storing user data in the database..");
		ShortURL shortURLFromDB = shortURLRepository.findByShortURL(shortURL.getShortURL());

		if (shortURLFromDB != null) {
			logger.log(Level.ERROR, "The same URL is worn in the database.");
			return null;
		}

		shortURL = shortURLRepository.save(shortURL);
		logger.log(Level.INFO, "URL '" + shortURL.getShortURL() + "' data saved.");
		return shortURL;
	}

	public ShortURL updateShortURL(ShortURL shortURL) {
		logger.log(Level.INFO, "Update URL '" + shortURL.getShortURL() + "' in database..");
		ShortURL newShortURL = shortURLRepository.save(shortURL);

		if(newShortURL == null) {
			logger.log(Level.ERROR, "URL '" + newShortURL + "' not updated.");
			return null;
		}

		logger.log(Level.INFO, "URL '" + newShortURL.getShortURL() + "' updated");
		return newShortURL;
	}

	public boolean deleteShortURL(Long shortURLId) {
		logger.log(Level.INFO, "Removing a URL from an ID '" + shortURLId + "'..");

		if (shortURLRepository.findById(shortURLId).isPresent()) {
			logger.log(Level.INFO, "URL with ID '" + shortURLId + "' deleted.");
			shortURLRepository.deleteById(shortURLId);
			return true;
		}

		logger.log(Level.ERROR, "URL with ID '" + shortURLId + "' not deleted.");
		return false;
	}
}
