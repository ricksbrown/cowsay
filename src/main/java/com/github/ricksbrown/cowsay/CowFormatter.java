package com.github.ricksbrown.cowsay;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * For a given cow template cleans comments, inserts message, cow face, removes header footer etc.
 *
 * @author Rick Brown
 */
public final class CowFormatter {

	private static final Pattern COWSTART_RE = Pattern.compile(".*\\$the_cow\\s*=\\s*<<\"?EOC\"?;?", Pattern.DOTALL);

	/**
	 * Utility class does not need constructor.
	 */
	private CowFormatter() {

	}

	/**
	 * Extracts the ascii art part of the cowfile, removing any before or after PERL comments, variable assignments, weird EOC markers etc.
	 * @param cow The raw cowfile content.
	 * @return The ascii art portion of the cowfile.
	 * @throws CowParseException if the cowfile cannot be parsed.
	 */
	private static String extractCowTemplate(final String cow) throws CowParseException {

		Matcher matcher = COWSTART_RE.matcher(cow);
		if (matcher.find(0)) {
			String result = matcher.replaceFirst("");
			return result;
		} else {
			throw new CowParseException("Could not parse cow " + cow);
		}
	}

	/**
	 * Processes the cow template (the raw content of a cowfile) inserting face and message as appropriate.
	 * @param cow The content of a cowfile.
	 * @param face The face to apply to this cow.
	 * @param message The message the cow is saying or thinking.
	 * @return The formatted cow.
	 * @throws CowParseException If the cow could not be parsed.
	 */
	public static String formatCow(final String cow, final CowFace face, final Message message) throws CowParseException {
		String result = extractCowTemplate(cow);
		String tongue = face.getTongue();
		String eyes = face.getEyes();
		// In third-party cowfile heredocs a single eye is frequently extracted using the perl chop function
		String eye = Character.toString(eyes.charAt(eyes.length() - 1));
		if (cow.contains("$eyes .= ($extra x 2);")) {
			// special case for extra three-eyes.cow - not sure how to handle it more robustly without a perl engine
			eyes += eye;
		} else if (cow.contains("$eyes .= \" $other_eye\";")) {
			// special case for udder.cow
			eyes = eye + " " + eye;
		}
		// result = result.replaceAll("\\\\\\\\", "\\\\");  // do this first
		/*
		 * A java unescape seems to work well enough for the cow files (perl heredocs).
		 * The main concern is the consume the backslashes correctly.
		 * The original regex method is not as accurate (not as true to the original cowsay).
		 * The regex method coped better with some cowfile errors (when people forget to escape a backslash,
		 * like in squirrel https://github.com/schacon/cowsay/pull/16) but worse in other scenarios (when people
		 * unnecessarily escape characters like "\#").
		 */
		result = StringEscapeUtils.unescapeJava(result);  // do this first
		// result = result.replace("\\@", "@");  // not needed with unescapeJava
		result = result.replace("${tongue}", tongue);
		result = result.replace("$tongue", tongue);
		result = result.replace("$thoughts", message.getThoughts());
		result = result.replace("${eyes}", eyes);  // sheep - doesn't help that i know zero perl
		result = result.replace("$eyes", eyes);
		result = result.replace("${eye}", eye);
		result = result.replace("$eye", eye);
		result = result.replace("${extra}", eye);
		result = result.replace("$extra", eye);
		// result = result.replace("\\$", "$");  // do this last (not needed with unescapeJava)
		result = result.replaceAll("EOC\\s*$", "");
		result = message.getMessage() + result;
		return result;
	}
}
