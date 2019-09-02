package com.github.ricksbrown.cowsay;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

/**
 * Utilities for the unit tests.
 * @author Rick Brown
 */
public class TestUtils {

	/**
	 * Private constructor for utility class.
	 */
	private TestUtils() {

	}

	/**
	 * Standard way of comparing output of cowsay.
	 * @param expected The expected output.
	 * @param actual The actual output.
	 */
	public static void cowfileCompare(final String expected, final String actual) {
		System.out.println("Expected:");
		System.out.println(expected);
		System.out.println("Actual:");
		System.out.println(actual);
		Assert.assertThat(expected, CoreMatchers.is(actual));
	}

	/**
	 * Gets all cowfiles in cowjar.
	 * @param cowjarName The name of the cowjar.
	 * @return The cowfiles.
	 */
	public static File[] getCowjarCowFiles(final String cowjarName) {
		File testResourcesDir = getPathToCows(cowjarName);
		File[] result = testResourcesDir.listFiles((final File dir, final String name) -> name.endsWith(".cow"));
		return result;
	}

	/**
	 * Gets the path to the cows directory of a cowjar project.
	 * @param cowjarName The name of the cowjar in this project.
	 * @return The path to the cows.
	 */
	public static File getPathToCows(final String cowjarName) {
		String path = String.join(File.separator, "..", cowjarName, "src", "main", "resources", "cows");
		File result = new File(path);
		return result;
	}

	/**
	 * Fetches the expected output from resources.
	 * @param name The name of a resource to load.
	 * @return The content of the file.
	 */
	public static String loadExpected(final String name) {
		InputStream expected = CowsayTest.class.getResourceAsStream("/" + name);
		if (expected == null) {
			System.out.println("Could not find " + "/" + name);
			return "";
		}
		return loadExpected(expected);
	}

	/**
	 * Fetches the expected output.
	 * @param expected A stream of the expected result.
	 * @return The content of the file.
	 */
	public static String loadExpected(final InputStream expected) {
		try {
			String result = IOUtils.toString(expected, "UTF-8");
			result = result.replaceAll("\\r?\\n", System.lineSeparator());
			return result;
		} catch (IOException ex) {
			Logger.getLogger(CowsayTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
}
