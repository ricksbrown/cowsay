package com.github.ricksbrown.cowsay.plugin;

import com.github.ricksbrown.cowsay.CowFace;
import com.github.ricksbrown.cowsay.Cowsay;
import com.github.ricksbrown.cowsay.CowsayCli;
import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as a convenient facade to the cowsay methods that accept arrays of arguments.
 * This is useful for Maven, Ant and possibly other plugin types.
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
	private String lang = null;
	private boolean html = false;
	private String alt = null;

	/**
	 * @param eyes Custom eyes.
	 */
	public void setEyes(final String eyes) {
		this.eyes = eyes;
	}

	/**
	 *
	 * @param cowfile Specify an alternate cowfile.
	 */
	public void setCowfile(final String cowfile) {
		this.cowfile = cowfile;
	}

	/**
	 *
	 * @param think If true the cow will think the message instead of saying it.
	 */
	public void setThink(final boolean think) {
		this.think = think;
	}

	/**
	 * @param tongue Custom tongue.
	 */
	public void setTongue(final String tongue) {
		this.tongue = tongue;
	}

	/**
	 * @param wrap Wrap message at approximately this length.
	 * Pass "0" for no wrap.
	 */
	public void setWrap(final String wrap) {
		this.wrap = wrap;
	}

	/**
	 * @param message The message for the cow to say / think.
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * @param mode The cow mode, for example "b" for "Borg".
	 */
	public void setMode(final String mode) {
		this.mode = mode;
	}

	/**
	 * Check that the mandatory properties have been set.
	 * @throws IllegalStateException If no message has been provided for the cow to say or think.
	 */
	private void validate() throws IllegalStateException {
		if (this.message == null) {
			throw new IllegalStateException("No message specified");
		}
	}

	/**
	 * Set the i18n language.
	 * @param lang The language to use.
	 */
	public void setLang(final String lang) {
		this.lang = lang;
	}

	/**
	 * Enable / disable HTML output mode.
	 * @param html true to turn on HTML output mode.
	 */
	public void setHtml(final boolean html) {
		this.html = html;
	}

	/**
	 * Set the alt text for HTML mode.
	 * @param alt The alt text.
	 */
	public void setAlt(final String alt) {
		this.alt = alt;
	}


	/**
	 * Build an args array that can be passed to cowsay.
	 * @return commandline args
	 */
	protected String[] buildArgs() {
		String[] result = new String[0];
		List<String> args = new ArrayList<>();
		if (lang != null && lang.length() > 0) {
			args.add(flagify(CowsayCli.Opt.LANG.toString()));
			args.add(lang);
		}
		if (html) {
			args.add(flagify(CowsayCli.Opt.HTML.toString()));
		}
		if (alt != null && alt.length() > 0) {
			args.add(flagify(CowsayCli.Opt.ALT.toString()));
			args.add(alt);
		}
		if (wrap != null) {
			args.add(flagify(CowsayCli.Opt.WRAP_AT.toString()));
			args.add(wrap);
		}
		buildFaceArgs(args);
		args.add(message);
		return args.toArray(result);
	}

	/**
	 * Face specific flags, either a face mode or face customizations.
	 * @param args The arg line to add to.
	 */
	private void buildFaceArgs(final List<String> args) {
		if (mode != null && CowFace.isKnownMode(mode)) {
			args.add(flagify(mode));
		} else {
			if (eyes != null) {
				args.add(flagify(CowsayCli.Opt.EYES.toString()));
				args.add(eyes);
			}
			if (tongue != null) {
				args.add(flagify(CowsayCli.Opt.TONGUE.toString()));
				args.add(tongue);
			}
			if (cowfile != null) {
				args.add(flagify(CowsayCli.Opt.COWFILE.toString()));
				args.add(cowfile);
			}
		}
	}

	/**
	 * Adds the '-' or '--' to a flag name ready to be passed as a commandline arg.
	 *
	 * @param flag A flag name (e.g. "b" or "lang")
	 * @return The flag with leading dash, (e.g. "-b" or "--lang")
	 */
	private String flagify(final String flag) {
		if (flag.length() > 1) {
			return "--" + flag;
		}
		return '-' + flag;
	}

	/**
	 * Run cowsay with the provided properties.
	 * @return The formatted cow message.
	 * @throws IllegalStateException If mandatory properties have not been set.
	 */
	public String execute() {
		validate();
		String[] args = buildArgs();
		String result;
		if (think) {
			result = Cowsay.think(args);
		} else {
			result = Cowsay.say(args);
		}
		return result;
	}
}