package com.github.ricksbrown.cowsay;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;

/**
 * Java port of the original cowsay commandline utility.
 * There are three entry points to this class:
 * <ul>
 *	<li>{@code Cowsay.main(args);} will call either cowsay or cowthink depending on the --cowthink flag</li>
 *	<li>{@code Cowsay.say(args);} is equivalent to cowsay</li>
 *	<li>{@code Cowsay.think(args);} is equivalent to cowthink</li>
 * </ul>
 *
 * @author Rick Brown
 */
public class Cowsay {

	/**
	 * Equivalent to cowsay.
	 * @param args the command line arguments
	 * @return What the cow said.
	 */
	public static String say(final String[] args) {
		return sayOrThink(args, false);
	}

	/**
	 * Equivalent to cowthink.
	 * @param args the command line arguments
	 * @return What the cow thought.
	 */
	public static String think(final String[] args) {
		return sayOrThink(args, true);
	}

	/**
	 * Do some cowsaying or cowthinking.
	 * @param args the command line arguments
	 * @param think if true will think instead of say (the --cowthink flag can also invoke thinking)
	 */
	private static String sayOrThink(final String[] args, final boolean think) {
		try {
			boolean isThought = think;
			Set<String> modes = CowFace.cowModes.keySet();
			String wordwrap = null;
			CommandLine commandLine = CowsayCli.parseCmdArgs(args);
			if (commandLine != null) {
				if (commandLine.hasOption(CowsayCli.Opt.HELP.toString())) {
					CowsayCli.showCmdLineHelp();
				}
				else if (commandLine.hasOption(CowsayCli.Opt.LIST_COWS.toString())) {
					String[] files = Cowloader.listAllCowfiles();
					if (files != null) {
						return StringUtils.join(files, System.getProperty("line.separator"));
					}
				}
				else {
					String cowfileSpec = null;
					CowFace cowFace = null;

					if (commandLine.hasOption(CowsayCli.Opt.WRAP_AT.toString())) {
						wordwrap = commandLine.getOptionValue(CowsayCli.Opt.WRAP_AT.toString());
					}
					else if (commandLine.hasOption(CowsayCli.Opt.NOWRAP.toString())) {
						wordwrap = "0";
					}

					for (String mode : modes) {
						if (commandLine.hasOption(mode)) {
							cowFace = CowFace.getByMode(mode);
							break;
						}
					}

					if (cowFace == null) {
						// if we are in here no modes were set
						cowFace = new CowFace();
						if (commandLine.hasOption(CowsayCli.Opt.COWFILE.toString())) {
							cowfileSpec = commandLine.getOptionValue(CowsayCli.Opt.COWFILE.toString());
						}
						if (commandLine.hasOption(CowsayCli.Opt.EYES.toString())) {
							cowFace.setEyes(commandLine.getOptionValue(CowsayCli.Opt.EYES.toString()));
						}
						if (commandLine.hasOption(CowsayCli.Opt.TONGUE.toString())) {
							cowFace.setTongue(commandLine.getOptionValue(CowsayCli.Opt.TONGUE.toString()));
						}
					}

					if (commandLine.hasOption(CowsayCli.Opt.THINK.toString())) {
						isThought = true;
					}

					if (cowfileSpec == null) {
						cowfileSpec = Cowloader.DEFAULT_COW;
					}

					String cowTemplate = Cowloader.load(cowfileSpec);
					if (cowTemplate != null) {
						String moosages[] = commandLine.getArgs();
						String moosage = StringUtils.join(moosages);
						if (moosage != null && moosage.length() > 0) {
							Message message = new Message(moosage, isThought);
							if (wordwrap != null) {
								message.setWordwrap(wordwrap);
							}
							String cow = CowFormatter.formatCow(cowTemplate, cowFace, message);
							return cow;
						}
					}
				}
			}
		}
		catch (CowParseException ex) {
			Logger.getLogger(Cowsay.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	/**
	 * Will cowsay (or cowthink if the --cowthink flag is set)
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		CowsayCli.addCowthinkOption();
		String cowsay = say(args);
		if (cowsay != null && cowsay.length() > 0) {
			System.out.println(cowsay);
		}
	}
}
