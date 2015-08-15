package com.github.ricksbrown.cowsay;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;

/**
 * Java port of the original cowsay commandline utility.
 * Call `say` for cowsay or `think` for cowthink.
 * @author Rick Brown
 */
public class Cowsay {

	/**
	 * cowsay
	 * @param args the command line arguments
	 */
	public static void say(final String[] args) {
		sayOrThink(args, false);
	}

	/**
	 * cowthink
	 * @param args the command line arguments
	 */
	public static void think(final String[] args) {
		sayOrThink(args, true);
	}

	/**
	 *
	 * @param args the command line arguments
	 * @param think if true will think instead of say
	 */
	private static void sayOrThink(final String[] args, final boolean think) {
		try {
			Set<String> modes = CowFace.cowModes.keySet();
			CommandLine commandLine = CowsayCli.parseCmdArgs(args);
			if (commandLine.hasOption("h")) {
				CowsayCli.showCmdLineHelp();
			}
			else if (commandLine.hasOption("l")) {
				String[] files = Cowloader.listAllCowfiles();
				if (files != null) {
					for (String file : files) {
						System.out.println(file);
					}
				}
			}
			else {
				String cowfileSpec = null;
				CowFace cowFace = null;
				if (commandLine.hasOption("W")) {
					Message.setWordwrap(commandLine.getOptionValue("W"));
				}
				else if (commandLine.hasOption("n")) {
					Message.setWordwrap("0");
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
					if (commandLine.hasOption("f")) {
						cowfileSpec = commandLine.getOptionValue("f");
					}
					if (commandLine.hasOption("e")) {
						cowFace.setEyes(commandLine.getOptionValue("e"));
					}
					if (commandLine.hasOption("T")) {
						cowFace.setEyes(commandLine.getOptionValue("T"));
					}
				}
				if (cowfileSpec == null) {
					cowfileSpec = Cowloader.DEFAULT_COW;
				}

				String cowTemplate = Cowloader.load(cowfileSpec);
				if (cowTemplate != null) {
					String moosages[] = commandLine.getArgs();
					String moosage = StringUtils.join(moosages);
					if (moosage != null && moosage.length() > 0) {
						String cow = CowFormatter.formatCow(cowTemplate, cowFace, new Message(moosage, think));
						System.out.println(cow);
					}
				}
			}
		}
		catch (CowParseException ex) {
			Logger.getLogger(Cowsay.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		say(args);
	}
}
