package com.nordcodes.services;

/**
 * 
 * @author hanza
 * Класс для работы с ссылками 
 * 
 */
final public class URLService {
	private final static int NUMBER_CHARACTERS_IN_SHORT_URL = 6;
	
	/**
	 * Метод, проверяющий строку на соответствие формату URL
	 * @param stringURL - строка, проверяемая на соответствие формату URL
	 * @return true - если строка соответствует формату URL, false - в противном случае.
	 */
	public final static boolean checkURL(String stringURL) {
		if(stringURL.matches("^(https?://|www\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Метод, генерирующий короткую ссылку
	 * @param longURL - длинная ссылка
	 * @return короткую ссылку
	 */
	public final static String genShortURL(String longURL) {
		StringBuilder stringBuilder = new StringBuilder();
		int i = 0;
		
		while(i < NUMBER_CHARACTERS_IN_SHORT_URL) {
			int randomNumber = (int)(Math.random() * 150);
			if(Character.isLetterOrDigit((char) randomNumber)) {
				stringBuilder.append((char) randomNumber);
				i++;
			}
		}
		return stringBuilder.toString();
	} 
}
