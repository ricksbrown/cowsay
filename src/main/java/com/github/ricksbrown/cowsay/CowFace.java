package com.github.ricksbrown.cowsay;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Contains the variables used to build a cow face.
 * @author Rick Brown
 */
public class CowFace {
	/**
	 * The various predefined modes built into cowsay, for example "borg" mode.
	 */
	public static final Map<String, CowFace> COW_MODES = new HashMap<String, CowFace>();

	private static final String DEFAULT_EYES = "oo";
	private static final String DEFAULT_TONGUE = "  ";

	private String eyes;  // they're watching you, they see your every move
	private String tongue;

	static {
		COW_MODES.put("b", new CowFace("=="));
		COW_MODES.put("d", new CowFace("xx", "U "));
		COW_MODES.put("g", new CowFace("$$"));
		COW_MODES.put("p", new CowFace("@@"));
		COW_MODES.put("s", new CowFace("**", "U "));
		COW_MODES.put("t", new CowFace("--"));
		COW_MODES.put("w", new CowFace("OO"));
		COW_MODES.put("y", new CowFace(".."));
	}

	/**
	 * Get a default cow face.
	 */
	public CowFace() {
		this(DEFAULT_EYES, DEFAULT_TONGUE);
	}

	/**
	 * Use custom cow eyes.
	 * Default tongue will be used.
	 * @param eyes The two characters to use as eyes.
	 */
	private CowFace(final String eyes) {
		this(eyes, DEFAULT_TONGUE);
	}

	/**
	 * Get a custom cow face!
	 * @param eyes The two characters to use as eyes.
	 * @param tongue The two characters to use as tongue.
	 */
	private CowFace(final String eyes, final String tongue) {
		setEyes(eyes);
		setTongue(tongue);
	}

	/**
	 * Get the character/s to use as eyes.
	 * @return The eyes.
	 */
	public String getEyes() {
		return this.eyes;
	}

	/**
	 * Get the character/s to use as tongue.
	 * @return The tongue.
	 */
	public String getTongue() {
		return this.tongue;
	}

	/**
	 * Get cow args by mode.
	 * @param mode A mode key, "b" for Borg, "d" for dead, "g" for greedy etc
	 * @return The CowArgs for the given mode or null if not found
	 */
	protected static CowFace getByMode(final String mode) {
		if (mode != null) {
			return COW_MODES.get(mode);
		}
		return null;
	}

	/**
	 * Set custom cow eyes.
	 * @param eyes The eyes to use - if more than two characters long the first two will be used.
	 */
	public final void setEyes(final String eyes) {
		if (eyes != null && eyes.length() > 0) {
			if (eyes.length() > 2) {
				this.eyes = eyes.substring(0, 2);
			} else {
				this.eyes = eyes;
			}
		}
	}

	/**
	 * Set custom cow tongue.
	 * @param tongue The tongue to use - if more than two characters long the first two will be used.
	 */
	public final void setTongue(final String tongue) {
		if (tongue != null && tongue.length() > 0) {
			if (tongue.length() > 2) {
				this.tongue = tongue.substring(0, 2);
			} else {
				this.tongue = tongue;
			}
		}
	}

	/**
	 * Determine if the given mode flag is known and mapped to a particular cow face.
	 * @param mode The key to the mode, generally a single character, e.g. "b" for "Borg" mode.
	 * @return true if this is a known mode.
	 */
	public static final boolean isKnownMode(final String mode) {
		Set<String> modes = COW_MODES.keySet();
		return modes.contains(mode);
	}
}
