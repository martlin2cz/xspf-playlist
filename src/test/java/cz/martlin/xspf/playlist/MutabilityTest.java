package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.util.XSPFException;

public class MutabilityTest {

	
	@Test
	void testBasicPlaylist() throws XSPFException  {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		
		XSPFPlaylist gettedPlaylist = file.getPlaylist();
		XSPFPlaylist playlist = file.playlist();
		
		assertEquals("Sample playlist", gettedPlaylist.getTitle());
		assertEquals("Sample playlist", playlist.getTitle());
		
		gettedPlaylist.setTitle("Another playlist");
		assertEquals("Another playlist", gettedPlaylist.getTitle());
		assertEquals("Sample playlist", playlist.getTitle());
		
		playlist.setTitle("Yet another playlist");
		assertEquals("Another playlist", gettedPlaylist.getTitle());
		assertEquals("Yet another playlist", playlist.getTitle());
		
		XSPFPlaylist regettedPlaylist = file.getPlaylist();
		assertEquals("Yet another playlist", regettedPlaylist.getTitle());
		
		file.setPlaylist(gettedPlaylist);
		playlist = file.playlist();
		assertEquals("Another playlist", playlist.getTitle());
		
	}
}
