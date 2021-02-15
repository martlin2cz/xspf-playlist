package cz.martlin.xspf.examples;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import cz.martlin.xspf.playlist.TestingFiles;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;

@TestMethodOrder(OrderAnnotation.class)
class TrackAdderRemoverTest {
	private static File testingFile;

	@BeforeAll
	static void setUp() throws Exception {
		testingFile = prepare();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	@Order(1)
	void testAdd() throws Exception {
		assertTracksTitles();
		assertTracksLocations();

		TrackAdderRemover.performAddRemoveAction(testingFile, "add", "title", "Lorem");
		assertTracksTitles("Lorem");
		assertTracksLocations();

		TrackAdderRemover.performAddRemoveAction(testingFile, "add", "location", "file://Ipsum");
		assertTracksTitles("Lorem");
		assertTracksLocations("file://Ipsum");

		TrackAdderRemover.performAddRemoveAction(testingFile, "add", "both", "file://Dolor");
		assertTracksTitles("Lorem", "file://Dolor");
		assertTracksLocations("file://Ipsum", "file://Dolor");
	}

	@Test
	@Order(2)
	void testRemove() throws Exception {
		assertTracksTitles("Lorem", "file://Dolor");
		assertTracksLocations("file://Ipsum", "file://Dolor");
		
		TrackAdderRemover.performAddRemoveAction(testingFile, "remove", "title", "Lorem");
		assertTracksTitles("file://Dolor");
		assertTracksLocations("file://Ipsum", "file://Dolor");

		TrackAdderRemover.performAddRemoveAction(testingFile, "remove", "location", "file://Ipsum");
		assertTracksTitles("file://Dolor");
		assertTracksLocations("file://Dolor");

		TrackAdderRemover.performAddRemoveAction(testingFile, "remove", "both", "file://Dolor");
		assertTracksTitles();
		assertTracksLocations();

	}

///////////////////////////////////////////////////////////////////////////////////////////////////	

	private void assertTracksLocations(String... expectedLocations) throws XSPFException {
		XSPFFile xspf = XSPFFile.load(testingFile);
		XSPFPlaylist playlist = xspf.playlist();
		XSPFTracks tracks = playlist.getTracks();

		for (String expectedLocation : expectedLocations) {
			if (!hasLocation(tracks, expectedLocation)) {
				fail("Missing location: " + expectedLocation);
			}
		}

	}

	private void assertTracksTitles(String... expectedTitles) throws XSPFException {
		XSPFFile xspf = XSPFFile.load(testingFile);
		XSPFPlaylist playlist = xspf.playlist();
		XSPFTracks tracks = playlist.getTracks();

		for (String expectedTitle : expectedTitles) {
			if (!hasTitle(tracks, expectedTitle)) {
				fail("Missing titles: " + expectedTitle);
			}
		}
	}

	private boolean hasLocation(XSPFTracks tracks, String expectedLocation) throws XSPFException {
		URI expectedLocationUri = URI.create(expectedLocation);

		for (XSPFTrack track : tracks.iterate()) {
			try {
				if (track.getLocation().equals(expectedLocationUri)) {
					return true;
				}
			} catch (XSPFException e) {
				continue;
			}
		}
		return false;
	}

	private boolean hasTitle(XSPFTracks tracks, String expectedTitle) throws XSPFException {
		for (XSPFTrack track : tracks.iterate()) {
			try {
				if (track.getTitle().equals(expectedTitle)) {
					return true;
				}
			} catch (XSPFException e) {
				continue;
			}
		}
		return false;
	}

	private static File prepare() throws Exception, XSPFException {
		File inputFile = TestingFiles.fileToRead("playlist", "empty.xspf");
		XSPFFile file = XSPFFile.load(inputFile);

		file.playlist().tracks(); // force to generate the trackList element

		File testingFile = TestingFiles.fileToWrite("empty.xspf");
		file.save(testingFile);
		return testingFile;
	}

}
