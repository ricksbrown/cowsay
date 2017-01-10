package com.github.ricksbrown.cowsay;

/**
 * Thrown if a cowfile cannot be parsed.
 * @author Rick Brown
 */
public class CowParseException extends Exception {

	/**
	 * Create an exception with the given message and preceding exception.
	 * @param message A message which explains this exception.
	 * @param cause The root cause.
	 */
	public CowParseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create an exception with the given message.
	 * @param message A message which explains this exception.
	 */
	public CowParseException(final String message) {
		super(message);
	}
}
