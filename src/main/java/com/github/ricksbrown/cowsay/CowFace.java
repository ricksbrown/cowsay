package com.github.ricksbrown.cowsay;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Contains the variables used to build a cow face.
 * @author Rick Brown
 */
public class CowFace {
	public static final Map<String, CowFace> cowModes = new HashMap<String, CowFace>();

	private static final String DEFAULT_EYES = "oo";
	private static final String DEFAULT_TONGUE = "  ";

	private String eyes;  // they're watching you, they see your every move
	private String tongue;

	/**
	 * Get a default cow face.
	 */
	public CowFace () {
		this(DEFAULT_EYES, DEFAULT_TONGUE);
	}

	/**
	 * Use custom cow eyes.
	 * Default tongue will be used.
	 * @param eyes The two characters to use as eyes.
	 */
	private CowFace (final String eyes) {
		this(eyes, DEFAULT_TONGUE);
	}

	/**
	 * Get a custom cow face!
	 * @param eyes The two characters to use as eyes.
	 * @param tongue The two characters to use as tongue.
	 */
	private CowFace (final String eyes, final String tongue) {
		setEyes(eyes);
		setTongue(tongue);
	}

	public String getEyes() {
		return this.eyes;
	}

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
			return cowModes.get(mode);
		}
		return null;
	}

	public final void setEyes(final String eyes) {
		if (eyes != null && eyes.length() > 0) {
			if (eyes.length() > 2) {
				this.eyes = eyes.substring(0, 2);
			}
			else {
				this.eyes = eyes;
			}
		}
	}

	public final void setTongue(final String tongue) {
		if (tongue != null && tongue.length() > 0) {
			if (tongue.length() > 2) {
				this.tongue = tongue.substring(0, 2);
			}
			else {
				this.tongue = tongue;
			}
		}
	}

	public static final boolean isKnownMode(final String mode) {
		Set<String> modes = cowModes.keySet();
		return modes.contains(mode);
	}

	static {
		cowModes.put("b", new CowFace("=="));
		cowModes.put("d", new CowFace("xx", " U"));
		cowModes.put("g", new CowFace("$$"));
		cowModes.put("p", new CowFace("@@"));
		cowModes.put("s", new CowFace("**", " U"));
		cowModes.put("t", new CowFace("--"));
		cowModes.put("w", new CowFace("OO"));
		cowModes.put("y", new CowFace(".."));
	}
}
