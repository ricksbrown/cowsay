package com.github.ricksbrown.cowsay;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Rick Brown
 */
public class Bubble {
	public static String formatSpeech(final String message, final int longestLine) {
		String newLine = System.getProperty("line.separator");
		String[] lines = message.split(newLine);
		StringBuilder sb = new StringBuilder();
		sb.append(' ').append(StringUtils.repeat('_', longestLine + 2));
		sb.append(newLine);
		if (lines.length > 1) {
			sb.append("/ ").append(StringUtils.rightPad(lines[0], longestLine)).append(" \\");
			sb.append(newLine);
			for (int i = 1; i < (lines.length - 1); i++) {
				sb.append("| ").append(StringUtils.rightPad(lines[i], longestLine)).append(" |");
				sb.append(newLine);
			}
			sb.append("\\ ").append(StringUtils.rightPad(lines[(lines.length - 1)], longestLine)).append(" /");
			sb.append(newLine);
		}
		else {
			sb.append("< ").append(lines[0]).append(" >");
			sb.append(newLine);
		}
		sb.append(' ').append(StringUtils.repeat('-', longestLine + 2));
		return sb.toString();
	}

	public static String formatThought(final String message, final int longestLine) {
		String newLine = System.getProperty("line.separator");
		String[] lines = message.split(newLine);
		StringBuilder sb = new StringBuilder();
		sb.append(' ').append(StringUtils.repeat('_', longestLine + 2));
		sb.append(newLine);
		if (lines.length > 1) {
			sb.append("( ").append(StringUtils.rightPad(lines[0], longestLine)).append(" )");
			sb.append(newLine);
			for (int i = 1; i < (lines.length - 1); i++) {
				sb.append("( ").append(StringUtils.rightPad(lines[i], longestLine)).append(" )");
				sb.append(newLine);
			}
			sb.append("( ").append(StringUtils.rightPad(lines[(lines.length - 1)], longestLine)).append(" )");
			sb.append(newLine);
		}
		else {
			sb.append("( ").append(lines[0]).append(" )");
			sb.append(newLine);
		}
		sb.append(' ').append(StringUtils.repeat('-', longestLine + 2));
		return sb.toString();
	}
}
