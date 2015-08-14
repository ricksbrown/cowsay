package com.github.ricksbrown.cowsay;

import static com.github.ricksbrown.cowsay.CowsayCli.showCmdLineHelp;
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
	public static void main(String[] args) {
		try {
			CommandLine commandLine = CowsayCli.parseCmdArgs(args);
			if (commandLine.hasOption("h")) {
				showCmdLineHelp();
			}
			else {
				String cowfileSpec = Cowloader.DEFAULT_COW;
				CowFace cowFace = new CowFace();
				if (commandLine.hasOption("W")) {
					Message.setWordwrap(commandLine.getOptionValue("W"));
				}
				else if (commandLine.hasOption("n")) {
					Message.setWordwrap("0");
				}
				if (commandLine.hasOption("f")) {
					cowfileSpec = commandLine.getOptionValue("f");
				}
				if (commandLine.hasOption("e")) {
					cowFace.setEyes(commandLine.getOptionValue("e"));
				}
				if (commandLine.hasOption("T")) {
					cowFace.setEyes(commandLine.getOptionValue("T"));
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
