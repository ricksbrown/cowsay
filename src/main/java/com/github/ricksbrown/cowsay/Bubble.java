package com.github.ricksbrown.cowsay;
import org.apache.commons.lang3.StringUtils;

/**
 * Knows how to draw a speech or thought bubble around a message.
 *
 * @author Rick Brown
 */
public class Bubble {
	private static final BubbleWrap speechBubble;
	private static final BubbleWrap thoughtBubble;

	static {
		speechBubble = new BubbleWrap();
		speechBubble.setSingle('<', '>');
		speechBubble.setMulti('/', '\\', '|', '|', '\\', '/');
		thoughtBubble = new BubbleWrap();
		thoughtBubble.setSingle('(', ')');
		thoughtBubble.setMulti('(', ')', '(', ')', '(', ')');
	}

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
		}
		else {
			sb.append(bubble.formatSingle(lines[0]));
		}
		sb.append(bubble.buildBottom(longestLine));
		return sb.toString();
	}

	/**
	 *
	 * @param message A message that has already been line wrapped (if necessary).
	 * @param longestLine The number of characters in the longest line of the message.
	 * @return The message, wrapped in a speech bubble.
	 */
	public static String formatSpeech(final String message, final int longestLine) {
		return formatBubble(speechBubble, message, longestLine);
	}

	/**
	 *
	 * @param message A message that has already been line wrapped (if necessary).
	 * @param longestLine The number of characters in the longest line of the message.
	 * @return The message, wrapped in a thought bubble.
	 */
	public static String formatThought(final String message, final int longestLine) {
		return formatBubble(thoughtBubble, message, longestLine);
	}

	/**
	 * Knows about bubble wrapping characters.
	 */
	private static class BubbleWrap {
		private String singleOpen;
		private String singleClose;
		private String multiStartOpen;
		private String multiStartClose;
		private String multiMidOpen;
		private String multiMidClose;
		private String multiEndOpen;
		private String multiEndClose;
		private final String newLine = System.getProperty("line.separator");

		public void setSingle(final char open, final char close) {
			this.singleOpen = open + " ";
			this.singleClose = " " + close;
		}

		public void setMulti(final char open, final char close, final char midOpen, final char midClose, final char endOpen, final char endClose) {
			this.multiStartOpen = open + " ";
			this.multiStartClose = " " + close;
			this.multiMidOpen = midOpen + " ";
			this.multiMidClose = " " + midClose;
			this.multiEndOpen = endOpen + " ";
			this.multiEndClose = " " + endClose;
		}

		public String buildTop(final int longestLine) {
			String result = StringUtils.repeat('_', longestLine + 2);
			return " " + result + newLine;
		}

		public String buildBottom(final int longestLine) {
			String result = StringUtils.repeat('-', longestLine + 2);
			return " " + result;
		}

		public String formatSingle(final String line) {
			String result = line;
			return singleOpen + result + singleClose + newLine;
		}

		public String formatMultiOpen(final String line, final int padTo) {
			String result = StringUtils.rightPad(line, padTo);
			return multiStartOpen + result + multiStartClose + newLine;
		}

		public String formatMultiMid(final String line, final int padTo) {
			String result = StringUtils.rightPad(line, padTo);
			return multiMidOpen + result + multiMidClose + newLine;
		}

		public String formatMultiEnd(final String line, final int padTo) {
			String result = StringUtils.rightPad(line, padTo);
			return multiEndOpen + result + multiEndClose + newLine;
		}
	}
}
