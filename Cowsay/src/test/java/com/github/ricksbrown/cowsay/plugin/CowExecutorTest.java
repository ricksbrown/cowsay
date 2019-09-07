package com.github.ricksbrown.cowsay.plugin;

import com.github.ricksbrown.cowsay.CowsayTest;
import com.github.ricksbrown.cowsay.TestUtils;
import java.util.Arrays;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Rick Brown
 */
public class CowExecutorTest {

	@BeforeClass
	public static void setUpClass() {
		CowsayTest.setUpClass();
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
		String expResult = TestUtils.loadExpected("cowsayEyes.txt");
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
		String expResult = TestUtils.loadExpected("cowsayTux.txt");
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
		String expResult = TestUtils.loadExpected("cowthinkHello.txt");
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
		String expResult = TestUtils.loadExpected("cowsayTongue.txt");
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
		String expResult = TestUtils.loadExpected("cowsayWrap2.txt");
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
		String expResult = TestUtils.loadExpected("cowsayHello.txt");
		Assert.assertEquals(expResult, result);
	}

	/**
	 * Test of setMode method, of class CowExecutor.
	 */
	@Test
	public void testSetMode() {
		CowExecutor instance = new CowExecutor();
		instance.setMessage("Hello");
		Set<String> modes = CowsayTest.modeMap.keySet();
		for (String key : modes) {
			System.out.println("setMode " + key);
			String expResult = TestUtils.loadExpected(CowsayTest.modeMap.get(key));
			instance.setMode(key);
			String result = instance.execute();
			Assert.assertEquals(expResult, result);
		}
	}

	/**
	 * Test of setMode method, of class CowExecutor, with unknown mode.
	 */
	@Test
	public void testSetModeUnknown() {
		CowExecutor instance = new CowExecutor();
		instance.setMessage("Hello");
		String mode = "A";
		System.out.println("setMode " + mode);
		instance.setMode(mode);
		String[] args = instance.buildArgs();
		Assert.assertThat(Arrays.asList(args), CoreMatchers.not(CoreMatchers.hasItems("-" + mode)));
	}

	/**
	 * Check that null message throws Exception.
	 * @throws Exception
	 */
	@Test(expected = IllegalStateException.class)
	public void testExecuteNullMessage() throws Exception {
		System.out.println("testExecute null message");
		CowExecutor instance = new CowExecutor();
		instance.execute();
	}

	/**
	 * Test of setLang method, of class CowExecutor.
	 */
	@Test
	public void testSetLang() {
		System.out.println("setLang");
		String lang = "de";
		CowExecutor instance = new CowExecutor();
		instance.setLang(lang);
		String[] args = instance.buildArgs();
		Assert.assertThat(Arrays.asList(args), CoreMatchers.hasItems("--lang", lang));
	}

	/**
	 * Test of setAlt method, of class CowExecutor.
	 */
	@Test
	public void testSetAlt() {
		System.out.println("setAlt");
		String alt = "A cow saying 'alt text in cowsay, seriously?'";
		CowExecutor instance = new CowExecutor();
		instance.setAlt(alt);
		String[] args = instance.buildArgs();
		Assert.assertThat(Arrays.asList(args), CoreMatchers.hasItems("--alt", alt));
	}

	/**
	 * Test of setHtml method, of class CowExecutor.
	 */
	@Test
	public void testSetHtml() {
		System.out.println("setHtml");
		CowExecutor instance = new CowExecutor();
		instance.setHtml(true);
		String[] args = instance.buildArgs();
		Assert.assertThat(Arrays.asList(args), CoreMatchers.hasItems("--html"));
	}
}
