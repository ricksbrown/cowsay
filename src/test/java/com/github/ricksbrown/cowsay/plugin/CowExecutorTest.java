package com.github.ricksbrown.cowsay.plugin;

import com.github.ricksbrown.cowsay.CowsayTest;
import static com.github.ricksbrown.cowsay.CowsayTest.loadExpected;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Rick Brown
 */
public class CowExecutorTest {

	public static Map<String, String> modeMap;

	public CowExecutorTest() {
	}

	@BeforeClass
	public static void setUpClass() {
		CowsayTest.setUpClass();
		modeMap = CowsayTest.modeMap;
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
	 * Test of setEyes method, of class CowExecutor.
	 */
	@Test
	public void testSetEyes() {
		System.out.println("setEyes");
		String eyes = "QQ";
		CowExecutor instance = new CowExecutor();
		instance.setEyes(eyes);
		String expResult = loadExpected("cowsayEyes.txt");
		instance.setMessage("Hello");
		String result = instance.execute();
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of setCowfile method, of class CowExecutor.
	 */
	@Test
	public void testSetCowfile() {
		System.out.println("setCowfile");
		String cowfile = "tux";
		CowExecutor instance = new CowExecutor();
		instance.setCowfile(cowfile);
		String expResult = loadExpected("cowsayTux.txt");
		instance.setMessage("Hello");
		String result = instance.execute();
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of setThink method, of class CowExecutor.
	 */
	@Test
	public void testSetThink() {
		System.out.println("setThink");
		boolean think = true;
		CowExecutor instance = new CowExecutor();
		instance.setThink(think);
		String expResult = loadExpected("cowthinkHello.txt");
		instance.setMessage("Hello");
		String result = instance.execute();
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of setTongue method, of class CowExecutor.
	 */
	@Test
	public void testSetTongue() {
		System.out.println("setTongue");
		String tongue = "V";
		CowExecutor instance = new CowExecutor();
		instance.setTongue(tongue);
		String expResult = loadExpected("cowsayTongue.txt");
		instance.setMessage("Hello");
		String result = instance.execute();
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of setWrap method, of class CowExecutor.
	 */
	@Test
	public void testSetWrap() {
		System.out.println("setWrap");
		String wrap = "2";
		CowExecutor instance = new CowExecutor();
		instance.setWrap(wrap);
		String expResult = loadExpected("cowsayWrap2.txt");
		instance.setMessage("Hello");
		String result = instance.execute();
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of setMessage method, of class CowExecutor.
	 */
	@Test
	public void testSetMessage() {
		System.out.println("setMessage");
		CowExecutor instance = new CowExecutor();
		instance.setMessage("Hello");
		String result = instance.execute();
		String expResult = loadExpected("cowsayHello.txt");
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of setMode method, of class CowExecutor.
	 */
	@Test
	public void testSetMode() {
		CowExecutor instance = new CowExecutor();
		instance.setMessage("Hello");
		Set<String> modes = modeMap.keySet();
		for (String key : modes) {
			String expResult = CowsayTest.loadExpected(modeMap.get(key));
			instance.setMode(key);
			System.out.println("setMode " + key);
			String result = instance.execute();
			Assert.assertEquals(expResult, result);
		}
	}

}
