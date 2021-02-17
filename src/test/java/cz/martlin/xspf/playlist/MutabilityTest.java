package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URI;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.collections.XSPFMetas;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFMeta;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.util.Printer;
import cz.martlin.xspf.util.XSPFException;

public class MutabilityTest {

	@Test
	void testBasicPlaylist()throws XSPFException {
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
		
		Printer.print(0, "With modified title", file);
	}

	@Test
	void testModifyMeta()throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.playlist();

		XSPFMetas metasView = playlist.metas();
		XSPFMeta metaView = metasView.list().findFirst().get();
		XSPFMetas newMetas = playlist.getMetas();
		XSPFMeta newMeta = newMetas.list().findFirst().get();
		assertEquals("Further meta value", metaView.getContent());
		assertEquals("Further meta value", newMeta.getContent());

		newMeta.setContent("Yet more meta");
		assertEquals("Further meta value", metaView.getContent());
		assertEquals("Yet more meta", newMeta.getContent());
		
		playlist.setMetas(newMetas);
		
		XSPFMetas reMetasView = playlist.metas();
		XSPFMeta reMetaView = reMetasView.list().findFirst().get();
		XSPFMetas reNewMetas = playlist.getMetas();
		XSPFMeta reNewMeta = reNewMetas.list().findFirst().get();
		assertEquals("Yet more meta", reMetaView.getContent());
		assertEquals("Yet more meta", reNewMeta.getContent());
		
		Printer.print(0, "With modified meta", file);
	}
	
	@Test
	void testExtensionsMutable()throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.playlist();

		XSPFExtensions extensions = playlist.extensions();
		XSPFExtension extension = extensions.list().findFirst().get();
		assertIterableEquals(Arrays.asList(extension), extensions.iterate());
		assertEquals(1, playlist.extensions().list().count());
		
		extensions.remove(extension);
		assertIterableEquals(Arrays.asList(), extensions.iterate());
		assertEquals(0, playlist.extensions().list().count());
		
		XSPFExtension newExtension = extensions.createExtension(URI.create("new-ext"));
		extensions.add(newExtension);
		assertIterableEquals(Arrays.asList(newExtension), extensions.iterate());
		assertEquals(1, playlist.extensions().list().count());
		
		Printer.print(0, "With modified extensions by extensions()", file);
	}
	
	@Test
	void testExtensionsGetSet()throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.playlist();

		XSPFExtensions extensions = playlist.getExtensions();
		XSPFExtension extension = extensions.list().findFirst().get();
		assertIterableEquals(Arrays.asList(extension), extensions.iterate());
		assertEquals(1, playlist.extensions().list().count());
		
		extensions.remove(extension);
		assertIterableEquals(Arrays.asList(), extensions.iterate());
		assertEquals(1, playlist.extensions().list().count());
		
		XSPFExtension newExtension = extensions.createExtension(URI.create("new-ext"));
		extensions.add(newExtension);
		assertIterableEquals(Arrays.asList(newExtension), extensions.iterate());
		assertEquals(1, playlist.extensions().list().count());
		
		playlist.setExtensions(extensions);
		assertEquals(1, playlist.extensions().list().count());
		
		Printer.print(0, "With modified extensions by get...set", file);
	}
	
	//TODO test add, remove
}
