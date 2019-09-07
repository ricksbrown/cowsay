package com.github.ricksbrown.cowsay;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for Message, only conditions not covered by the end-to-end testing.
 * @author Rick Brown
 */
public class MessageTest {

	/**
	 * Null should translate to an empty string, no exceptions or anything.
	 */
	@Test
	public void testGetMessageWithNull() {
		System.out.println("getMessage with null message");
		Message instance = new Message(null, false);
		String expResult = "";
		String result = instance.getMessage();
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Check that setting an invalid wordwrap leaves the instance unchanged.
	 */
	@Test
	public void testSetWordwrap() {
		System.out.println("setWordwrap");
		String wordwrap = "80";
		Message instance = new Message("Hello", true);
		instance.setWordwrap(wordwrap);
		instance.setWordwrap("Moo");
		Assert.assertEquals(Integer.parseInt(wordwrap), instance.getWordwrap());
	}
}
