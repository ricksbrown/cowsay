package com.github.ricksbrown.cowsay;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Allows cowsay to be internationalized.
 * Moo! Muh! Meuh!
 * @author Rick Brown
 */
public final class I18n {
	private static final String DEFAULT_LANG = "en";
	private static Locale currentLocale = null;
	private static ResourceBundle messages = null;

	/**
	 * Utility classes do not need constructors.
	 */
	private I18n() {

	}

	/**
	 * Set the language.
	 * @param language A BCP47 language, e.g. "en", "fr".
	 */
	public static void setLanguage(final String language) {
		currentLocale = new Locale(language);
		messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	}

	/**
	 * Get a message in the correct language.
	 * @param key The lookup key for the message.
	 * @return The message in the correct language, if found, otherwise in the default language.
	 */
	protected static String getMessage(final String key) {
		if (messages == null) {
			setLanguage(DEFAULT_LANG);
		}
		return messages.getString(key);
	}

}
