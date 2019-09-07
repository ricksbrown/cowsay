package com.github.ricksbrown.cowsay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Rick Brown
 */
public class CowsayTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;


	/**
	 * Maps mode switches to test resources containing expected output.
	 */
	public static Map<String, String> modeMap;

	@BeforeClass
	public static void setUpClass() {
		modeMap = new HashMap<>();
		modeMap.put("b", "cowsayBorg.txt");
		modeMap.put("d", "cowsayDead.txt");
		modeMap.put("g", "cowsayGreedy.txt");
		modeMap.put("p", "cowsayParanoid.txt");
		modeMap.put("s", "cowsayStoned.txt");
		modeMap.put("t", "cowsayTired.txt");
		modeMap.put("w", "cowsayWired.txt");
		modeMap.put("y", "cowsayYoung.txt");
	}

	/**
	 * Test of main method, of class Cowsay.
	 */
	@Test
	public void testMain() {
		System.out.println("cowsay Hello");
		String[] args = new String[]{"Hello"};
		String expResult = TestUtils.loadExpected("cowsayHello.txt") + System.lineSeparator();  // extra linefeed because it's println;
		try {
			System.setOut(new PrintStream(outContent));
			Cowsay.main(args);
			String result = outContent.toString();
			TestUtils.cowfileCompare(expResult, result);
		} finally {
			System.setOut(originalOut);
		}
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSay() {
		System.out.println("cowsay Hello");
		String[] args = new String[]{"Hello"};
		String expResult = TestUtils.loadExpected("cowsayHello.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay, as HTML.
	 */
	@Test
	public void testSayHtml() {
		System.out.println("cowsay --html --alt \"A cow saying 'Hello'\"");
		String[] args = new String[]{"--html", "--alt", "A cow saying 'Hello'", "Hello"};
		String expResult = TestUtils.loadExpected("cowsayHelloHtml.html");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay, with -list arg.
	 */
	@Test
	public void testListCows() {
		System.out.println("cowsay -list");
		String[] args = new String[]{"-list"};
		String[] expected = TestUtils.getCowjarCowNames(new String[]{"cowjar"});
		String[] actual = Cowsay.say(args).split(" ");
		Assert.assertArrayEquals(expected, actual);
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSayWhitespaceNormalized() {
		System.out.println("cowsay \"\t\tHello     there  \t\t  \"");
		String[] args = new String[]{"\t\tHello     there  \t\t  "};
		String expResult = TestUtils.loadExpected("cowsayWhitespace.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay, with piped input.
	 */
	@Test
	public void testPipedSay() {
		try {
			System.out.println("echo Hello | cowsay");
			String[] args = new String[0];
			ByteArrayInputStream in = new ByteArrayInputStream("Hello".getBytes());
			System.setIn(in);
			String expResult = TestUtils.loadExpected("cowsayHello.txt");
			String result = Cowsay.say(args);
			TestUtils.cowfileCompare(expResult, result);
		} finally {
			System.setIn(System.in);
		}
	}

	/**
	 * Test of say method, of class Cowsay with both piped input and arg.
	 */
	@Test
	public void testPipedSayTrumpedByArgs() {
		try {
			System.out.println("echo foobar | cowsay Hello");
			String[] args = new String[]{"Hello"};
			String expResult = TestUtils.loadExpected("cowsayHello.txt");
			ByteArrayInputStream in = new ByteArrayInputStream("foobar".getBytes());
			System.setIn(in);
			String result = Cowsay.say(args);
			Assert.assertEquals("Message from args trumps piped input", expResult, result);
		} finally {
			System.setIn(System.in);
		}
	}

	/**
	 * Test of say method, of class Cowsay, with multiple words in multiple args issue #4.
	 */
	@Test
	public void testSayManyWords() {
		System.out.println("cowsay foo bar baz");
		String[] args = new String[]{"foo", "bar", "baz"};
		String expResult = TestUtils.loadExpected("cowsayFooBarBaz.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay, with multiple words in one arg.
	 */
	@Test
	public void testSayManyWordsOneArg() {
		System.out.println("cowsay foo bar baz");
		String[] args = new String[]{"foo bar baz"};
		String expResult = TestUtils.loadExpected("cowsayFooBarBaz.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay, with multiple words piped.
	 */
	@Test
	public void testPipedSayManyWords() {
		try {
			System.out.println("echo foo bar baz | cowsay");
			String expResult = TestUtils.loadExpected("cowsayFooBarBaz.txt");
			String[] args = new String[0];
			ByteArrayInputStream in = new ByteArrayInputStream("foo bar baz".getBytes());
			System.setIn(in);
			String result = Cowsay.say(args);
			TestUtils.cowfileCompare(expResult, result);
		} finally {
			System.setIn(System.in);
		}
	}

	/**
	 * Test non-standard cowthink arg.
	 */
	@Test
	public void testCowthinkArg() {
		System.out.println("cowsay --cowthink Hello");
		String[] args = new String[]{"--cowthink", "Hello"};
		String expResult = TestUtils.loadExpected("cowthinkHello.txt");
		CowsayCli.addCowthinkOption();
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSayWrap2() {
		System.out.println("cowsay -W 2 Hello");
		String[] args = new String[]{"-W", "2", "Hello"};
		String expResult = TestUtils.loadExpected("cowsayWrap2.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSayNowrap() {
		System.out.println("cowsay -n msg");
		String[] args = new String[]{"-n", "Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
		String expResult = TestUtils.loadExpected("cowsayLong.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay.
	 */
	@Test
	public void testSayWrapZero() {
		System.out.println("cowsay -W 0 msg");
		String[] args = new String[]{"-W", "0", "Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
		String expResult = TestUtils.loadExpected("cowsayLong.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with named cow.
	 */
	@Test
	public void testSayWithNamedFile() {
		System.out.println("cowsay -f tux Hello");
		String[] args = new String[]{"-f", "tux", "Hello"};
		String expResult = TestUtils.loadExpected("cowsayTux.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with named cow - not found.
	 */
	@Test
	public void testSayWithNamedFileNotFound() {
		System.out.println("cowsay -f abcdefgzzzblah Hello");
		String[] args = new String[]{"-f", "abcdefgzzzblah", "Hello"};
		String expResult = TestUtils.loadExpected("cowsayHello.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with modes.
	 */
	@Test
	public void testSayModes() {
		Set<String> modes = modeMap.keySet();
		for (String key : modes) {
			String[] args = new String[]{'-' + key, "Hello"};
			System.out.println("cowsay -" + key + " Hello");
			String expResult = TestUtils.loadExpected(modeMap.get(key));
			String result = Cowsay.say(args);
			TestUtils.cowfileCompare(expResult, result);
		}
	}

	/**
	 * Test of say method, of class Cowsay, with piped input and flags.
	 */
	@Test
	public void testSayModesPiped() {
		try {
			Set<String> modes = modeMap.keySet();
			for (String key : modes) {
				String[] args = new String[]{'-' + key};
				System.out.println("echo Hello | cowsay -" + key);
				String expResult = TestUtils.loadExpected(modeMap.get(key));
				ByteArrayInputStream in = new ByteArrayInputStream("Hello".getBytes());
				System.setIn(in);
				String result = Cowsay.say(args);
				TestUtils.cowfileCompare(expResult, result);
			}
		} finally {
			System.setIn(System.in);
		}
	}


	/**
	 * Test of say method, of class Cowsay with custom eyes.
	 */
	@Test
	public void testSayEyes() {
		System.out.println("-e QQ cowsay");
		String[] args = new String[]{"-e", "QQ", "Hello"};
		String expResult = TestUtils.loadExpected("cowsayEyes.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with custom eyes.
	 */
	@Test
	public void testSayEyesDollars() {
		System.out.println("-e $$ cowsay");
		String[] args = new String[]{"-e", "$$", "Hello"};
		String expResult = TestUtils.loadExpected("cowsayGreedy.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with custom eyes.
	 */
	@Test
	public void testSayEyesBackslash() {
		System.out.println("-e \\\\ cowsay");
		String[] args = new String[]{"-e", "\\\\", "Hello"};
		String expResult = TestUtils.loadExpected("cowsayEyesBackslash.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with custom tongue.
	 */
	@Test
	public void testSayTongue() {
		System.out.println("-T V cowsay");
		String[] args = new String[]{"-T", "V", "Hello"};
		String expResult = TestUtils.loadExpected("cowsayTongue.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with custom tongue and eyes.
	 */
	@Test
	public void testSayTongueEyes() {
		System.out.println("cowsay -T V -e QQ Hello");
		String[] args = new String[]{"-T", "V", "-e", "QQ", "Hello"};
		String expResult = TestUtils.loadExpected("cowsayTongueEyes.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with long message.
	 */
	@Test
	public void testSayLongLine() {
		System.out.println("cowsay long");
		String[] args = new String[]{"Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
		String expResult = TestUtils.loadExpected("cowsayMulti.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of think method, of class Cowsay with long message.
	 */
	@Test
	public void testThinkLongLine() {
		System.out.println("cowthink long");
		String[] args = new String[]{"Moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo moo"};
		String expResult = TestUtils.loadExpected("cowthinkMulti.txt");
		String result = Cowsay.think(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with multiline input message.
	 */
	@Test
	public void testSayMultiLineInput() {
		System.out.println("cowsay mutli");
		String[] args = new String[]{"This is a test text."
				+ System.getProperty("line.separator") + "To test long lines in cowsay."
				+ System.getProperty("line.separator") + "Moo moo moo."};
		String expResult = TestUtils.loadExpected("cowsayMultiline.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of say method, of class Cowsay with multiline input message.
	 */
	@Test
	public void testSayMultiLineLongInput() {
		System.out.println("cowsay mutli long");
		String[] args = new String[]{"This is a test text." + System.lineSeparator()
				+ "To test long lines in cowsay." + System.lineSeparator()
				+ "This is a really really really really really long line." + System.lineSeparator()
				+ "Moo moo moo."};
		String expResult = TestUtils.loadExpected("cowsayMultilineLong.txt");
		String result = Cowsay.say(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test of think method, of class Cowsay.
	 */
	@Test
	public void testThink() {
		System.out.println("cowthink Hello");
		String[] args = new String[]{"Hello"};
		String expResult = TestUtils.loadExpected("cowthinkHello.txt");
		String result = Cowsay.think(args);
		TestUtils.cowfileCompare(expResult, result);
	}

	/**
	 * Test output of standard cowfiles except those moved to cowjar-off.
	 * Tests against output from original perl cowsay on ubuntu.
	 */
	@Test
	public void testAllCowfiles() {
		testCowjar("cowjar");
	}

	/**
	 * Test output of all "offensive" cowfiles in cowjar-off.
	 * Tests against output from original perl cowsay on ubuntu.
	 */
	@Test
	public void testOffensiveCowfiles() {
		testCowjar("cowjar-off");
	}

	/**
	 * Test output of extra cowfiles.
	 * Tests against output from original perl cowsay on ubuntu.
	 */
	@Test
	public void testExtraCowfiles() {
		testCowjar("cowjar-extra");
	}

	/**
	 * Test output of cowfiles found in https://github.com/piuccio/cowsay.
	 * Tests against output from original perl cowsay on ubuntu.
	 */
	@Test
	public void testJsCowfiles() {
		testCowjar("cowjar-js");
	}
/**
	 * Tests that the output of java cowsay matches that of original perl cowsay.
	 * @param cowjarName The name of the cowjar which contains the cowfiles to test.
	 */
	private void testCowjar(final String cowjarName) {
		File[] cowfiles = TestUtils.getCowjarCowFiles(cowjarName);
		for (File cowfile : cowfiles) {
			System.out.println("cowsay -f " + cowfile.getAbsolutePath() + " Moo");
			String name = cowfile.getName();
			String[] args = new String[]{"-f", cowfile.getAbsolutePath(), "Moo"};
			String result = Cowsay.say(args);
			String expResult = TestUtils.loadExpected(String.join(File.separator, "cowgen", cowjarName, name + ".txt"));
			TestUtils.cowfileCompare(expResult, result);
		}
	}
}
