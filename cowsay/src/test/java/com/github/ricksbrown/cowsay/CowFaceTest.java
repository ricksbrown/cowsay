/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ricksbrown.cowsay;

import java.util.Set;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author prrbcl
 */
public class CowFaceTest {

	@BeforeClass
	public static void setUpClass() {
		CowsayTest.setUpClass();
	}

	/**
	 * Test of isKnownMode method, of class CowFace.
	 */
	@Test
	public void testIsKnownMode() {
		Set<String> modes = CowsayTest.modeMap.keySet();
		for (String key : modes) {
			System.out.println("isKnownMode " + key);
			boolean expResult = true;
			boolean result = CowFace.isKnownMode(key);
			Assert.assertEquals(expResult, result);
		}
	}

	/**
	 * Test of isKnownMode with unknown mode.
	 */
	@Test
	public void testIsKnownModeWithUnknown() {
		boolean expResult = false;
		boolean result = CowFace.isKnownMode("1");
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of isKnownMode with unknown mode.
	 */
	@Test
	public void testGetByMode() {
		Set<String> modes = CowsayTest.modeMap.keySet();
		for (String key : modes) {
			System.out.println("testGetByMode " + key);
			CowFace result = CowFace.getByMode(key);
			Assert.assertNotNull(result);
		}
	}

	/**
	 * Test of isKnownMode with null mode.
	 */
	@Test
	public void testGetByModeWithNull() {
		CowFace result = CowFace.getByMode(null);
		Assert.assertNull(result);
	}

	/**
	 * Test setEyes with valid eyes.
	 */
	@Test
	public void testSetEyes() {
		System.out.println("setEyes");
		CowFace instance = new CowFace();
		String eyes = "GG";
		instance.setEyes(eyes);
		Assert.assertEquals(eyes, instance.getEyes());
	}

	/**
	 * Test setEyes with single eye.
	 */
	@Test
	public void testSetEyesWithSingle() {
		System.out.println("setEyes");
		CowFace instance = new CowFace();
		String eyes = "G";
		instance.setEyes(eyes);
		Assert.assertEquals(eyes, instance.getEyes());
	}

	/**
	 * Test setEyes with too many eyes.
	 */
	@Test
	public void testSetEyesWithTooManyChars() {
		System.out.println("setEyes");
		CowFace instance = new CowFace();
		String eyes = "WWWWW";
		instance.setEyes(eyes);
		Assert.assertEquals("WW", instance.getEyes());
	}

	/**
	 * Test setEyes with null eyes.
	 */
	@Test
	public void testSetEyesWithNull() {
		System.out.println("setEyes");
		CowFace instance = new CowFace();
		String defaultEyes = instance.getEyes();
		instance.setEyes(null);
		Assert.assertEquals("Cow eyes should not be changed when set to null", defaultEyes, instance.getEyes());
	}

	/**
	 * Test setEyes with empty eyes.
	 */
	@Test
	public void testSetEyesWithEmpty() {
		System.out.println("setEyes");
		CowFace instance = new CowFace();
		String defaultEyes = instance.getEyes();
		instance.setEyes("");
		Assert.assertEquals("Cow eyes should not be changed when set to empty string", defaultEyes, instance.getEyes());
	}

	/**
	 * Test setTongue with valid tongue.
	 */
	@Test
	public void testSetTongue() {
		System.out.println("setTongue");
		CowFace instance = new CowFace();
		String tongue = "GG";
		instance.setTongue(tongue);
		Assert.assertEquals(tongue, instance.getTongue());
	}

	/**
	 * Test setTongue with single char.
	 */
	@Test
	public void testSetTongueWithSingle() {
		System.out.println("setTongue");
		CowFace instance = new CowFace();
		String tongue = "G";
		instance.setTongue(tongue);
		Assert.assertEquals(tongue, instance.getTongue());
	}

	/**
	 * Test setTongue with too many tongue.
	 */
	@Test
	public void testSetTongueWithTooManyChars() {
		System.out.println("setTongue");
		CowFace instance = new CowFace();
		String tongue = "WWWWW";
		instance.setTongue(tongue);
		Assert.assertEquals("WW", instance.getTongue());
	}

	/**
	 * Test setTongue with null tongue.
	 */
	@Test
	public void testSetTongueWithNull() {
		System.out.println("setTongue");
		CowFace instance = new CowFace();
		String defaultTongue = instance.getTongue();
		instance.setTongue(null);
		Assert.assertEquals("Cow tongue should not be changed when set to null", defaultTongue, instance.getTongue());
	}

	/**
	 * Test setTongue with empty tongue.
	 */
	@Test
	public void testSetTongueWithEmpty() {
		System.out.println("setTongue");
		CowFace instance = new CowFace();
		String defaultEyes = instance.getTongue();
		instance.setTongue("");
		Assert.assertEquals("Cow tongue should not be changed when set to empty string", defaultEyes, instance.getTongue());
	}
}
