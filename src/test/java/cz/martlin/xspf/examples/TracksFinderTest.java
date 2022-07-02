package cz.martlin.xspf.examples;

import java.io.File;

import org.junit.jupiter.api.Test;

import cz.martlin.xspf.util.TestingFiles;
import cz.martlin.xspf.util.XSPFException;

/**
 * An simple test of the {@link TracksFinder} app.
 * 
 * @author martin
 *
 */
class TracksFinderTest {

	/**
	 * Runs some tests.
	 * 
	 * @throws XSPFException
	 */
	@Test
	void test() throws XSPFException {
		File fullPlaylistFile = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		TracksFinder.findAndPrintTracks(fullPlaylistFile, "Sample");

		File npcTracksFile = TestingFiles.fileToReadAssumed("playlist", "50-npc-tracks.xspf");
		TracksFinder.findAndPrintTracks(npcTracksFile, "feat.");
	}

}
