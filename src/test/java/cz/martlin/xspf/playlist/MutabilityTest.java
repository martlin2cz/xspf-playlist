package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.collections.XSPFLinks;
import cz.martlin.xspf.playlist.collections.XSPFMetas;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFLink;
import cz.martlin.xspf.playlist.elements.XSPFMeta;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.Printer;
import cz.martlin.xspf.util.TestingFiles;
import cz.martlin.xspf.util.XSPFException;

/**
 * Tests advanced modifications of the playlist and its components.
 * @author martin
 *
 */
public class MutabilityTest {

	/**
	 * Basically obtains the playlist from the file, updates its title and sets back to the file.
	 * @throws XSPFException
	 */
	@Test
	void testBasicPlaylist() throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		System.out.println(file);

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

	/**
	 * By both ways verifies the modification of one playlist meta.
	 * @throws XSPFException
	 */
	@Test
	void testModifyMeta() throws XSPFException {
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

		assertIterableEquals(Arrays.asList(newMeta), newMetas.iterate());
		System.out.println(newMetas);
		
		playlist.setMetas(newMetas);

		XSPFMetas reMetasView = playlist.metas();
		XSPFMeta reMetaView = reMetasView.list().findFirst().get();
		XSPFMetas reNewMetas = playlist.getMetas();
		XSPFMeta reNewMeta = reNewMetas.list().findFirst().get();
		assertEquals("Yet more meta", reMetaView.getContent());
		assertEquals("Yet more meta", reNewMeta.getContent());

		assertIterableEquals(Arrays.asList(reNewMeta), reNewMetas.iterate());
		System.out.println(reNewMetas);

		Printer.print(0, "With modified metas", file);
	}

	/**
	 * Tries to add and remove links.
	 * @throws XSPFException
	 */
	@Test
	void testModifyLinks() throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.playlist();

		XSPFLinks linksView = playlist.links();
		XSPFLink originalLink = linksView.list().findFirst().get();
		XSPFLink link = linksView.createLink(URI.create("Hello"), URI.create("world"));

		linksView.add(link);
		System.out.println(linksView);
		assertIterableEquals(Arrays.asList(originalLink, link), linksView.iterate());

		XSPFLinks linksMod = playlist.getLinks();
		XSPFLink originalLinkMod = linksMod.list().findFirst().get();
		linksMod.remove(originalLinkMod);
		System.out.println(linksMod);
		assertIterableEquals(Arrays.asList(link), linksMod.iterate());

		assertIterableEquals(Arrays.asList(originalLink, link), linksView.iterate());
		playlist.setLinks(linksMod);
		assertIterableEquals(Arrays.asList(link), linksView.iterate());

		Printer.print(0, "With modified links", file);
	}

	/**
	 * Manipulates the extensions by view.
	 * @throws XSPFException
	 */
	@Test
	void testExtensionsMutable() throws XSPFException {
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

	/**
	 * Manipulates the extensions by copy (get+set).
	 * @throws XSPFException
	 */
	@Test
	void testExtensionsGetSet() throws XSPFException {
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

	/**
	 * Creates brand new list of tracks and sets to the playlist.
	 * @throws XSPFException
	 */
	@Test
	void testBuildTracks() throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.playlist();

		XSPFTracks tracks = file.newTracks();
		assertEquals(0, tracks.list().count());

		XSPFTrack foo = tracks.createTrack(URI.create("htt://foo"));
		tracks.add(foo);
		assertIterableEquals(Arrays.asList(foo), tracks.iterate());

		XSPFTrack bar = tracks.createTrack(URI.create("htt://bar"), "Bar");
		tracks.add(bar);
		assertIterableEquals(Arrays.asList(foo, bar), tracks.iterate());

		XSPFTrack baz = tracks.createTrack(URI.create("htt://baz"), "foobar", "The foo", //
				"Baz", 2, Duration.ofMinutes(4));
		tracks.add(baz);
		assertIterableEquals(Arrays.asList(foo, bar, baz), tracks.iterate());
		System.out.println(tracks);

		XSPFTrack refoo = tracks.list().findFirst().get();
		tracks.remove(refoo);
		assertIterableEquals(Arrays.asList(bar, baz), tracks.iterate());

		playlist.setTracks(tracks);
		assertIterableEquals(Arrays.asList(bar, baz), playlist.tracks().iterate());

		Printer.print(0, "With tracks modified", file);
	}
	
	//TODO test attribution
}
