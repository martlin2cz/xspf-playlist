package cz.martlin.xspf.util;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Just an testing utility responsible for access to the testing files. Note:
 * There is difference between file to read, which is directly path to the
 * actual resource file (obviously, you don't want to override directly the
 * source testing data), and to write, which is just prepared empty unique
 * temporary file.
 * 
 * @author martin
 *
 */
public class TestingFiles {

	/**
	 * Prepares file to read and assumes it didn't fail.
	 * 
	 * @param pkg  the testing file package
	 * @param name the testing file name
	 * @return
	 */
	public static File fileToReadAssumed(String pkg, String name) {
		try {
			return fileToRead(pkg, name);
		} catch (Exception e) {
			assumeTrue(false, e.toString());
			return null;
		}
	}

	/**
	 * Prepares testing file to read.
	 * 
	 * @param pkg  the testing file package
	 * @param name the testing file name
	 * @return
	 * @throws Exception
	 */
	public static File fileToRead(String pkg, String name) throws Exception {
		String path = "cz/martlin/xspf/" + pkg + "/" + name;
		URL url = TestingFiles.class.getClassLoader().getResource(path);
		Objects.requireNonNull(url, "The resource " + path + " does not exist");

		File file;
		try {
			file = Paths.get(url.toURI()).toFile();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("The uri is invalid", e);
		}

		if (!file.exists()) {
			throw new FileNotFoundException("The file " + path + " does not exist");
		}

		System.out.println("File to read ready: " + file);
		return file;
	}

	/**
	 * Prepares file to write and assumes it didn't fail.
	 * 
	 * @param name the testing file name (kind of)
	 * @return
	 */
	public static File fileToWriteAssumed(String name) {
		try {
			return fileToWrite(name);
		} catch (Exception e) {
			assumeTrue(false, e.toString());
			return null;
		}
	}

	/**
	 * Prepares testing file to write.
	 * 
	 * @param name the testing file name (kind of)
	 * @return
	 * @throws Exception
	 */
	public static File fileToWrite(String name) throws Exception {
		try {
			File file = File.createTempFile("playlist-", "-" + name);

			System.out.println("File to write ready: " + file);
			return file;
		} catch (IOException e) {
			throw new IOException("The file could not be prepared", e);
		}
	}

}
