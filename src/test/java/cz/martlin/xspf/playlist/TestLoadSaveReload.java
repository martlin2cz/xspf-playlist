package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import cz.martlin.xspf.util.XSPFException;

public class TestLoadSaveReload {

	@ParameterizedTest
	@ValueSource(strings = { //
			"full.xspf", //
			"minimal.xspf" //
	})
	public void testIt(String fileName) throws XSPFException {
		System.out.println("== Testing " + fileName + " ==");

		File fileToRead = TestingXSPFFiles.fileToReadAssumed(fileName);
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.getPlaylist();

		File fileToWrite = TestingXSPFFiles.fileToWriteAssumed(fileName);
		file.save(fileToWrite);

		XSPFFile reloadedFile = XSPFFile.load(fileToWrite);
		XSPFPlaylist reloadedPlaylist = reloadedFile.getPlaylist();

		assertEquals(playlist.toString(), reloadedPlaylist.toString());
		// TODO equals
		System.out.println("== Tested " + fileName + " ==");
	}

}
