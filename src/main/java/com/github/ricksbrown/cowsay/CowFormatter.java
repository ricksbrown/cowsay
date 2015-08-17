package com.github.ricksbrown.cowsay;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * For a given cow template cleans comments, inserts message, cow face, removes header footer etc.
 *
 * @author Rick Brown
 */
public class CowFormatter {

	private static final Pattern COWSTART_RE = Pattern.compile(".*\\$the_cow\\s*=\\s*<<\"?EOC\"?;?", Pattern.DOTALL);

	/**
	 * Extracts the ascii art part of the cowfile, removing any before or after PERL comments, variable assignments, weird EOC markers etc.
	 * @param cow The raw cowfile content.
	 * @return The ascii art portion of the cowfile.
	 * @throws CowParseException if the cowfile cannot be parsed.
	 */
	private static String extractCowTemplate (final String cow) throws CowParseException {

		Matcher matcher = COWSTART_RE.matcher(cow);
		if (matcher.find(0)) {
			String result = matcher.replaceFirst("");
			return result;
		}
		else {
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
		result = result.replaceAll("\\\\\\\\", "\\\\");  // do this first
		result = result.replace("\\@", "@");
		result = result.replace("\\$", "$");
		result = result.replace("${tongue}", tongue);
		result = result.replace("$tongue", tongue);
		result = result.replace("$thoughts", message.getThoughts());
		result = result.replace("${eyes}", eyes);  // sheep - doesn't help that i know zero perl
		result = result.replace("$eyes", eyes);
		result = result.replaceAll("EOC\\s*$", "");
		result = message.getMessage() + result;
		return result;
	}
}
