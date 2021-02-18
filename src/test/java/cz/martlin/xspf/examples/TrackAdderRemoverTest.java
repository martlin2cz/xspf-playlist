package cz.martlin.xspf.examples;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import cz.martlin.xspf.examples.TrackAdderRemover.Action;
import cz.martlin.xspf.examples.TrackAdderRemover.Specifier;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.TestingFiles;
import cz.martlin.xspf.util.XSPFException;

/**
 * The test of the {@link TrackAdderRemover}.
 * 
 * @author martin
 *
 */
@TestMethodOrder(OrderAnnotation.class)
class TrackAdderRemoverTest {
	/**
	 * The testing playlist file.
	 */
	private static File testingFile;

	@BeforeAll
	static void setUp() throws Exception {
		testingFile = prepare();
	}

///////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Tests Action.ADD features.
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(1)
	void testAdd() throws Exception {
		assertTracksTitles();
		assertTracksLocations();

		TrackAdderRemover.performAction(testingFile, Action.ADD, Specifier.TITLE, "Lorem");
		assertTracksTitles("Lorem");
		assertTracksLocations();

		TrackAdderRemover.performAction(testingFile, Action.ADD, Specifier.LOCATION, "file://Ipsum");
		assertTracksTitles("Lorem");
		assertTracksLocations("file://Ipsum");

		TrackAdderRemover.performAction(testingFile, Action.ADD, Specifier.BOTH, "file://Dolor");
		assertTracksTitles("Lorem", "file://Dolor");
		assertTracksLocations("file://Ipsum", "file://Dolor");
	}

	/**
	 * Tests Action.REMOVE features.
	 * 
	 * @throws Exception
	 */
	@Test
	@Order(2)
	void testRemove() throws Exception {
		assertTracksTitles("Lorem", "file://Dolor");
		assertTracksLocations("file://Ipsum", "file://Dolor");

		TrackAdderRemover.performAction(testingFile, Action.REMOVE, Specifier.TITLE, "Lorem");
		assertTracksTitles("file://Dolor");
		assertTracksLocations("file://Ipsum", "file://Dolor");

		TrackAdderRemover.performAction(testingFile, Action.REMOVE, Specifier.LOCATION, "file://Ipsum");
		assertTracksTitles("file://Dolor");
		assertTracksLocations("file://Dolor");

		TrackAdderRemover.performAction(testingFile, Action.REMOVE, Specifier.BOTH, "file://Dolor");
		assertTracksTitles();
		assertTracksLocations();

	}

///////////////////////////////////////////////////////////////////////////////////////////////////	

	/**
	 * Verifies whether the there tracks with following locations present in the
	 * testing file.
	 * 
	 * @param expectedLocations
	 * @throws XSPFException
	 */
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

	/**
	 * Verifies whether the there tracks with following titles present in the
	 * testing file.
	 * 
	 * @param expectedTitles
	 * @throws XSPFException
	 */
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

	/**
	 * Returns true if there is a track with the given location in the given tracks
	 * collection.
	 * 
	 * @param tracks
	 * @param expectedLocation
	 * @return
	 * @throws XSPFException
	 */
	private boolean hasLocation(XSPFTracks tracks, String expectedLocation) throws XSPFException {
		URI expectedLocationUri = URI.create(expectedLocation);

		for (XSPFTrack track : tracks.iterate()) {
			if (expectedLocationUri.equals(track.getLocation())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if there is a track with the given title in the given tracks
	 * collection.
	 * 
	 * @param tracks
	 * @param expectedTitle
	 * @return
	 * @throws XSPFException
	 */
	private boolean hasTitle(XSPFTracks tracks, String expectedTitle) throws XSPFException {
		for (XSPFTrack track : tracks.iterate()) {
			try {
				if (expectedTitle.equals(track.getTitle())) {
					return true;
				}
			} catch (XSPFException e) {
				continue;
			}
		}
		return false;
	}

	/**
	 * Prepares the testing playlist to be modified (infact loads the empty paylist
	 * and saves to temp location).
	 * 
	 * @return
	 * @throws Exception
	 * @throws XSPFException
	 */
	private static File prepare() throws Exception, XSPFException {
		File inputFile = TestingFiles.fileToRead("playlist", "empty.xspf");
		XSPFFile file = XSPFFile.load(inputFile);

		file.playlist().tracks(); // force to generate the trackList element

		File testingFile = TestingFiles.fileToWrite("empty.xspf");
		file.save(testingFile);
		return testingFile;
	}

}
