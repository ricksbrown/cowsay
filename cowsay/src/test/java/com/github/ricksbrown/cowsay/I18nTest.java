package com.github.ricksbrown.cowsay;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test i18n works.
 * @author Rick Brown
 */
public class I18nTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	/**
	 * Redirect the standard io streams so they can be read in test.
	 */
	@Before
	public void setupStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	/**
	 * Restore the standard io streams.
	 */
	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	/**
	 * Test translation bundles work.
	 */
	@Test
	public void testSetLanguage() {
		System.out.println("setLanguage");
		String language = "de";
		I18n.setLanguage(language);
		CowsayCli.showCmdLineHelp();
		Assert.assertTrue("'Kuh' must be there somewhere or it's not German cowsay help", outContent.toString().contains(" Kuh "));
	}

	/**
	 * Test fall back to default lang.
	 */
	@Test
	public void testGetMessageDefault() {
		System.out.println("getMessage");
		String language = "ar";  // pick a language from which there is no bundle
		I18n.setLanguage(language);
		Assert.assertTrue("'cow' must be there somewhere", I18n.getMessage("e").contains("cow"));
	}

	/**
	 * Test translation bundles work.
	 */
	@Test
	public void testGetMessage() {
		System.out.println("getMessage");
		String language = "de";
		I18n.setLanguage(language);
		Assert.assertTrue("'Kuh' must be there somewhere or it's not German", I18n.getMessage("e").contains("Kuh"));
	}
}
