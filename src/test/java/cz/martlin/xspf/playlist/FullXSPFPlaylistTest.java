package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.util.XSPFException;

class FullXSPFPlaylistTest {

	@Test
	void testLoad() throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.getPlaylist();

		verify(playlist);
	}

	private void verify(XSPFPlaylist playlist) throws XSPFException {
		verifyBasicProperties(playlist);
		// TODO verify links, metas, ...
		// TODO verify tracks
	}

	private void verifyBasicProperties(XSPFPlaylist playlist) throws XSPFException {
		assertEquals("Sample playlist", //
				playlist.getTitle());

		assertEquals("m@rtlin", //
				playlist.getCreator());

		assertEquals("Sample playlist with some testing track.", //
				playlist.getAnnotation());

		assertEquals(URI.create("https://github.com/martlin2cz/jmop/xspf-playlist/sample-info.htm"), //
				playlist.getInfo());

		assertEquals(URI.create(
				"https://github.com/martlin2cz/jmop/xspf-playlist/scr/test/resources/cz/martlin/xspf/playlist/full.xspf"), //
				playlist.getLocation());

		assertEquals(URI.create("sample.playist"), //
				playlist.getIdentifier());

		assertEquals(URI.create("https://github.com/martlin2cz/jmop/xspf-playlist/sample-image.png"), //
				playlist.getImage());

		assertEquals(LocalDateTime.of(2020, 02, 11, 23, 24, 32), //
				playlist.getDate());

		assertEquals(URI.create("https://github.com/martlin2cz/jmop/xspf-playlist/sample-licence.txt"), //
				playlist.getLicense());
	}

	@Test
	void testCreate() throws XSPFException {
		XSPFFile file = XSPFFile.create();
		XSPFPlaylist playlist = file.getPlaylist();

		fill(playlist);

		verify(playlist);
	}

	private void fill(XSPFPlaylist playlist) throws XSPFException {
		fillBasicProperties(playlist);

	}

	private void fillBasicProperties(XSPFPlaylist playlist) throws XSPFException {
		playlist.setTitle("Sample playlist");

		playlist.setCreator("m@rtlin");

		playlist.setAnnotation("Sample playlist with some testing track.");

		playlist.setInfo(URI.create("https://github.com/martlin2cz/jmop/xspf-playlist/sample-info.htm"));

		playlist.setLocation(URI.create(
				"https://github.com/martlin2cz/jmop/xspf-playlist/scr/test/resources/cz/martlin/xspf/playlist/full.xspf"));

		playlist.setIdentifier(URI.create("sample.playist"));

		playlist.setImage(URI.create("https://github.com/martlin2cz/jmop/xspf-playlist/sample-image.png"));

		playlist.setDate(LocalDateTime.of(2020, 02, 11, 23, 24, 32));

		playlist.setLicense(URI.create("https://github.com/martlin2cz/jmop/xspf-playlist/sample-licence.txt"));
	}

}
