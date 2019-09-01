package com.github.ricksbrown.cowsay;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
		String[] expected = getCowjarCowNames(new String[]{"cowjar"});
		String[] result = Cowloader.listAllCowfiles();
		Assert.assertArrayEquals(expected, result);
	}

	/**
	 * Test of listAllCowfiles method, of class Cowloader.
	 */
//	@Test
//	public void testListCl() {
//		try {
//			ClassLoader cl = this.getClass().getClassLoader();
//			URLClassLoader classloader;
//			classloader = new URLClassLoader(new URL[]{new File("../cowjar-js/target/cowjar-js-1.1.0-SNAPSHOT.jar").toURI().toURL()}, cl);
//			String[] result = Cowloader.listAllCowfiles(classloader);
//			String[] expected = getCowjarCowNames(new String[]{"cowjar", "cowjar-js"});
//			Assert.assertArrayEquals(expected, result);
//		} catch (MalformedURLException ex) {
//			Logger.getLogger(CowloaderTest.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}

	/**
	 * Gets all of the cowfile names from a cowjar.
	 * @param cowjarNames The name of cowjars to list.
	 * @return List of combined cowfile names (without .cow).
	 */
	public static String[] getCowjarCowNames(final String[] cowjarNames) {
		Set<String> cowfileNames = new HashSet<>();
		for (String cowjarName : cowjarNames) {
			File[] cowfiles = CowsayTest.getCowjarCowFiles(cowjarName);
			for (File cowfile : cowfiles) {
				String name = cowfile.getName().replaceAll("\\.cow$", "");
				cowfileNames.add(name);
			}
		}
		String[] result = cowfileNames.toArray(new String[]{});
		Arrays.sort(result);
		return result;
	}
}
