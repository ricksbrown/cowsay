package com.github.ricksbrown.cowsay;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Represents a "bubble-wrapped" and "line-wrapped" moosage portion of the cowsay / cowthink output.
 * This is the user provided moosage line wrapped (if appropriate) and formatted with a thought or speech bubble.
 *
 * @author Rick Brown
 */
public class Message {
	/**
	 * The default wrap point for long lines.
	 */
	public static final byte DEFAULT_WRAP = 40;
	private static final String SAY_TOKEN = "\\";
	private static final String THINK_TOKEN = "o";
	private int wrapat = -1;

	private final String moosage;
	private final boolean isThinking;

	/**
	 * Create the formatted moosage.
	 * @param message The user provided moosage to format.
	 * @param isThought true if this should be formatted as thought instead of speech.
	 */
	public Message(final String message, final boolean isThought) {
		this.isThinking = isThought;
		this.moosage = message;
	}

	/**
	 * Get the formatted moosage.
	 * @return The moosage formatted with speech or thought bubble.
	 */
	public String getMessage() {
		return formatMessage(this.moosage);
	}

	/**
	 * Get the character/s to use for the lines going from the cow to the bubble.
	 * @return The correct character/s to use for `$thoughts`.
	 */
	public String getThoughts() {
		return this.isThinking ? THINK_TOKEN : SAY_TOKEN;
	}

	/**
	 * Applies word wrapping to the moosage to handle long lines.
	 * @param message The raw input moosage.
	 * @return The moosage with long lines wrapped.
	 */
	private String wrapMessage(final String message) {
		// Note that the original cowsay wraps lines mid-word.
		// This version differs in that it wraps between words if possible.
		int wrap = getWordwrap();
		if (wrap <= 0) {
			return message;
		}
		final List<String> result = new ArrayList<>();
		String newLine = System.getProperty("line.separator");
		String[] lines = message.split(newLine);
		for (String line : lines) {
			result.add(WordUtils.wrap(line, wrap, null, true));
		}
		return StringUtils.join(result, newLine);
	}

	/**
	 * Builds the bubble around the moosage.
	 * @param message The plain moosage as provided by the user.
	 * @return The moosage, line-wrapped and bubble-wrapped.
	 */
	private String formatMessage(final String message) {
		String result;
		if (message != null) {
			result = wrapMessage(message);
			int longestLine = getLongestLineLen(result);
			if (!isThinking) {
				result = Bubble.formatSpeech(result, longestLine);
			} else {
				result = Bubble.formatThought(result, longestLine);
			}
			return result;
		}
		return "";
	}

	/**
	 * Set the length of the wrapat, default is "40", zero disables line-wrap.
	 * @param wordwrap A number indicating where (approximately) to line-wrap the moosage.
	 */
	public void setWordwrap(final String wordwrap) {
		try {
			int ww = Integer.parseInt(wordwrap);
			if (ww >= 0) {
				this.wrapat = ww;
			}
		} catch (NumberFormatException ignore) {
			// ignore
		}
	}

	/**
	 * Get the current value used for line wrap length.
	 * @return The current line-wrap value.
	 */
	public int getWordwrap() {
		if (this.wrapat >= 0) {
			return this.wrapat;
		}
		return DEFAULT_WRAP;
	}

	/**
	 * For a given multiline moosage determines the character count of the longest line.
	 * @param message The moosage after line-wrap has been applied but before any bubble wrapping.
	 * @return The count of characters in the longest line.
	 */
	private static int getLongestLineLen(final String message) {
		String newLine = System.getProperty("line.separator");
		String[] lines = message.split(newLine);
		int maxLen = 0;
		for (String line : lines) {
			maxLen = Math.max(maxLen, line.length());
		}
		return maxLen;
	}
}
