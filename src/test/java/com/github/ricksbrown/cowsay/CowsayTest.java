/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ricksbrown.cowsay;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
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
public class CowsayTest {

	private static Map<String, String> modeMap;

	@BeforeClass
	public static void setUpClass() {
		modeMap = new HashMap<String, String>();
		modeMap.put("-b", "cowsayBorg.txt");
		modeMap.put("-d", "cowsayDead.txt");
		modeMap.put("-g", "cowsayGreedy.txt");
		modeMap.put("-p", "cowsayParanoid.txt");
		modeMap.put("-s", "cowsayStoned.txt");
		modeMap.put("-t", "cowsayTired.txt");
		modeMap.put("-w", "cowsayWired.txt");
		modeMap.put("-y", "cowsayYoung.txt");
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSay() {
		System.out.println("cowsay Hello");
		String[] args = new String[]{"Hello"};
		String expResult = loadExpected("cowsayHello.txt");
		String result = Cowsay.say(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSayWrap2() {
		System.out.println("cowsay -W 2 Hello");
		String[] args = new String[]{"Hello", "-W", "2"};
		String expResult = loadExpected("cowsayWrap2.txt");
		String result = Cowsay.say(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSayNowrap() {
		System.out.println("cowsay -n msg");
		String[] args = new String[]{"Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo", "-n"};
		String expResult = loadExpected("cowsayLong.txt");
		String result = Cowsay.say(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSayWrapZero() {
		System.out.println("cowsay -W 0 msg");
		String[] args = new String[]{"Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo", "-W", "0"};
		String expResult = loadExpected("cowsayLong.txt");
		String result = Cowsay.say(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with named cow.
	 */
	@Test
	public void testSayWithNamedFile() {
		System.out.println("cowsay -f Tux Hello");
		String[] args = new String[]{"Hello", "-f", "Tux"};
		String expResult = loadExpected("cowsayTux.txt");
		String result = Cowsay.say(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with modes.
	 */
	@Test
	public void testSayModes() {
		Set<String> modes = modeMap.keySet();
		for (String key : modes) {
			String[] args = new String[]{"Hello", key};
			System.out.println("cowsay Hello " + key);
			String expResult = loadExpected(modeMap.get(key));
			String result = Cowsay.say(args);
			Assert.assertEquals(expResult, result);
		}
	}

	/**
	 * Test of say method, of class Cowsay with custom eyes.
	 */
	@Test
	public void testSayEyes() {
		System.out.println("cowsay -e QQ");
		String[] args = new String[]{"Hello", "-e", "QQ"};
		String expResult = loadExpected("cowsayEyes.txt");
		String result = Cowsay.say(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with custom tongue.
	 */
	@Test
	public void testSayTongue() {
		System.out.println("cowsay -T V");
		String[] args = new String[]{"Hello", "-T", "V"};
		String expResult = loadExpected("cowsayTongue.txt");
		String result = Cowsay.say(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with custom tongue and eyes.
	 */
	@Test
	public void testSayTongueEyes() {
		System.out.println("cowsay -T V -e QQ");
		String[] args = new String[]{"Hello", "-T", "V", "-e", "QQ"};
		String expResult = loadExpected("cowsayTongueEyes.txt");
		String result = Cowsay.say(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with long message.
	 */
	@Test
	public void testSayMultiLine() {
		System.out.println("cowsay mutli");
		String[] args = new String[]{"Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
		String expResult = loadExpected("cowsayMulti.txt");
		String result = Cowsay.say(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of think method, of class Cowsay with long message.
	 */
	@Test
	public void testThinkMultiLine() {
		System.out.println("cowthink multi");
		String[] args = new String[]{"Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
		String expResult = loadExpected("cowthinkMulti.txt");
		String result = Cowsay.think(args);
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of think method, of class Cowsay.
	 */
	@Test
	public void testThink() {
		System.out.println("cowthink Hello");
		String[] args = new String[]{"Hello"};
		String expResult = loadExpected("cowthinkHello.txt");
		String result = Cowsay.think(args);
		Assert.assertEquals(expResult, result);
	}

	private static String loadExpected(final String name) {
		try {
			InputStream expected = CowsayTest.class.getResourceAsStream("/" + name);
			return IOUtils.toString(expected, "UTF-8");
		}
		catch (IOException ex) {
			Logger.getLogger(CowsayTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

}
