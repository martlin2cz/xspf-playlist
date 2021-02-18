package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.collections.XSPFLinks;
import cz.martlin.xspf.playlist.collections.XSPFMetas;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFAttribution;
import cz.martlin.xspf.playlist.elements.XSPFAttribution.XSPFAttributionItem;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFLink;
import cz.martlin.xspf.playlist.elements.XSPFMeta;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.TestingFiles;
import cz.martlin.xspf.util.XSPFException;

/**
 * Runs the complete test of all the getters and setters of the completed
 * playlist by the specification. The playlist get either loaded and created by
 * code.
 * 
 * @author martin
 *
 */
class FullXSPFPlaylistTest {

	/**
	 * Tests whether the loaded playlist has all the expected properties.
	 * 
	 * @throws XSPFException
	 */
	@Test
	void testLoad() throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.getPlaylist();

		verify(playlist);
	}

	/**
	 * Test whether the created playlist has all the expected properties.
	 * 
	 * @throws XSPFException
	 */
	@Test
	void testCreate() throws XSPFException {
		XSPFFile file = XSPFFile.create();
		XSPFPlaylist playlist = file.getPlaylist();

		fill(playlist);
		verify(playlist);
	}

///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Fills the playlist with the expected properties.
	 * 
	 * @param playlist
	 * @throws XSPFException
	 */
	private void fill(XSPFPlaylist playlist) throws XSPFException {
		fillPlaylistBasicProperties(playlist);

		playlist.setAttribution(createPlaylistAttribution(playlist));
		playlist.setExtensions(createPlaylistExtensions(playlist));
		playlist.setLinks(createPlaylistLinks(playlist));
		playlist.setMetas(createPlaylistMetas(playlist));
		playlist.setTracks(createPlaylistTracks(playlist));
	}

	/**
	 * Fills the playlist with the basic (atomic) properties.
	 * 
	 * @param playlist
	 * @throws XSPFException
	 */
	private void fillPlaylistBasicProperties(XSPFPlaylist playlist) throws XSPFException {
		playlist.setTitle("Sample playlist");

		playlist.setCreator("m@rtlin");

		playlist.setAnnotation("Sample playlist with some testing track.");

		playlist.setInfo(URI.create("https://jmop-xspf/sample-info.htm"));

		playlist.setLocation(URI.create("https://jmop-xspf/scr/test/resources/cz/martlin/xspf/playlist/full.xspf"));

		playlist.setIdentifier(URI.create("sample.playist"));

		playlist.setImage(URI.create("https://jmop-xspf/sample-image.png"));

		playlist.setDate(LocalDateTime.of(2020, 02, 11, 23, 24, 32));

		playlist.setLicense(URI.create("https://jmop-xspf/sample-licence.txt"));
	}

	/**
	 * Creates the expected playlist attribution.
	 * 
	 * @param playlist
	 * @return
	 * @throws XSPFException
	 */
	private XSPFAttribution createPlaylistAttribution(XSPFPlaylist playlist) throws XSPFException {
		XSPFAttribution attribution = playlist.attribution();

		attribution.add("location", "https://jmop-xspf/sample-attribution-location.txt");
		attribution.add("identifier", "https://jmop-xspf/sample-attribution-identifier.txt");

		return attribution;
	}

	/**
	 * Creates the expected playlist extensions.
	 * 
	 * @param playlist
	 * @return
	 * @throws XSPFException
	 */
	private XSPFExtensions createPlaylistExtensions(XSPFPlaylist playlist) throws XSPFException {
		XSPFExtensions extensions = playlist.extensions();

		XSPFExtension extension = extensions.createExtension(URI.create("https://github.com/martlin2cz/jmop/"));
		// TODO create extension element
		extensions.add(extension);

		return extensions;
	}

	/**
	 * Creates the expected playlist links.
	 * 
	 * @param playlist
	 * @return
	 * @throws XSPFException
	 */
	private XSPFLinks createPlaylistLinks(XSPFPlaylist playlist) throws XSPFException {
		XSPFLinks links = playlist.links();

		XSPFLink link = links.createLink( //
				URI.create("https://jmop-xspf/sample-link"), //
				URI.create("https://jmop-xspf/sample-resource"));

		links.add(link);
		return links;
	}

	/**
	 * Creates the expected playlist metas.
	 * 
	 * @param playlist
	 * @return
	 * @throws XSPFException
	 */
	private XSPFMetas createPlaylistMetas(XSPFPlaylist playlist) throws XSPFException {
		XSPFMetas metas = playlist.metas();

		XSPFMeta meta = metas.createMeta( //
				URI.create("https://jmop-xspf/sample-meta"), //
				"Further meta value");

		metas.add(meta);
		return metas;
	}

	/**
	 * Creates the expected tracks.
	 * 
	 * @param playlist
	 * @return
	 * @throws XSPFException
	 */
	private XSPFTracks createPlaylistTracks(XSPFPlaylist playlist) throws XSPFException {
		XSPFTracks tracks = playlist.tracks();

		XSPFTrack track = tracks.createNew();

		fillTrackBasicProperties(track);
		track.setExtensions(createTrackExtensions(track));
		track.setLinks(createTrackLinks(track));
		track.setMetas(createTrackMetas(track));

		tracks.add(track);
		return tracks;
	}

	/**
	 * Fills the tracks basic (atomic) properties.
	 * 
	 * @param track
	 * @throws XSPFException
	 */
	private void fillTrackBasicProperties(XSPFTrack track) throws XSPFException {

		track.setAlbum("Sample album vol. 2");

		track.setAnnotation("This is the sample track.");

		track.setCreator("The sample creator");

		track.setDuration(Duration.ofMillis(360000));

		track.setIdentifier(URI.create("https://jmop-xspf/sample-track.txt"));

		track.setImage(URI.create("https://jmop-xspf/sample-image.png"));

		track.setInfo(URI.create("https://jmop-xspf/sample-info.txt"));

		track.setLocation(URI.create("https://jmop-xspf/sample-track.html"));

		track.setTitle("Sample track");

		track.setTrackNum(1);
	}

	/**
	 * Creates the expected track attribution.
	 * 
	 * @param track
	 * @return
	 * @throws XSPFException
	 */
	private XSPFExtensions createTrackExtensions(XSPFTrack track) throws XSPFException {
		XSPFExtensions extensions = track.extensions();

		XSPFExtension extension = extensions.createExtension(URI.create("https://github.com/martlin2cz/jmop/"));
		// TODO create extension element
		extensions.add(extension);

		return extensions;
	}

	/**
	 * Creates the expected track links.
	 * 
	 * @param track
	 * @return
	 * @throws XSPFException
	 */
	private XSPFLinks createTrackLinks(XSPFTrack track) throws XSPFException {
		XSPFLinks links = track.links();

		XSPFLink link = links.createLink( //
				URI.create("https://jmop-xspf/sample-link"), //
				URI.create("https://jmop-xspf/sample-resource"));

		links.add(link);
		return links;
	}

	/**
	 * Creates the expected track metas.
	 * 
	 * @param track
	 * @return
	 * @throws XSPFException
	 */
	private XSPFMetas createTrackMetas(XSPFTrack track) throws XSPFException {
		XSPFMetas metas = track.metas();

		XSPFMeta meta = metas.createMeta( //
				URI.create("https://jmop-xspf/sample-meta"), //
				"Further meta value");

		metas.add(meta);
		return metas;
	}
