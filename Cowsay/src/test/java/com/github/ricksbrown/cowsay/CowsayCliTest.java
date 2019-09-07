package com.github.ricksbrown.cowsay;

import org.apache.commons.cli.CommandLine;
import org.junit.Assert;
import org.junit.Test;

/**
 * CowsayCli tests.
 * @author Rick Brown
 */
public class CowsayCliTest {

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
	 * Test that CLI dows not throw exception with missing arg.
	 */
	@Test
	public void testParseCmdArgsMissingArg() {
		try {
			System.out.println("parseCmdArgs missing arg");
			String[] argv = new String[]{"-f"};
			CommandLine result = CowsayCli.parseCmdArgs(argv);
		} catch (Throwable t) {
			Assert.fail(t.getMessage());
		}
	}
}
