package com.github.ricksbrown.cowsay;

/**
 * CowEnvironments must implement this interface.
 * This is primarily intended for reading the COWPATH environment variable.
 * @author Rick Brown
 */
public interface Environment {

	/**
	 * Get an environment variable by name.
	 * @param name The name of the environment variable.
	 * @return The value of the environment variable.
	 */
	public String getVariable(final String name);
}
