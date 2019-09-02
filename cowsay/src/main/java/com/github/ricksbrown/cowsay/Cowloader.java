package com.github.ricksbrown.cowsay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads a cow either from:
 *	a provided relative path,
 *  a provided absolute path,
 *	or a named cowfile found on the COWPATH environment variable,
 *	or a named cowfile found in the bundled cowfiles
 *	or the default cowfile.
 *
 * The cow will not be formatted or parsed, it will be the raw content loaded from the filesystem
 *    (though newlines will be replaced to platform default).
 * @author Rick Brown
 */
public final class Cowloader {
	/**
	 * The file extension of a cowfile.
	 */
	public static final String COWFILE_EXT = ".cow";

	/**
	 * The name of the default cowfile to use.
	 */
	public static final String DEFAULT_COW = "default";

	/**
	 * Used for matching file names to determine if they are cowfiles.
	 */
	private static final String COWFILE_EXT_RE = "\\" + COWFILE_EXT + "$";

	/**
	 * The logger for this class.
	 */
	private static final Logger LOGGER = Logger.getLogger(Cowloader.class.getName());

	/**
	 * Used to read environment variables.
	 */
	private static Environment cowEnvironment = CowEnvironment.getInstance();

	/**
	 * Utility classes do not need constructors.
	 */
	private Cowloader() {

	}

	/**
	 * Call this with the provided cowfileSpec - that is the value passed to `-f` on the commandline.
	 *
	 * @param cowfileSpec If cowfileSpec contains a filepath separator it is interpreted as relative to CWD
	 *    otherwise the COWPATH will be searched. If not found on the COWPATH we will search for a bundled cowfile.
	 * @return The content of the specified cowfile (or default cowfile if cowfileSpec is null or empty).
	 */
	public static String load(final String cowfileSpec) {
		String effectiveCowfileSpec = (cowfileSpec != null) ? cowfileSpec.trim() : DEFAULT_COW;
		if (effectiveCowfileSpec.length() > 0) {
			if (!effectiveCowfileSpec.endsWith(COWFILE_EXT)) {
				effectiveCowfileSpec += COWFILE_EXT;
			}
			InputStream cowInputStream;
			if (effectiveCowfileSpec.indexOf(File.separatorChar) >= 0) {
				cowInputStream = getCowFromPath(effectiveCowfileSpec);
			} else {
				cowInputStream = getCowFromCowPath(effectiveCowfileSpec);
			}
			if (cowInputStream == null) {
				// Maybe there should be a verbose mode where we log this sort of error instead of silently failing?
				cowInputStream = getCowFromResources(DEFAULT_COW + COWFILE_EXT);
			}
			if (cowInputStream != null) {
				String cow = cowInputStreamToString(cowInputStream);
				return cow;
			}
		}
		return null;  // should never happen
	}

	/**
	 * Set the Environment instance used to look up environment variables.
	 * This is primarily to facilitate unit testing.
	 * @param env The environment variable lookup class.
	 */
	public static void setCowEnvironment(final Environment env) {
		cowEnvironment = env;
	}

