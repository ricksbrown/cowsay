package com.github.ricksbrown.cowsay;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Rick Brown
 */
public class CowloaderTest {

	/**
	 * Test of listAllCowfiles method, of class Cowloader.
	 */
	@Test
	public void testList() {
		File[] expResult = CowsayTest.getCowjarCowFiles("cowjar");
		String[] result = Cowloader.listAllCowfiles();
		Assert.assertEquals(expResult.length, result.length);
	}

}
