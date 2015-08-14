package com.github.ricksbrown.cowsay;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Rick Brown
 */
public class Cowsay {
	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			Set<String> modes = CowFace.cowModes.keySet();
			CommandLine commandLine = CowsayCli.parseCmdArgs(args);
			if (commandLine.hasOption("h")) {
				CowsayCli.showCmdLineHelp();
			}
			else {
				String cowfileSpec = Cowloader.DEFAULT_COW;
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

				String cowTemplate = Cowloader.load(cowfileSpec);
				if (cowTemplate != null) {
					String moosages[] = commandLine.getArgs();
					String moosage = StringUtils.join(moosages);
					if (moosage != null && moosage.length() > 0) {
						String cow = CowFormatter.formatCow(cowTemplate, cowFace, new Message(moosage, false));
						System.out.println(cow);
					}
				}
			}
		}
		catch (CowParseException ex) {
			Logger.getLogger(Cowsay.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