///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Verifies the playlist.
	 * 
	 * @param playlist
	 * @throws XSPFException
	 */
	private void verify(XSPFPlaylist playlist) throws XSPFException {
		verifyPlaylistBasicProperties(playlist);
		verifyPlaylistAttribution(playlist.getAttribution());
		verifyPlaylistExtensions(playlist.getExtensions());
		verifyPlaylistLinks(playlist.getLinks());
		verifyPlaylistMetas(playlist.getMetas());
		verifyPlaylistTracks(playlist.getTracks());
	}

	/**
	 * Verifies the playlist has all the basic (atomic) properties matching the
	 * expectations.
	 * 
	 * @param playlist
	 * @throws XSPFException
	 */
	private void verifyPlaylistBasicProperties(XSPFPlaylist playlist) throws XSPFException {
		assertEquals("Sample playlist", //
				playlist.getTitle());

		assertEquals("m@rtlin", //
				playlist.getCreator());

		assertEquals("Sample playlist with some testing track.", //
				playlist.getAnnotation());

		assertEquals(URI.create("https://jmop-xspf/sample-info.htm"), //
				playlist.getInfo());

		assertEquals(URI.create("https://jmop-xspf/scr/test/resources/cz/martlin/xspf/playlist/full.xspf"), //
				playlist.getLocation());

		assertEquals(URI.create("sample.playist"), //
				playlist.getIdentifier());

		assertEquals(URI.create("https://jmop-xspf/sample-image.png"), //
				playlist.getImage());

		assertEquals(LocalDateTime.of(2020, 02, 11, 23, 24, 32), //
				playlist.getDate());

		assertEquals(URI.create("https://jmop-xspf/sample-licence.txt"), //
				playlist.getLicense());
	}

	/**
	 * Verifies the playlist attribution matches the expectations.
	 * 
	 * @param attribution
	 * @throws XSPFException
	 */
	private void verifyPlaylistAttribution(XSPFAttribution attribution) throws XSPFException {
		List<XSPFAttributionItem> items = attribution.list();
		assertEquals(2, items.size());

		assertEquals("location", items.get(0).element);
		assertEquals("https://jmop-xspf/sample-attribution-location.txt", items.get(0).value);
		assertEquals("identifier", items.get(1).element);
		assertEquals("https://jmop-xspf/sample-attribution-identifier.txt", items.get(1).value);
	}

	/**
	 * Verifies the playlist extensions matches the expectations.
	 * 
	 * @param extensions
	 * @throws XSPFException
	 */
	private void verifyPlaylistExtensions(XSPFExtensions extensions) throws XSPFException {
		XSPFExtension extension = checkSizeAndGetThatOne(extensions);

		assertEquals(URI.create("https://github.com/martlin2cz/jmop/"), //
				extension.getApplication());
	}

	/**
	 * Verifies the playlist links matches the expectations.
	 * 
	 * @param links
	 * @throws XSPFException
	 */
	private void verifyPlaylistLinks(XSPFLinks links) throws XSPFException {
		XSPFLink link = checkSizeAndGetThatOne(links);

		assertEquals(URI.create("https://jmop-xspf/sample-link"), //
				link.getRel());

		assertEquals(URI.create("https://jmop-xspf/sample-resource"), //
				link.getContent());
	}

	/**
	 * Verifies the playlist metas matches the expectations.
	 * 
	 * @param metas
	 * @throws XSPFException
	 */
	private void verifyPlaylistMetas(XSPFMetas metas) throws XSPFException {
		XSPFMeta meta = checkSizeAndGetThatOne(metas);

		assertEquals(URI.create("https://jmop-xspf/sample-meta"), //
				meta.getRel());

		assertEquals("Further meta value", //
				meta.getContent());
	}

	/**
	 * Verifies the playlist tracks matches the expectations.
	 * 
	 * @param tracks
	 * @throws XSPFException
	 */
	private void verifyPlaylistTracks(XSPFTracks tracks) throws XSPFException {
		XSPFTrack track = checkSizeAndGetThatOne(tracks);

		verifyTrackBasicProperties(track);
		verifyTrackExtensions(track.getExtensions());
		verifyTrackLinks(track.getLinks());
		verifyTrackMetas(track.getMetas());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Verifies the track basic (atomic) properties matches the expectations.
	 * 
	 * @param track
	 * @throws XSPFException
	 */
	private void verifyTrackBasicProperties(XSPFTrack track) throws XSPFException {

		assertEquals("Sample album vol. 2", //
				track.getAlbum());

		assertEquals("This is the sample track.", //
				track.getAnnotation());

		assertEquals("The sample creator", //
				track.getCreator());

		assertEquals(Duration.ofMillis(360000), //
				track.getDuration());

		assertEquals(URI.create("https://jmop-xspf/sample-track.txt"), //
				track.getIdentifier());

		assertEquals(URI.create("https://jmop-xspf/sample-image.png"), //
				track.getImage());

		assertEquals(URI.create("https://jmop-xspf/sample-info.txt"), //
				track.getInfo());

		assertEquals(URI.create("https://jmop-xspf/sample-track.html"), //
				track.getLocation());

		assertEquals("Sample track", //
				track.getTitle());

		assertEquals(1, //
				track.getTrackNum());
	}

	/**
	 * Verifies the ẗrack extensions matches the expectations.
	 * 
	 * @param extensions
	 * @throws XSPFException
	 */
	private void verifyTrackExtensions(XSPFExtensions extensions) throws XSPFException {
		XSPFExtension extension = checkSizeAndGetThatOne(extensions);

		assertEquals(URI.create("https://github.com/martlin2cz/jmop/"), //
				extension.getApplication());
	}

	/**
	 * Verifies the track links matches the expectations.
	 * 
	 * @param links
	 * @throws XSPFException
	 */
	private void verifyTrackLinks(XSPFLinks links) throws XSPFException {
		XSPFLink link = checkSizeAndGetThatOne(links);

		assertEquals(URI.create("https://jmop-xspf/sample-link"), //
				link.getRel());

		assertEquals(URI.create("https://jmop-xspf/sample-resource"), //
				link.getContent());
	}

	/**
	 * Verifies the track metas matches the expectations.
	 * 
	 * @param metas
	 * @throws XSPFException
	 */
	private void verifyTrackMetas(XSPFMetas metas) throws XSPFException {
		XSPFMeta meta = checkSizeAndGetThatOne(metas);

		assertEquals(URI.create("https://jmop-xspf/sample-meta"), //
				meta.getRel());

		assertEquals("Further meta value", //
				meta.getContent());
	}

///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Checks whether the given collection has exactly one item and if so, returns
	 * it.
	 * 
	 * @param <E>
	 * @param collection
	 * @return
	 * @throws XSPFException
	 */
	private static <E extends XSPFElement> E checkSizeAndGetThatOne(XSPFCollection<E> collection) throws XSPFException {
		List<E> list = collection.list().collect(Collectors.toList());
		assertEquals(1, list.size(), "There may be exactly one element of that kind");

		return list.get(0);
	}

}
