package com.github.ricksbrown.cowsay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
			Logger.getLogger(Cowloader.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (cowInputStream != null) {
				try {
					cowInputStream.close();
				} catch (IOException ex) {
					Logger.getLogger(Cowloader.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * In the case that the cowfileSpec is a filesystem path call this method to attempt to load the cowfile.
	 * It will attempt to load the cowfile relative to CWD and if that fails it will try as an absolute path.
	 * @param path A path to a cowfile either relative to CWD or an absolute path.
	 * @return An InputStream to the cowfile if it exists.
	 */
	private static InputStream getCowFromPath(final String path) {
		String cwd = System.getProperty("user.dir");  // TODO is this really CWD?
		if (cwd != null) {
			File cowfile = new File(cwd, path);
			if (isCowfile(cowfile)) {
				return cowfileToCowInputStream(cowfile);
			}
		}
		// maybe it's an absolute path?
		File cowfile = new File(path);
		if (isCowfile(cowfile)) {
			return cowfileToCowInputStream(cowfile);
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
		String cowPath = System.getenv("COWPATH");
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
		String[] resultAsArray;
		String[] bundled = null;
		String cowfileExtRe = "\\" + COWFILE_EXT + "$";
		InputStream bundleStream = Cowloader.class.getResourceAsStream("/cowfile-list.csv");
		if (bundleStream != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(bundleStream));
			StringBuilder sb = new StringBuilder();
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
				String bundleList = sb.toString();
				bundled = bundleList.split(",");
			} catch (IOException ex) {
				Logger.getLogger(Cowloader.class.getName()).log(Level.WARNING, null, ex);
			} finally {
				try {
					bundleStream.close();
				} catch (IOException ex) {
					Logger.getLogger(Cowloader.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

		Set<String> result = new HashSet<String>();
		if (bundled != null) {
			for (String cowfile : bundled) {
				if (cowfile.endsWith(COWFILE_EXT)) {  // mech-and-cow for example is not a cowfile and should be excluded
					result.add(cowfile.replaceAll(cowfileExtRe, ""));
				}
			}
		}
		String cowPath = System.getenv("COWPATH");
		if (cowPath != null) {
			String[] paths = cowPath.split(File.pathSeparator);
			if (paths != null) {
				for (String path : paths) {
					File[] cowfiles = getCowFiles(path);
					if (cowfiles != null) {
						for (File cowfile : cowfiles) {
							result.add(cowfile.getName().replaceAll(cowfileExtRe, ""));
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
			Logger.getLogger(Cowloader.class.getName()).log(Level.SEVERE, null, ex);
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
		files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				return name.endsWith(".cow");
			}
		});
		return files;
	}
}
