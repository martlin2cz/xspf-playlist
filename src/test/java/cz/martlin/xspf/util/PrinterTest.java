package cz.martlin.xspf.util;

import java.io.File;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cz.martlin.xspf.playlist.elements.XSPFFile;

/**
 * Just the test of the {@link Printer}.
 * 
 * @author martin
 *
 */
public class PrinterTest {

	/**
	 * Loads and prints the testing file.
	 * 
	 * @param name
	 * @throws XSPFException
	 */
	@ParameterizedTest
	@ValueSource(strings = { //
			"full.xspf", //
			"minimal.xspf", //
			"lorem-ipsum.xspf", //
			"50-npc-tracks.xspf", })
	public void test(String name) throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", name);
		XSPFFile file = XSPFFile.load(fileToRead);

		Printer.print(1, "The " + name + " playlist file", file);
	}

}
