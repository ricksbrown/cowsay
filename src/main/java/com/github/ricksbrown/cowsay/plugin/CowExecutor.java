package com.github.ricksbrown.cowsay.plugin;

import com.github.ricksbrown.cowsay.CowFace;
import com.github.ricksbrown.cowsay.Cowsay;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rick Brown
 */
public class CowExecutor {

	private boolean think = false;
	private String message = null;
	private String mode = null;
	private String eyes = null;
	private String tongue = null;
	private String wrap = null;
	private String cowfile = null;

	/**
	 * @param eyes Custom eyes.
	 */
	public void setEyes(String eyes) {
		this.eyes = eyes;
	}

	/**
	 *
	 * @param cowfile Specify an alternate cowfile.
	 */
	public void setCowfile(String cowfile) {
		this.cowfile = cowfile;
	}

	/**
	 *
	 * @param think If true the cow will think the message instead of saying it.
	 */
	public void setThink(boolean think) {
		this.think = think;
	}

	/**
	 * @param tongue Custom tongue.
	 */
	public void setTongue(String tongue) {
		this.tongue = tongue;
	}

	/**
	 * @param wrap Wrap message at approximately this length.
	 * Pass "0" for no wrap.
	 */
	public void setWrap(String wrap) {
		this.wrap = wrap;
	}

	/**
	 * @param message The message for the cow to say / think.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param mode The cow mode, for example "b" for "Borg".
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}


	/**
	 * Check that the mandatory properties have been set.
	 */
	private void validate() throws IllegalStateException {
		if(this.message == null) {
			throw new IllegalStateException("No message specified");
		}
	}

	/**
	 * Build an args array that can be passed to cowsay.
	 * @return commandline args
	 */
	String[] buildArgs() {
		String result[] = new String[0];
		List<String> args = new ArrayList<String>();
		if (wrap != null) {
			args.add("-W");
			args.add(wrap);
		}
		if (mode != null && CowFace.isKnownMode(mode)) {
			args.add('-' + mode);
		}
		else {
			if (eyes != null) {
				args.add("-e");
				args.add(eyes);
			}
			if (tongue != null) {
				args.add("-T");
				args.add(tongue);
			}
			if (cowfile != null) {
				args.add("-f");
				args.add(cowfile);
			}
		}
		args.add(message);
		return args.toArray(result);
	}

	/**
	 * Run cowsay with the provided properties.
	 * @return The formatted cow message.
	 * @throws IllegalStateException If mandatory properties have not been set.
	 */
	public String execute() throws IllegalStateException {
		validate();
		String[] args = buildArgs();
		String result;
		if (think) {
			result = Cowsay.think(args);
		}
		else {
			result = Cowsay.say(args);
		}
		return result;
	}
}