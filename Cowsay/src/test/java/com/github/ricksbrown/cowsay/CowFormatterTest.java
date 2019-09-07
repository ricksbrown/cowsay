package com.github.ricksbrown.cowsay;

import org.junit.Test;

/**
 * @author Rick Brown
 */
public class CowFormatterTest {


	/**
	 * Check that invalid cowfile throws CowParseException.
	 * @throws Exception
	 */
	@Test(expected = CowParseException.class)
	public void testFormatCowInvalid() throws Exception {
		System.out.println("formatCow");
		CowFormatter.formatCow("how now brown cow", CowFace.getByMode("b"), new Message("Hello", false));

	}

}