	/**
	 * Reads a cowfile from an InputStream and returns a string.
	 * @param cowInputStream The InputStream instance to read into a String.
	 * @return A String representing the result of reading the entire InputStream.
	 */
	private static String cowInputStreamToString(final InputStream cowInputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(cowInputStream, StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			String newLine = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append(newLine);
			}
			reader.close();
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		} finally {
			if (cowInputStream != null) {
				try {
					cowInputStream.close();
				} catch (IOException ex) {
					LOGGER.log(Level.SEVERE, null, ex);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * In the case that the cowfileSpec is a filesystem path call this method to attempt to load the cowfile.
	 * @param path A path to a cowfile either relative to CWD or an absolute path.
	 * @return An InputStream to the cowfile if it exists.
	 */
	private static InputStream getCowFromPath(final String path) {
		File cowfile = new File(path);
		if (cowfile.isAbsolute()) {
			if (isCowfile(cowfile)) {
				return cowfileToCowInputStream(cowfile);
			}
		}
		String cwd = System.getProperty("user.dir");  // TODO is this really CWD?
		if (cwd != null) {
			cowfile = new File(cwd, path);
			if (isCowfile(cowfile)) {
				return cowfileToCowInputStream(cowfile);
			}
		}
		return null;
	}

	/**
	 * This will attempt to load a cowfile, by name, from the COWPATH environment variable or bundled cowfiles.
	 * Note that bundled cowfiles are considered part of the COWPATH since this is how to original `cowsay` worked.
	 * COWPATH takes precedence and bundled cowfiles are only considered after searching the COWPATH.
	 *
	 * @param cowName The name of a cowfile, e.g. "sheep" or "sheep.cow".
	 * @return An InputStream to the first matching cowfile found.
	 */
	private static InputStream getCowFromCowPath(final String cowName) {
		String cowPath = cowEnvironment.getVariable("COWPATH");
		if (cowPath != null) {
			String[] paths = cowPath.split(File.pathSeparator);
			if (paths != null) {
				for (String path : paths) {
					File cowfile = getCowfile(path, cowName);
					if (cowfile != null) {
						return cowfileToCowInputStream(cowfile);
					}
				}
			}
		}
		return getCowFromResources(cowName);
	}

	/**
	 * List the names of all cowfiles found when searching COWPATH (including bundled cowfiles).
	 * Primarily useful for the "-l" commandline flag and also handy for unit testing.
	 * @return The names of available cowfiles.
	 */
	public static String[] listAllCowfiles() {
		return listAllCowfiles(Cowloader.class.getClassLoader());
	}

	/**
	 * List the names of all cowfiles found when searching COWPATH (including bundled cowfiles).
	 * Primarily useful for the "-l" commandline flag and also handy for unit testing.
	 * @param classloader The classloader to use when searching for cowjars (useful for testing).
	 * @return The names of available cowfiles.
	 */
	public static String[] listAllCowfiles(final ClassLoader classloader) {
		String[] resultAsArray;
		Set<String> result = getBundledCowfiles(classloader);
		String cowPath = cowEnvironment.getVariable("COWPATH");
		if (cowPath != null) {
			String[] paths = cowPath.split(File.pathSeparator);
			if (paths != null) {
				for (String path : paths) {
					File[] cowfiles = getCowFiles(path);
					if (cowfiles != null) {
						for (File cowfile : cowfiles) {
							result.add(cowfile.getName().replaceAll(COWFILE_EXT_RE, ""));
						}
					}
				}
			}
		}
		resultAsArray = result.toArray(new String[result.size()]);
		Arrays.sort(resultAsArray);
		return resultAsArray;
	}

	/**
	 * Determine if this File appears to be a genuine cowfile.
	 * This is not a deep check, more rigor will be applied later.
	 * @param cowfile A potential cowfile.
	 * @return true if this File seems to be a cowfile.
	 */
	private static boolean isCowfile(final File cowfile) {
		if (cowfile != null && cowfile.exists()) {
			return cowfile.getName().endsWith(COWFILE_EXT);
		}
		return false;
	}

	/**
	 * Reads a File to an InputStream.
	 * Not sure why I thought this should be a separate method, I guess it made sense at the time.
	 * @param cowfile The cowfile to read.
	 * @return An InputStream which can be used to read the File.
	 */
	private static InputStream cowfileToCowInputStream(final File cowfile) {
		InputStream cowInputStream = null;
		try {
			cowInputStream = new FileInputStream(cowfile);
		} catch (FileNotFoundException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
		return cowInputStream;
	}

	/**
	 * Get a cowfile, by name, from the bundled cowfiles.
	 * @param cowName The name of the cowfile to load.
	 * @return An InputStream which can be used to read the cowfile or null if not found.
	 */
	private static InputStream getCowFromResources(final String cowName) {
		return Cowloader.class.getResourceAsStream("/cows/" + cowName);
	}

	/**
	 * Get a cowfile, by name, from the given directory.
	 * @param folder The absolute path to the filesystem folder.
	 * @param cowName The name of the cowfile to load (without the ".cow" extension).
	 * @return A File if the cowfile is found in this directory otherwise null.
	 */
	private static File getCowfile(final String folder, final String cowName) {
		File[] cowfiles = getCowFiles(folder);
		for (File cowfile : cowfiles) {
			if (cowfile.getName().equals(cowName)) {
				return cowfile;
			}
		}
		return null;
	}

	/**
	 * Gets all cowfiles found in the given directory.
	 * @param folder The absolute path to the filesystem folder.
	 * @return A list of all cowfiles found in this directory.
	 */
	private static File[] getCowFiles(final String folder) {
		File dir = new File(folder);
		File[] files;
		files = dir.listFiles((final File dir1, final String name) -> name.endsWith(".cow"));
		return files;
	}

	/**
	 * Get a list of cowfiles bundled in cowjars.
	 * @param classloader The classloader to use to search for cowfiles.
	 * @return The names of bundled cowfiles.
	 */
	private static Set<String> getBundledCowfiles(final ClassLoader classloader) {
		Set<String> result = new HashSet<>();
		try {
			Enumeration<URL> enumeration = classloader.getResources("cows");
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();
				URLConnection connection = url.openConnection();
				if (connection instanceof JarURLConnection) {
					processJAR((JarURLConnection) connection, result);
				} else {
					String filePath = URLDecoder.decode(url.getPath(), "UTF-8");
					String[] fileList = new File(filePath).list();
					if (fileList != null) {
						for (String file : fileList) {
							addCowfile(file, result);
						}
					}
				}
			}
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
		return result;
	}

	/**
	 * Helper for getBundledCowfiles and processJAR, add a cowfile to a Set.
	 * @param cowfile A potential cowfile.
	 * @param result The cowfile will be added to this if it is indeed a cowfile.
	 */
	private static void addCowfile(final String cowfile, final Set<String> result) {
		if (cowfile.endsWith(COWFILE_EXT)) {  // mech-and-cow for example is not a cowfile and should be excluded
			String cowName = cowfile.replaceAll(COWFILE_EXT_RE, "");
			cowName = cowName.replace("cows/", "");
			result.add(cowName);
		}
	}

	/**
	 * Heper for getBundledCowfiles, handles the scenario when cow files are contained in a JAR on the classpath.
	 * @param connection The connection to the JAR on the classpath.
	 * @param result Cowfiles will be added to this.
	 */
	private static void processJAR(final JarURLConnection connection, final Set<String> result) {
		try (JarFile jar = connection.getJarFile()) {
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				String entry = entries.nextElement().getName();
				addCowfile(entry, result);
			}
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}
	}
}
