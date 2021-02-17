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
import cz.martlin.xspf.util.XSPFException;

class FullXSPFPlaylistTest {

	//TODO shorten all the URIs !
	
	
	@Test
	void testLoad()throws XSPFException {
		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "full.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.getPlaylist();

		verify(playlist);
	}

	private void verify(XSPFPlaylist playlist)throws XSPFException {
		verifyPlaylistBasicProperties(playlist);
		verifyPlaylistAttribution(playlist.getAttribution());
		verifyPlaylistExtensions(playlist.getExtensions());
		verifyPlaylistLinks(playlist.getLinks());
		verifyPlaylistMetas(playlist.getMetas());
		verifyPlaylistTracks(playlist.getTracks());
	}

	private void verifyPlaylistBasicProperties(XSPFPlaylist playlist)throws XSPFException {
		assertEquals("Sample playlist", //
				playlist.getTitle());

		assertEquals("m@rtlin", //
				playlist.getCreator());

		assertEquals("Sample playlist with some testing track.", //
				playlist.getAnnotation());

		assertEquals(URI.create("https://jmop-xspf/sample-info.htm"), //
				playlist.getInfo());

		assertEquals(URI.create(
				"https://jmop-xspf/scr/test/resources/cz/martlin/xspf/playlist/full.xspf"), //
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

	private void verifyPlaylistAttribution(XSPFAttribution attribution)throws XSPFException {
		List<XSPFAttributionItem> items = attribution.list();
		assertEquals(2, items.size());
		
		assertEquals("location", items.get(0).element);
		assertEquals("https://jmop-xspf/sample-attribution-location.txt", items.get(0).value);
		assertEquals("identifier", items.get(1).element);
		assertEquals("https://jmop-xspf/sample-attribution-identifier.txt", items.get(1).value);
	}

	private void verifyPlaylistExtensions(XSPFExtensions extensions)throws XSPFException {
		XSPFExtension extension = checkSizeAndGetThatOne(extensions);

		assertEquals(URI.create("https://github.com/martlin2cz/jmop/"), //
				extension.getApplication());
	}

	private void verifyPlaylistLinks(XSPFLinks links)throws XSPFException {
		XSPFLink link = checkSizeAndGetThatOne(links);

		assertEquals(URI.create("https://jmop-xspf/sample-link"), //
				link.getRel());

		assertEquals(URI.create("https://jmop-xspf/sample-resource"), //
				link.getContent());
	}

	private void verifyPlaylistMetas(XSPFMetas metas)throws XSPFException {
		XSPFMeta meta = checkSizeAndGetThatOne(metas);

		assertEquals(URI.create("https://jmop-xspf/sample-meta"), //
				meta.getRel());

		assertEquals("Further meta value", //
				meta.getContent());
	}

	private void verifyPlaylistTracks(XSPFTracks tracks)throws XSPFException {
		XSPFTrack track = checkSizeAndGetThatOne(tracks);

		verifyTrackBasicProperties(track);
		verifyTrackExtensions(track.getExtensions());
		verifyTrackLinks(track.getLinks());
		verifyTrackMetas(track.getMetas());
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void verifyTrackBasicProperties(XSPFTrack track)throws XSPFException {

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

	private void verifyTrackExtensions(XSPFExtensions extensions)throws XSPFException {
		XSPFExtension extension = checkSizeAndGetThatOne(extensions);

		assertEquals(URI.create("https://github.com/martlin2cz/jmop/"), //
				extension.getApplication());
	}

	private void verifyTrackLinks(XSPFLinks links)throws XSPFException {
		XSPFLink link = checkSizeAndGetThatOne(links);

		assertEquals(URI.create("https://jmop-xspf/sample-link"), //
				link.getRel());

		assertEquals(URI.create("https://jmop-xspf/sample-resource"), //
				link.getContent());
	}

	private void verifyTrackMetas(XSPFMetas metas)throws XSPFException {
		XSPFMeta meta = checkSizeAndGetThatOne(metas);

		assertEquals(URI.create("https://jmop-xspf/sample-meta"), //
				meta.getRel());

		assertEquals("Further meta value", //
				meta.getContent());
	}

	@Test
	void testCreate()throws XSPFException {
		XSPFFile file = XSPFFile.create();
		XSPFPlaylist playlist = file.getPlaylist();

		fill(playlist);

		verify(playlist);
	}

	private void fill(XSPFPlaylist playlist)throws XSPFException {
		fillPlaylistBasicProperties(playlist);

		playlist.setAttribution(createPlaylistAttribution(playlist));
		playlist.setExtensions(createPlaylistExtensions(playlist));
		playlist.setLinks(createPlaylistLinks(playlist));
		playlist.setMetas(createPlaylistMetas(playlist));
		playlist.setTracks(createPlaylistTracks(playlist));
	}

	private void fillPlaylistBasicProperties(XSPFPlaylist playlist)throws XSPFException {
		playlist.setTitle("Sample playlist");

		playlist.setCreator("m@rtlin");

		playlist.setAnnotation("Sample playlist with some testing track.");

		playlist.setInfo(URI.create("https://jmop-xspf/sample-info.htm"));

		playlist.setLocation(URI.create(
				"https://jmop-xspf/scr/test/resources/cz/martlin/xspf/playlist/full.xspf"));

		playlist.setIdentifier(URI.create("sample.playist"));

		playlist.setImage(URI.create("https://jmop-xspf/sample-image.png"));

		playlist.setDate(LocalDateTime.of(2020, 02, 11, 23, 24, 32));

		playlist.setLicense(URI.create("https://jmop-xspf/sample-licence.txt"));
	}

	private XSPFAttribution createPlaylistAttribution(XSPFPlaylist playlist)throws XSPFException {
		XSPFAttribution attribution = playlist.attribution();
		
		attribution.add("location", "https://jmop-xspf/sample-attribution-location.txt");
		attribution.add("identifier", "https://jmop-xspf/sample-attribution-identifier.txt");
		
		return attribution;
	}

	private XSPFExtensions createPlaylistExtensions(XSPFPlaylist playlist)throws XSPFException {
		XSPFExtensions extensions = playlist.extensions();

		XSPFExtension extension = extensions.createExtension(URI.create("https://github.com/martlin2cz/jmop/"));
		// TODO create extension element
		extensions.add(extension);

		return extensions;
	}

	private XSPFLinks createPlaylistLinks(XSPFPlaylist playlist)throws XSPFException {
		XSPFLinks links = playlist.links();

		XSPFLink link = links.createLink( //
				URI.create("https://jmop-xspf/sample-link"), //
				URI.create("https://jmop-xspf/sample-resource"));

		links.add(link);
		return links;
	}

	private XSPFMetas createPlaylistMetas(XSPFPlaylist playlist)throws XSPFException {
		XSPFMetas metas = playlist.metas();

		XSPFMeta meta = metas.createMeta( //
				URI.create("https://jmop-xspf/sample-meta"), //
				"Further meta value");

		metas.add(meta);
		return metas;
	}

	private XSPFTracks createPlaylistTracks(XSPFPlaylist playlist)throws XSPFException {
		XSPFTracks tracks = playlist.tracks();

		XSPFTrack track = tracks.createNew();

		fillTrackBasicProperties(track);
		track.setExtensions(createTrackExtensions(track));
		track.setLinks(createTrackLinks(track));
		track.setMetas(createTrackMetas(track));

		tracks.add(track);
		return tracks;
	}

	private void fillTrackBasicProperties(XSPFTrack track)throws XSPFException {

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

	private XSPFExtensions createTrackExtensions(XSPFTrack track)throws XSPFException {
		XSPFExtensions extensions = track.extensions();

		XSPFExtension extension = extensions.createExtension(URI.create("https://github.com/martlin2cz/jmop/"));
		// TODO create extension element
		extensions.add(extension);

		return extensions;
	}

	private XSPFLinks createTrackLinks(XSPFTrack track)throws XSPFException {
		XSPFLinks links = track.links();

		XSPFLink link = links.createLink( //
				URI.create("https://jmop-xspf/sample-link"), //
				URI.create("https://jmop-xspf/sample-resource"));

		links.add(link);
		return links;
	}

	private XSPFMetas createTrackMetas(XSPFTrack track)throws XSPFException {
		XSPFMetas metas = track.metas();

		XSPFMeta meta = metas.createMeta( //
				URI.create("https://jmop-xspf/sample-meta"), //
				"Further meta value");

		metas.add(meta);
		return metas;
	}
///////////////////////////////////////////////////////////////////////////////////////////////

	private static <E extends XSPFElement> E checkSizeAndGetThatOne(XSPFCollection<E> collection)throws XSPFException {
		List<E> list = collection.list().collect(Collectors.toList());
		assertEquals(1, list.size(), "There may be exactly one element of that kind");

		return list.get(0);
	}

}
