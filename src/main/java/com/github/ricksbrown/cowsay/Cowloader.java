package com.github.ricksbrown.cowsay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
 *	or the default cowfile
 * @author Rick Brown
 */
public class Cowloader {
	public static final String COWFILE_EXT = ".cow";
	public static final String DEFAULT_COW = "default";

	public static final String load() {
		return load((String) null);
	}

	/**
	 *
	 * @param cowFileSpec If cowFileSpec contains a filepath separator it is interpreted as relative to CWD
	 *    otherwise the COWPATH will be searched. If not found on the COWPATH we will search for a bundled cowfile.
	 * @return
	 */
	public static String load(final String cowFileSpec) {
		String _cowFileSpec = (cowFileSpec != null) ? cowFileSpec.trim() : DEFAULT_COW;
		if (_cowFileSpec != null && _cowFileSpec.length() > 0) {
			if (!_cowFileSpec.endsWith(COWFILE_EXT)) {
				_cowFileSpec += COWFILE_EXT;
			}
			InputStream cowInputStream;
			if (_cowFileSpec.indexOf(File.separatorChar) >= 0) {
				cowInputStream = getCowFromCwd(_cowFileSpec);
			}
			else {
				cowInputStream = getCowFromCowPath(_cowFileSpec);
			}
			if (cowInputStream != null) {
				String cow = loadCow(cowInputStream);
				return cow;
			}
		}
		return "";
	}

	private static String loadCow(final InputStream cowInputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(cowInputStream));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			String newLine = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append(newLine);
			}
			reader.close();
		}
		catch (IOException ex) {
			Logger.getLogger(Cowloader.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally {
			if (cowInputStream != null) {
				try {
					cowInputStream.close();
				}
				catch (IOException ex) {
					Logger.getLogger(Cowloader.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return sb.toString();
	}

	private static InputStream getCowFromCwd(final String relativePath) {
		String cwd = System.getProperty("user.dir");  // TODO is this really CWD?
		if (cwd != null) {
			File cowFile = new File(cwd, relativePath);
			if (cowFile.exists()) {
				return cowFileToCowInputStream(cowFile);
			}
		}
		// maybe it's an absolute path?
		File cowFile = new File(relativePath);
		if (cowFile.exists()) {
			return cowFileToCowInputStream(cowFile);
		}
		return null;
	}

	private static InputStream getCowFromCowPath(final String cowName) {
		String cowPath = System.getenv("COWPATH");
		if(cowPath != null) {
			String[] paths = cowPath.split(File.pathSeparator);
			if (paths != null) {
				for (String path : paths) {
					File cowFile = getCowFile(path, cowName);
					if (cowFile != null) {
						return cowFileToCowInputStream(cowFile);
					}
				}
			}
		}
		return getCowFromResources(cowName);
	}

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
			}
			catch (IOException ex) {
				Logger.getLogger(Cowloader.class.getName()).log(Level.WARNING, null, ex);
			}
			finally {
				try {
					bundleStream.close();
				}
				catch (IOException ex) {
					Logger.getLogger(Cowloader.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

		Set<String> result = new HashSet<>();
		if (bundled != null) {
			for (String cowfile : bundled) {
				result.add(cowfile.replaceAll(cowfileExtRe, ""));
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

	private static InputStream cowFileToCowInputStream(final File cowFile) {
		InputStream cowInputStream = null;
		try {
			cowInputStream = new FileInputStream(cowFile);
		}
		catch (FileNotFoundException ex) {
			Logger.getLogger(Cowloader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return cowInputStream;
	}

	private static InputStream getCowFromResources(final String cowName) {
		return Cowloader.class.getResourceAsStream("/cows/" + cowName);
	}

	private static File getCowFile(final String folder, final String cowFileName) {
		File[] cowFiles = getCowFiles(folder);
		for (File cowFile : cowFiles) {
			if (cowFile.getName().equals(cowFileName)) {
				return cowFile;
			}
		}
		return null;
	}

	private static File[] getCowFiles(final String folder) {
		File dir = new File(folder);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".cow");
			}
		});
		return files;
	}
}
