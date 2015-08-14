package com.github.ricksbrown.cowsay;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Rick Brown
 */
public class CowsayCli {

	private static final Options options;

	static {
		String newLine = System.getProperty("line.separator");
		options = new Options();
		options.addOption("n", false, "The given message will not be word-wrapped");
		options.addOption("W", true, "Specifies roughly (where the message should be wrapped.  "
				+ "The default is equivalent to -W 40 ");
		options.addOption("h", false, "Display this help message");
		options.addOption("l", false, "To list all cowfiles on the current COWPATH");

		options.addOption("b", false, "Initiates Borg mode");
		options.addOption("d", false, "Causes the cow to appear dead");
		options.addOption("g", false, "Invokes greedy mode");
		options.addOption("p", false, "Causes a state of paranoia to come over the cow");
		options.addOption("s", false, "Makes the cow appear thoroughly stoned");
		options.addOption("t", false, "Yields a tired cow");
		options.addOption("w", false, "Initiates wired mode");
		options.addOption("y", false, "Brings on the cow's youthful appearance");
		options.addOption("e", true, "Select the appearance of the cow's eyes");
		options.addOption("T", true, "Select the appearance of the cow's tongue");
		options.addOption("f", true, "Specifies a particular cowfile to use.  " +
			"If the cowfile spec contains '" + File.separatorChar + "' then it will be interpreted " +
			"as a path relative to the current directory.  Otherwise, cowsay " +
			"will search the path specified in the COWPATH environment variable");
	}

	public static CommandLine parseCmdArgs(final String[] argv) {
		final CommandLineParser cmdLineParser = new DefaultParser();

		try {
			return cmdLineParser.parse(options, argv);
		}
		catch (ParseException ex) {
			Logger.getLogger(CowsayCli.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	/**
	 * Displays help message to user.
	 */
	public static void showCmdLineHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("cowsay [option] message", options);
	}
}
