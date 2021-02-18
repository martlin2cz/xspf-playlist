package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.util.Printer;
import cz.martlin.xspf.util.TestingFiles;
import cz.martlin.xspf.util.XSPFException;

/**
 * An, kind of, simple, brute-force test. Tries to load existing testing file
 * and then save it back. Nextly, it loads it back and compares with the
 * original one.
 * 
 * @author martin
 *
 */
public class TestLoadSaveReload {

	/**
	 * Loads the testing file and saves elsewhere. Loads again and compares. Theese
	 * files must be same.
	 * 
	 * @param fileName
	 * @throws XSPFException
	 */
	@ParameterizedTest
	@ValueSource(strings = { //
			"full.xspf", //
			"minimal.xspf", //
			"50-npc-tracks.xspf", //
			"lorem-ipsum.xspf" //
	})
	public void testSucessfull(String fileName) throws XSPFException {
		System.out.println("== Testing " + fileName + " ==");

		File fileToRead = TestingFiles.fileToReadAssumed("playlist", fileName);
		XSPFFile file = XSPFFile.load(fileToRead);
		Printer.print(0, fileName, file);

		File fileToWrite = TestingFiles.fileToWriteAssumed(fileName);
		file.save(fileToWrite);

		XSPFFile reloadedFile = XSPFFile.load(fileToWrite);

		assertEquals(file.toString(), reloadedFile.toString());
		assertEquals(file, reloadedFile);
	}

	/**
	 * Tries to load and print the file. Expects to fail for some reason.
	 * 
	 * @param fileName
	 * @throws XSPFException
	 */
	@ParameterizedTest
	@ValueSource(strings = { //
			"empty.xspf", //
			"incorrect-syntax.xspf", //
			"incorrect-attrvals.xspf" //
	})
	public void testFailurars(String fileName) throws XSPFException {
		System.err.println("== Testing " + fileName + " ==");

		File fileToRead = TestingFiles.fileToReadAssumed("playlist", fileName);
		try {
			XSPFFile file = XSPFFile.load(fileToRead);
			Printer.print(0, fileName, file);
			fail("The playlist " + fileName + " should fail");
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
