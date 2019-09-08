package com.github.ricksbrown.cowsay;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Handles parsing of command line arguments.
 *
 * @author Rick Brown
 */
public final class CowsayCli {

	/**
	 * Does not need instantiation.
	 */
	private CowsayCli() {

	}

	private static final Options OPTIONS;

	static {
		// Option descriptions are added later, if needed, in case the locale / language changes.
		OPTIONS = new Options();
		OPTIONS.addOption(Opt.NOWRAP.text, false, "");
		OPTIONS.addOption(Opt.WRAP_AT.text, true, "");
		OPTIONS.addOption(Opt.HELP.text, false, "");
		OPTIONS.addOption(Opt.LIST_COWS.text, false, "");
		Set<String> modes = CowFace.COW_MODES.keySet();
		for (String mode : modes) {
			OPTIONS.addOption(mode, false, "");
		}
		OPTIONS.addOption(Opt.EYES.text, true, "");
		OPTIONS.addOption(Opt.TONGUE.text, true, "");
		OPTIONS.addOption(Opt.COWFILE.text, true, "");
		OPTIONS.addOption(null, Opt.LANG.text, true, "");
		OPTIONS.addOption(null, Opt.HTML.text, false, "");
		OPTIONS.addOption(null, Opt.ALT.text, true, "");

		for (Option option : OPTIONS.getOptions()) {
			option.setRequired(false);
		}
	}

	/**
	 * Command line argument constants.
	 */
	public enum Opt {
		/** The given message will not be word-wrapped. */
		NOWRAP("n"),
		/** Specifies roughly where the message should be wrapped. */
		WRAP_AT("W"),
		/** Display help. */
		HELP("h"),
		/** List all cowfiles on the current COWPATH. */
		LIST_COWS("l"),
		/** Select the appearance of the cow's eyes. */
		EYES("e"),
		/** Select the appearance of the cow's tongue. */
		TONGUE("T"),
		/** Specifies a particular cowfile to use. */
		COWFILE("f"),
		/** Specifies a language besides English for inbuilt messages. */
		LANG("lang"),
		/** cowthink instead of cowsay. */
		THINK("cowthink"),
		/** Alt text describing the cow picture for non-visual users (HTML mode only). */
		ALT("alt"),
		/** Mark up cow output in HTML. */
		HTML("html");

		private final String text;

		/**
		 * @param s What the cow will say or think.
		 */
		Opt(final String s) {
			this.text = s;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * Calling this method results in adding the non-standard "--cowthink" option to the accepted commandline flags.
	 * This is useful if calling cowsay as an executable JAR where there is no other way to determine a
	 *    `cowsay` or a `cowthink` invocation.
	 */
	protected static void addCowthinkOption() {
		if (!OPTIONS.hasOption(null)) {
			OPTIONS.addOption(null, Opt.THINK.text, false, "");
		}
	}

	/**
	 * Parses an array of arguments.
	 * @param argv Arguments array.
	 * @return The parsed arguments.
	 */
	public static CommandLine parseCmdArgs(final String[] argv) {
		final CommandLineParser cmdLineParser = new DefaultParser();

		try {
			CommandLine parsed = cmdLineParser.parse(OPTIONS, argv, true);
			if (parsed.hasOption(Opt.LANG.text)) {
				String language = parsed.getOptionValue(Opt.LANG.text);
				if (language != null) {
					I18n.setLanguage(language);
				}
			}
			return parsed;
		} catch (MissingArgumentException ex) {
			Option option = ex.getOption();
			String flag = option.getOpt();
			if (flag == null) {
				flag = option.getLongOpt();
			}
			String message;
			try {
				message = I18n.getMessage("missingarg." + flag);
			} catch (MissingResourceException mre) {
				message = MessageFormat.format(I18n.getMessage("missingarg"), flag);
			}
			System.err.println(message);
		} catch (ParseException ex) {
			Logger.getLogger(CowsayCli.class.getName()).log(Level.FINEST, null, ex);
		}
		return null;
	}

	/**
	 * Call to add OPTIONS descriptions to the command line OPTIONS.
	 * This is deferred so that language / locale changes will be reflected.
	 */
	private static void updateOptionDescriptions() {
		Collection<Option> allOptions = OPTIONS.getOptions();
		for (Option option : allOptions) {
			String key = option.getOpt();
			if (key == null) {
				key = option.getLongOpt();
			}
			if (key != null) {
				String description = I18n.getMessage(key);
				if (description != null) {
					if (key.equals(Opt.COWFILE.text)) {
						description = String.format(description, File.separatorChar);
					}
					option.setDescription(description);
				}
			}
		}
	}

	/**
	 * Checks StdIn for piped input.
	 * @return All lines from StdIn.
	 */
	public static String[] getPipedInput() {
		List<String> messages = new ArrayList<>();
		try (InputStreamReader isr = new InputStreamReader(System.in)) {
			if (isr.ready()) {
				try (BufferedReader bufferedReader = new BufferedReader(isr)) {
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						messages.add(line);
					}
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(CowsayCli.class.getName()).log(Level.WARNING, null, ex);
		}
		return messages.toArray(new String[messages.size()]);
	}

	/**
	 * Displays help message to user.
	 */
	public static void showCmdLineHelp() {
		HelpFormatter formatter = new HelpFormatter();
		updateOptionDescriptions();
		formatter.printHelp(I18n.getMessage("usage"), OPTIONS);
	}
}
