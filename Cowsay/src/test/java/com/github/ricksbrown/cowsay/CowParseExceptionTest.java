package com.github.ricksbrown.cowsay;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for custom exception.
 * Because in such critically important software nothing can be left to chance.
 *
 * @author Rick Brown
 */
public class CowParseExceptionTest {

	/**
	 * Test the custom exception is, well like exceptiony.
	 */
	@Test
	public void testSomeMethod() {
		CowParseException ex = new CowParseException("Ex the first");
		CowParseException cpe = new CowParseException("Stupid unit test", ex);
		Assert.assertEquals(ex, cpe.getCause());
	}
}
