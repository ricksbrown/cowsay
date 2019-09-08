package com.github.ricksbrown.cowsay;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.commons.cli.CommandLine;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * CowsayCli tests.
 * @author Rick Brown
 */
public class CowsayCliTest {
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalErr = System.err;

	/**
	 * Redirect the standard io streams so they can be read in test.
	 */
	@Before
	public void setupStreams() {
		System.setErr(new PrintStream(errContent));
	}

	/**
	 * Restore the standard io streams.
	 */
	@After
	public void restoreStreams() {
		System.setErr(originalErr);
	}

	/**
	 * Test that CLI understands --lang.
	 */
	@Test
	public void testParseCmdArgs() {
		System.out.println("parseCmdArgs");
		String lang = "de";
		String[] argv = new String[]{"--lang", lang};
		CommandLine result = CowsayCli.parseCmdArgs(argv);
		Assert.assertEquals(lang, result.getOptionValue("lang"));
	}

	/**
	 * Test that CLI shows specific messages with missing arg.
	 */
	@Test
	public void testParseCmdArgsMissingArg() {
		System.out.println("parseCmdArgs missing arg");
		String[] argv = new String[]{"-f"};
		CowsayCli.parseCmdArgs(argv);
		Assert.assertTrue("It should be talking about cowfiles", errContent.toString().contains(" cowfile "));
	}
}
