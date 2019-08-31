package com.github.ricksbrown.cowsay;
import org.apache.commons.lang3.StringUtils;

/**
 * Knows how to draw a speech or thought bubble around a message.
 *
 * @author Rick Brown
 */
public final class Bubble {
	private static final BubbleWrap SPEECH_BUBBLE;
	private static final BubbleWrap THOUGHT_BUBBLE;

	/**
	 * Utility class does not need a constructor.
	 */
	private Bubble() {

	}

	static {
		SPEECH_BUBBLE = new BubbleWrap();
		SPEECH_BUBBLE.setSingle('<', '>');
		SPEECH_BUBBLE.setMulti('/', '\\', '|', '|', '\\', '/');
		THOUGHT_BUBBLE = new BubbleWrap();
		THOUGHT_BUBBLE.setSingle('(', ')');
		THOUGHT_BUBBLE.setMulti('(', ')', '(', ')', '(', ')');
	}

	/**
	 * Draws a complete bubble around a line-wrapped message.
	 * @param bubble The BubbleWrap to use (e.g. SPEECH_BUBBLE or THOUGHT_BUBBLE)
	 * @param message The message to wrap, it should already be line-wrapped if appropriate.
	 * @param longestLine The length of the longest line in the message.
	 * @return The message, wrapped in a bubble.
	 */
	private static String formatBubble(final BubbleWrap bubble, final String message, final int longestLine) {
		String newLine = System.getProperty("line.separator");
		String[] lines = message.split(newLine);
		StringBuilder sb = new StringBuilder();
		sb.append(bubble.buildTop(longestLine));
		if (lines.length > 1) {
			sb.append(bubble.formatMultiOpen(lines[0], longestLine));
			for (int i = 1; i < (lines.length - 1); i++) {
				sb.append(bubble.formatMultiMid(lines[i], longestLine));
			}
			sb.append(bubble.formatMultiEnd(lines[(lines.length - 1)], longestLine));
		} else {
			sb.append(bubble.formatSingle(lines[0]));
		}
		sb.append(bubble.buildBottom(longestLine));
		return sb.toString();
	}

	/**
	 * Wraps the message in a speech bubble.
	 * @param message A message that has already been line wrapped (if necessary).
	 * @param longestLine The number of characters in the longest line of the message.
	 * @return The message, wrapped in a speech bubble.
	 */
	public static String formatSpeech(final String message, final int longestLine) {
		return formatBubble(SPEECH_BUBBLE, message, longestLine);
	}

	/**
	 * Wraps the message in a thought bubble.
	 * @param message A message that has already been line wrapped (if necessary).
	 * @param longestLine The number of characters in the longest line of the message.
	 * @return The message, wrapped in a thought bubble.
	 */
	public static String formatThought(final String message, final int longestLine) {
		return formatBubble(THOUGHT_BUBBLE, message, longestLine);
	}

	/**
	 * Instances of this class knows about bubble wrapping characters for a specific bubble type.
	 */
	private static class BubbleWrap {
		private String singleOpen;  // characters to use at start of a single line bubble
		private String singleClose;  // characters to use at end of a single line bubble
		private String multiStartOpen;  // characters to use at start of first line of a multi line bubble
		private String multiStartClose;  // characters to use at end of first line of a multi line bubble
		private String multiMidOpen;  // characters to use at start of ongoing lines of a multi line bubble
		private String multiMidClose;  // characters to use at end of ongoing lines of a multi line bubble
		private String multiEndOpen;  // characters to use at start of final line of a multi line bubble
		private String multiEndClose;  // characters to use at end of final line of a multi line bubble
		private final String newLine = System.getProperty("line.separator");

		/**
		 * Set the characters to use for a single line bubble.
		 * @param open The bubble character to use at the start of the line.
		 * @param close The bubble character to use at the end of the line.
		 */
		public void setSingle(final char open, final char close) {
			this.singleOpen = open + " ";
			this.singleClose = " " + close;
		}

		/**
		 * Set the characters to use for multi line bubbles.
		 * @param open The bubble character to use at the start of the first line.
		 * @param close The bubble character to use at the end of the first line.
		 * @param midOpen The character to use at the start of subsequent lines.
		 * @param midClose The character to use at the end of subsequent lines.
		 * @param endOpen The character to use at the start of the final line.
		 * @param endClose The character to use at the end of the final line.
		 */
		public void setMulti(final char open, final char close, final char midOpen, final char midClose, final char endOpen, final char endClose) {
			this.multiStartOpen = open + " ";
			this.multiStartClose = " " + close;
			this.multiMidOpen = midOpen + " ";
			this.multiMidClose = " " + midClose;
			this.multiEndOpen = endOpen + " ";
			this.multiEndClose = " " + endClose;
		}

		/**
		 * Build the line that forms the top of the bubble.
		 * @param longestLine The length of the longest line in the message.
		 * @return The top line.
		 */
		public String buildTop(final int longestLine) {
			String result = StringUtils.repeat('_', longestLine + 2);
			return " " + result + newLine;
		}

		/**
		 * Build the line that forms the bottom of the bubble.
		 * @param longestLine The length of the longest line in the message.
		 * @return The bottom line.
		 */
		public String buildBottom(final int longestLine) {
			String result = StringUtils.repeat('-', longestLine + 2);
			return " " + result;
		}

		/**
		 * Apply bubble formatting to a single line message.
		 * @param line The message line.
		 * @return The line with start and end characters.
		 */
		public String formatSingle(final String line) {
			String result = line;
			return singleOpen + result + singleClose + newLine;
		}

		/**
		 * Apply bubble formatting to a first line of a mutli line message.
		 * @param line The message line.
		 * @param padTo The minimum length the string should be after padding is applied.
		 * @return The line with start and end characters.
		 */
		public String formatMultiOpen(final String line, final int padTo) {
			String result = StringUtils.rightPad(line, padTo);
			return multiStartOpen + result + multiStartClose + newLine;
		}

		/**
		 * Apply bubble formatting to a subsequent line of a mutli line message.
		 * @param line The message line.
		 * @param padTo The minimum length the string should be after padding is applied.
		 * @return The line with start and end characters.
		 */
		public String formatMultiMid(final String line, final int padTo) {
			String result = StringUtils.rightPad(line, padTo);
			return multiMidOpen + result + multiMidClose + newLine;
		}

		/**
		 * Apply bubble formatting to a last line of a mutli line message.
		 * @param line The message line.
		 * @param padTo The minimum length the string should be after padding is applied.
		 * @return The line with start and end characters.
		 */
		public String formatMultiEnd(final String line, final int padTo) {
			String result = StringUtils.rightPad(line, padTo);
			return multiEndOpen + result + multiEndClose + newLine;
		}
	}
}
