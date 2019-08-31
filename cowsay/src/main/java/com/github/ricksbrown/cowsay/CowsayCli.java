package com.github.ricksbrown.cowsay;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
public class CowsayCli {

	private static final Options options;

	static {
		// Option descriptions are added later, if needed, in case the locale / language changes.
		options = new Options();
		options.addOption(Opt.NOWRAP.text, false, "");
		options.addOption(Opt.WRAP_AT.text, true, "");
		options.addOption(Opt.HELP.text, false, "");
		options.addOption(Opt.LIST_COWS.text, false, "");
		Set<String> modes = CowFace.COW_MODES.keySet();
		for (String mode : modes) {
			options.addOption(mode, false, "");
		}
		options.addOption(Opt.EYES.text, true, "");
		options.addOption(Opt.TONGUE.text, true, "");
		options.addOption(Opt.COWFILE.text, true, "");
		options.addOption(null, Opt.LANG.text, true, "");
		options.addOption(null, Opt.HTML.text, false, "");
		options.addOption(null, Opt.ALT.text, true, "");

		for (Option option : options.getOptions()) {
			option.setRequired(false);
		}
	}

	/**
	 * Command line argument constants.
	 */
	public enum Opt {
		NOWRAP("n"),
		WRAP_AT("W"),
		HELP("h"),
		LIST_COWS("l"),
		EYES("e"),
		TONGUE("T"),
		COWFILE("f"),
		LANG("lang"),
		THINK("cowthink"),
		ALT("alt"),
		HTML("html");

		final String text;

		Opt(final String text) {
			this.text = text;
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
		if (!options.hasOption(null)) {
			options.addOption(null, Opt.THINK.text, false, "");
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
			CommandLine parsed = cmdLineParser.parse(options, argv, true);
			if (parsed.hasOption(Opt.LANG.text)) {
				String language = parsed.getOptionValue(Opt.LANG.text);
				if (language != null) {
					I18n.setLanguage(language);
				}
			}
			return parsed;
		}
		catch (MissingArgumentException ex) {
			Option option = ex.getOption();
			String flag = option.getOpt();
			if (flag == null) {
				flag = option.getLongOpt();
			}
			Logger.getLogger(CowsayCli.class.getName()).log(Level.INFO, I18n.getMessage("missingarg"), flag);
		}
		catch (ParseException ex) {
			Logger.getLogger(CowsayCli.class.getName()).log(Level.FINEST, null, ex);
		}
		return null;
	}

	/**
	 * Call to add options descriptions to the command line options.
	 * This is deferred so that language / locale changes will be reflected.
	 */
	private static void updateOptionDescriptions() {
		Collection<Option> allOptions = options.getOptions();
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
		formatter.printHelp(I18n.getMessage("usage"), options);
	}
}
