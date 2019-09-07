package com.github.ricksbrown.cowsay.ant;

import com.github.ricksbrown.cowsay.TestUtils;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.junit.Test;

/**
 * Tests for the Ant task.
 * Honestly nobody is ever going to use this BUT it's essentially free to make an Ant task when you
 * create a Maven mojo so why not...
 * 
 * @author Rick Brown
 */
public class CowsayTaskTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	/**
	 * Test that the Ant task sets a project property.
	 */
	@Test
	public void testSetProperty() {
		System.out.println("setProperty");
		String property = "moo.cow";
		CowsayTask instance = new CowsayTask();
		Project project = new Project();
		instance.setProject(project);
		instance.setProperty(property);
		instance.setMessage("Hello");
		instance.execute();
		String actual = project.getProperty("moo.cow");
		String expResult = TestUtils.loadExpected("cowsayHello.txt");
		TestUtils.cowfileCompare(expResult, actual);
	}

	/**
	 * Test that Ant pipes to stdout when property not set.
	 */
	@Test
	public void testAntTaskStdOut() {
		System.out.println("Ant task stdout");
		String expResult = TestUtils.loadExpected("cowsayHello.txt") + System.lineSeparator();  // extra linefeed because it's println;
		try {
			CowsayTask instance = new CowsayTask();
			Project project = new Project();
			instance.setProject(project);
			instance.setMessage("Hello");
			System.setOut(new PrintStream(outContent));
			instance.execute();
			String actual = outContent.toString();
			TestUtils.cowfileCompare(expResult, actual);
		} finally {
			System.setOut(originalOut);
		}
	}

	/**
	 * Test addText of Ant task.
	 */
	@Test
	public void testAntAddText() {
		System.out.println("Ant task addText");
		String expResult = TestUtils.loadExpected("cowsayHello.txt") + System.lineSeparator();  // extra linefeed because it's println;
		try {
			CowsayTask instance = new CowsayTask();
			Project project = new Project();
			instance.setProject(project);
			instance.addText("Hello");
			System.setOut(new PrintStream(outContent));
			instance.execute();
			String actual = outContent.toString();
			TestUtils.cowfileCompare(expResult, actual);
		} finally {
			System.setOut(originalOut);
		}
	}

	/**
	 * Test that the Ant task sets named cowfile.
	 */
	@Test
	public void testSetCowfile() {
		System.out.println("setCowfile");
		String property = "moo.cow";
		CowsayTask instance = new CowsayTask();
		Project project = new Project();
		instance.setProject(project);
		instance.setProperty(property);
		instance.setMessage("Hello");
		instance.setCowfile("tux");
		instance.execute();
		String actual = project.getProperty("moo.cow");
		String expResult = TestUtils.loadExpected("cowsayTux.txt");
		TestUtils.cowfileCompare(expResult, actual);
	}

	/**
	 * Test that the Ant task sets named cowfile.
	 */
	@Test
	public void testSetThink() {
		System.out.println("setThink");
		String property = "moo.cow";
		CowsayTask instance = new CowsayTask();
		Project project = new Project();
		instance.setProject(project);
		instance.setProperty(property);
		instance.setMessage("Hello");
		instance.setThink(true);
		instance.execute();
		String actual = project.getProperty("moo.cow");
		String expResult = TestUtils.loadExpected("cowthinkHello.txt");
		TestUtils.cowfileCompare(expResult, actual);
	}

	/**
	 * Test that the Ant task sets named cowfile.
	 */
	@Test
	public void testSetTongue() {
		System.out.println("setTongue");
		String property = "moo.cow";
		CowsayTask instance = new CowsayTask();
		Project project = new Project();
		instance.setProject(project);
		instance.setProperty(property);
		instance.setMessage("Hello");
		instance.setTongue("V");
		instance.execute();
		String actual = project.getProperty("moo.cow");
		String expResult = TestUtils.loadExpected("cowsayTongue.txt");
		TestUtils.cowfileCompare(expResult, actual);
	}

	/**
	 * Test that the Ant task sets a wrap.
	 */
	@Test
	public void testSetWrap() {
		System.out.println("setWrap");
		String property = "moo.cow";
		CowsayTask instance = new CowsayTask();
		Project project = new Project();
		instance.setProject(project);
		instance.setProperty(property);
		instance.setMessage("Hello");
		instance.setWrap("2");
		instance.execute();
		String actual = project.getProperty("moo.cow");
		String expResult = TestUtils.loadExpected("cowsayWrap2.txt");
		TestUtils.cowfileCompare(expResult, actual);
	}

	/**
	 * Test that the Ant task sets a the mode.
	 */
	@Test
	public void testSetMode() {
		System.out.println("setMode");
		String property = "moo.cow";
		CowsayTask instance = new CowsayTask();
		Project project = new Project();
		instance.setProject(project);
		instance.setProperty(property);
		instance.setMode("y");
		instance.setMessage("Hello");
		instance.execute();
		String actual = project.getProperty("moo.cow");
		String expResult = TestUtils.loadExpected("cowsayYoung.txt");
		TestUtils.cowfileCompare(expResult, actual);
	}

	/**
	 * Test Ant task with HTML.
	 * In the entire future history of the universe I think nobody will ever do this.
	 */
	@Test
	public void testSayHtml() {
		System.out.println("setHtml");
		String property = "moo.cow";
		CowsayTask instance = new CowsayTask();
		Project project = new Project();
		instance.setProject(project);
		instance.setProperty(property);
		instance.setHtml(true);
		instance.setLang("en");  // this is kind of meaningless on the Ant task
		instance.setAlt("A cow saying 'Hello'");
		instance.setMessage("Hello");
		instance.execute();
		String actual = project.getProperty("moo.cow");
		String expResult = TestUtils.loadExpected("cowsayHelloHtml.html");
		TestUtils.cowfileCompare(expResult, actual);
	}

	/**
	 * Test that Ant won't execute when required values are not set.
	 */
	@Test(expected = BuildException.class)
	public void testExecute() {
		CowsayTask instance = new CowsayTask();
		Project project = new Project();
		instance.setProject(project);
		instance.execute();
	}
}
