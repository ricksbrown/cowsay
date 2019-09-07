package com.github.ricksbrown.cowsay;

/**
 * Used to access environment variables.
 * The main reason to abstract this away is to facilitate easy test mocking.
 * @author Rick Brown
 */
public class CowEnvironment implements Environment {

	/**
	 * Only need one instance because there's only one environment.
	 */
	private static CowEnvironment instance;

	/**
	 * Singleton constructor is private.
	 */
	private CowEnvironment() {

	}

	/**
	 * Get the singleton instance.
	 * @return The singleton instance.
	 */
	public static CowEnvironment getInstance() {
		if (instance == null) {
			instance = new CowEnvironment();
		}
		return instance;
	}

	/**
	 * Get an environment variable by name.
	 * @param name The name of the environment variable.
	 * @return The value of the environment variable.
	 */
	@Override
	public String getVariable(final String name) {
		return System.getenv(name);
	}
}
