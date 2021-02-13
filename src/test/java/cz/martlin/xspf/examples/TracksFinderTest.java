package cz.martlin.xspf.examples;

import java.io.File;

import org.junit.jupiter.api.Test;

import cz.martlin.xspf.playlist.TestingFiles;
import cz.martlin.xspf.util.XSPFException;

class TracksFinderTest {

	@Test
	void test() throws XSPFException {
		File fullPlaylistFile = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		TracksFinder.findAndPrintTracks(fullPlaylistFile, "Sample");
		
		File npcTracksFile = TestingFiles.fileToReadAssumed("playlist", "50-npc-tracks.xspf");
		TracksFinder.findAndPrintTracks(npcTracksFile, "feat.");
	}

}
