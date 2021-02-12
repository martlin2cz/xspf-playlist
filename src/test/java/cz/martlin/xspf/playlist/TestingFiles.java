package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class TestingFiles {
	
	public static File fileToReadAssumed(String pkg, String name) {
		try {
			return fileToRead(pkg, name);
		} catch (Exception e) {
			assumeTrue(false, e.toString());
			return null;
		}
	}
	
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
			throw new FileNotFoundException("The file " + path  + " does not exist");
		}
		
		System.out.println("File to read ready: " + file);
		return file;
	}
	
	
	public static File fileToWriteAssumed(String name) {
		try {
			return fileToWrite(name);
		} catch (Exception e) {
			assumeTrue(false, e.toString());
			return null;
		}
	}

	public static File fileToWrite(String name) throws Exception {
		try {
			File file = File.createTempFile("playlist-", "-" + name);
			
			System.out.println("File to write ready: " + file);
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileNotFoundException("The file could not be prepared");
		}
	}

}
