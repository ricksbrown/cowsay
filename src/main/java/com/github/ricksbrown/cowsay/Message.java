package com.github.ricksbrown.cowsay;

import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Rick Brown
 */
public class Message {
	private static final String SAY_TOKEN = "\\\\";
	private static final String THINK_TOKEN = "o";
	public static final byte DEFAULT_WRAP = 40;
	private int wordwrap = -1;

	private final String message;
	private final boolean isThought;

	public Message (final String message, final boolean isThought) {
		this.isThought = isThought;
		this.message = message;
	}


	public String getMessage() {
		return formatMessage(this.message);
	}

	public String getThoughts() {
		return this.isThought ? THINK_TOKEN : SAY_TOKEN;
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
			if (!isThought) {
				result = Bubble.formatSpeech(result, longestLine);
			}
			else {
				result = Bubble.formatThought(result, longestLine);
			}
			return result;
		}
		return "";
	}

	public void setWordwrap(final String wordwrap) {
		try {
			int ww = Integer.parseInt(wordwrap);
			if (ww >= 0) {
				this.wordwrap = ww;
			}
		}
		catch(Throwable ignore) {
			// ignore
		}
	}

	public int getWordwrap() {
		if (this.wordwrap >= 0) {
			return this.wordwrap;
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
