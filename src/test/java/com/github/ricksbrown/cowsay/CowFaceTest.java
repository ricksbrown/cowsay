/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ricksbrown.cowsay;

import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
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
		boolean expResult = false;
		boolean result = CowFace.isKnownMode("1");
		Assert.assertEquals(expResult, result);
	}

}
