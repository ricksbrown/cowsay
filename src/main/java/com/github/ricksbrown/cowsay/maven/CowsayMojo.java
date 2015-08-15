package com.github.ricksbrown.cowsay.maven;

import com.github.ricksbrown.cowsay.plugin.CowExecutor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
/**
 * Maven plugin that performs the critical task of running cowsay in your build.
 * @author Rick Brown
 */
public class CowsayMojo extends AbstractMojo {

	/**
	 * The message for the cow to say.
	 * @parameter property="cowsay.message"
	 */
	private String message;

	/**
	 * Optionally set a cow mode.
	 * @parameter property="cowsay.mode"
	 */
	private String mode;

	/**
	 * Optionally set custom tongue.
	 * @parameter property="cowsay.tongue"
	 */
	private String tongue;

	/**
	 * Optionally set custom eyes.
	 * @parameter property="cowsay.eyes"
	 */
	private String eyes;

	/**
	 * Optionally set a cow cowfile.
	 * @parameter property="cowsay.cowfile"
	 */
	private String cowfile;

	/**
	 * Optionally determine where the message will (approximately) wrap.
	 * Pass "0" for no wrap.
	 * @parameter property="cowsay.wrap"
	 */
	private String wrap;

	/**
	 * Optionally tell the cow to think instead of speak.
	 * @parameter property="cowsay.think"
	 */
	private boolean think;

	@Override
	public void execute() throws MojoExecutionException
	{
		try
		{
			CowExecutor executor = new CowExecutor();
			executor.setMessage(message);
			executor.setMode(mode);
			executor.setCowfile(cowfile);
			executor.setThink(think);
			executor.setEyes(eyes);
			executor.setTongue(tongue);
			executor.setWrap(wrap);

			String moo = executor.execute();
			System.out.println(moo);  // Probably need to allow for option to set a property
		}
		catch(IllegalStateException ex)
		{
			throw new MojoExecutionException(ex.getMessage(), ex);
		}
	}
}
