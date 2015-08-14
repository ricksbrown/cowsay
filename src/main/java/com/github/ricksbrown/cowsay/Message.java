package com.github.ricksbrown.cowsay;

import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Rick Brown
 */
public class Message {
	private static final String SAY_TOKEN = "\\\\";
	private static final String THINK_TOKEN = "o";
	private static final byte DEFAULT_WRAP = 40;
	private static int wordwrap = -1;

	private final String thoughts;
	private final String message;

	public Message (final String message, final boolean isThought) {
		this.thoughts = isThought ? THINK_TOKEN : SAY_TOKEN;
		this.message = formatMessage(message);
	}


	public String getMessage() {
		return this.message;
	}

	public String getThoughts() {
		return this.thoughts;
	}

	private String formatMessage(final String message) {
		String result;
		if (message != null) {
			int wrap = getWordwrap();
			if (wrap > 0) {
				result = WordUtils.wrap(message, wrap, null, true);
			}
			else {
				result = message;
			}
			int longestLine = getLongestLineLen(result);
			result = Bubble.formatSpeech(result, longestLine);
			return result;
		}
		return "";
	}

	public static void setWordwrap(final String wordwrap) {
		try {
			int ww = Integer.parseInt(wordwrap);
			if (ww >= 0) {
				Message.wordwrap = ww;
			}
		}
		catch(Throwable ignore) {
			// ignore
		}
	}

	public static int getWordwrap() {
		if (Message.wordwrap >= 0) {
			return Message.wordwrap;
		}
		return DEFAULT_WRAP;
	}

	private static int getLongestLineLen(final String message) {
		String newLine = System.getProperty("line.separator");
		String[] lines = message.split(newLine);
		int maxLen = 0;
		for (String line : lines) {
			maxLen = Math.max(maxLen, lines[0].length());
		}
		return maxLen;
	}
}
