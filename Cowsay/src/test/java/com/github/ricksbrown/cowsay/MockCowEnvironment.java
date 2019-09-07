package com.github.ricksbrown.cowsay;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows environment variables to be mocked out
 * @author Rick Brown
 */
public class MockCowEnvironment implements Environment {

	/**
	 * Contains environment variable key/value overrides.
	 */
	private final Map<String, String> overrides = new HashMap<>();

	/**
	 * Get an environment variable by name.
	 * If a variable has been overridden the overriding value will be returned
	 * otherwise calls will be delegated to the real CowEnvironment class.
	 * @param name The name of the environment variable.
	 * @return The value of the environment variable.
	 */
	@Override
	public String getVariable(final String name) {
		if (overrides.containsKey(name)) {
			System.out.println("Override found: " + name + " = " + overrides.get(name));
			return overrides.get(name);
		}
		System.out.println("Override NOT found: " + name);
		return CowEnvironment.getInstance().getVariable(name);
	}

	/**
	 * Override the value of an environment variable.
	 * @param name The name of the variable to override.
	 * @param value The value you want to return for this environment variable.
	 */
	public void setOverride(final String name, final String value) {
		System.out.println("Setting override " + name + "=" + value);
		overrides.put(name, value);
	}

	/**
	 * Clear all overrides so it will delegate ALL calls to the real CowEnvironment class.
	 */
	public void clearOverrides() {
		overrides.clear();
	}
}
