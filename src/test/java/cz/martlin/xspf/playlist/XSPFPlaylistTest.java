package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import cz.martlin.xspf.util.XSPFException;

/**
 * Replaced by the new, more precise, test classes.
 * @author martin
 *
 */
@Deprecated
class XSPFPlaylistTest {

	@Test
	void testSome() throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "some.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.getPlaylist();
		
		verifyPlaylist(playlist);

		File fileToWrite = TestingFiles.fileToWriteAssumed("some.xspf");
		file.save(fileToWrite);

		XSPFFile reloadedFile = XSPFFile.load(fileToWrite);
		XSPFPlaylist reloadedPlaylist = reloadedFile.getPlaylist();
		verifyPlaylist(reloadedPlaylist);
	}

	@Test
	void testSimplyAll() {
		testPlaylistFile("some.xspf");
		testPlaylistFile("correct.xspf");
		
		assertThrows(AssertionFailedError.class, //
				() -> testPlaylistFile("from-parole.xspf"));
		
		assertThrows(AssertionFailedError.class, //
				() -> testPlaylistFile("from-vlc.xspf"));

		testPlaylistFile("incorrect-attrvals.xspf");
		
		assertThrows(AssertionFailedError.class, //
				() -> testPlaylistFile("incorrect-syntax.xspf"));
	}

	public void testPlaylistFile(String name) {
		try {
			File fileToRead = TestingFiles.fileToReadAssumed("playlist", name);
			XSPFFile file = XSPFFile.load(fileToRead);
			XSPFPlaylist playlist = file.getPlaylist();
			
			verifyPlaylist(playlist);
	
			File fileToWrite = TestingFiles.fileToWriteAssumed(name);
			file.save(fileToWrite);
	
			XSPFFile reloadedFile = XSPFFile.load(fileToWrite);
			XSPFPlaylist reloadedPlaylist = reloadedFile.getPlaylist();
			
			verifyPlaylist(reloadedPlaylist);
		} catch (XSPFException e) {
			fail("The file " + name + " failed.", e);
		}
	}

	private void verifyPlaylist(XSPFPlaylist playlist) throws XSPFException {
		System.out.println(playlist);
		assertEquals("Discovery", playlist.getTitle());
		assertEquals(3, playlist.getTracks().size());

		// TODO test the tracks
	}

}
