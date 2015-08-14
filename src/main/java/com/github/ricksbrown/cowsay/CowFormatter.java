package com.github.ricksbrown.cowsay;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * For a given cow template cleans comments, inserts message, cow face, removes header footer etc.
 *
 * @author Rick Brown
 */
public class CowFormatter {

	private static final Pattern COWSTART_RE = Pattern.compile(".*\\$the_cow\\s*=\\s*<<\"?EOC\"?;", Pattern.DOTALL);
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
		result = result.replaceAll("\\$eyes", face.getEyes());
		result = result.replaceAll("\\\\\\\\", "\\\\");
		result = result.replaceAll("\\$tongue", face.getTongue());
		result = result.replaceAll("\\$thoughts", message.getThoughts());
		result = result.replaceAll("EOC\\s*$", "");
		result = message.getMessage() + result;
		return result;
	}
}
