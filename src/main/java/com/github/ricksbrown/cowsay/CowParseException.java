package com.github.ricksbrown.cowsay;

/**
 * Thrown if a cowfile cannot be parsed.
 * @author Rick Brown
 */
public class CowParseException extends Exception {

	public CowParseException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public CowParseException(final String message) {
		super(message);
	}
}
