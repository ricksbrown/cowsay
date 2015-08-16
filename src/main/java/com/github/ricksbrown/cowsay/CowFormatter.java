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
	/*

	$the_cow = <<"EOC";
	        $thoughts   ^__^
	         $thoughts  ($eyes)\\_______
	            (__)\\       )\\/\\
	             $tongue ||----w |
	                ||     ||
	EOC

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

	public static String formatCow(final String cow, final CowFace face, final Message message) throws CowParseException {
		String result = extractCowTemplate(cow);
		String tongue = face.getTongue();
		String eyes = face.getEyes();
		result = result.replaceAll("\\\\\\\\", "\\\\");  // do this first
		result = result.replace("\\@", "@");
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
