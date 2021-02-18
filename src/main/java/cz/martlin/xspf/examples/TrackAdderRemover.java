package cz.martlin.xspf.examples;

import java.io.File;
import java.net.URI;

import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;

/**
 * Simple program allowing to add or remove track(s) to/from the playlist file.
 * 
 * @author martin
 *
 */
public class TrackAdderRemover {

	/**
	 * The action to be done.
	 * 
	 * @author martin
	 *
	 */
	public enum Action {
		/**
		 * Add new track.
		 */
		ADD,

		/**
		 * Remove existing track.
		 */
		REMOVE;
	}

	/**
	 * Specifier what the provided "value" means.
	 * 
	 * @author martin
	 *
	 */
	public enum Specifier {
		/**
		 * The track title.
		 */
		TITLE,
		/**
		 * The track location.
		 */
		LOCATION,
		/**
		 * Both the track title and location.
		 */
		BOTH;
	}

	/**
	 * The main.
	 * 
	 * @param args
	 * @throws XSPFException
	 */
	public static void main(String[] args) throws XSPFException {
		if (args.length < 4) {
			System.err.println("Usage: TrackAdderRemover <PLAYIST_FILE> <add|remove> <title|location|both> <VALUE>");
			System.exit(1);
		}

		String filePath = args[0];
		String actionStr = args[1];
		String specifierStr = args[2];
		String value = args[3];

		File file = new File(filePath);
		Action action = Action.valueOf(actionStr.toLowerCase());
		Specifier specifier = Specifier.valueOf(specifierStr.toLowerCase());

		performAction(file, action, specifier, value);
	}

	/**
	 * Performs the given action with given playlist file and given value.
	 * 
	 * @param file      the playlist file
	 * @param action    the add/remove action specifier
	 * @param specifier the specifier of the value parameter
	 * @param value     the title/location value
	 * @throws XSPFException
	 */
	public static void performAction(File file, Action action, Specifier specifier, String value) throws XSPFException {
		XSPFFile xspf = XSPFFile.load(file);

		XSPFPlaylist playlist = xspf.playlist();
		XSPFTracks tracks = playlist.tracks();

		switch (action) {
		case ADD:
			performAddAction(tracks, specifier, value);
			break;
		case REMOVE:
			performRemoveAction(tracks, specifier, value);
			break;
		default:
			throw new IllegalArgumentException("Unsupported action: " + action + ", only add|remove is valid.");
		}

		xspf.save(file);
	}

	/**
	 * Adds track of given specified value to the given tracks.
	 * 
	 * @param tracks
	 * @param specifier
	 * @param value
	 * @throws XSPFException
	 */
	private static void performAddAction(XSPFTracks tracks, Specifier specifier, String value) throws XSPFException {
		XSPFTrack track = tracks.createNew();
		initTrack(specifier, value, track);

		tracks.add(track);
	}

	/**
	 * Populates the given value of the given specifier to the given track.
	 * 
	 * @param specifier
	 * @param value
	 * @param track
	 * @throws XSPFException
	 */
	private static void initTrack(Specifier specifier, String value, XSPFTrack track) throws XSPFException {
		switch (specifier) {
		case TITLE: {
			String title = value;
			track.setTitle(title);
			break;
		}
		case LOCATION: {
			URI location = URI.create(value);
			track.setLocation(location);
			break;
		}
		case BOTH: {
			String title = value;
			track.setTitle(title);
			URI location = URI.create(value);
			track.setLocation(location);
			break;
		}
		default:
			throw new IllegalArgumentException(
					"Unsupported specifier: " + specifier + ", only location|title|both is valid.");
		}
	}

	/**
	 * Removes all the tracks of the given value.
	 * 
	 * @param tracks
	 * @param specifier
	 * @param value
	 * @throws XSPFException
	 */
	private static void performRemoveAction(XSPFTracks tracks, Specifier specifier, String value) throws XSPFException {

		for (XSPFTrack track : tracks.iterate()) {
			if (matches(track, specifier, value)) {
				tracks.remove(track);
			}
		}
	}

	/**
	 * Returns true if given track has given value.
	 * 
	 * @param track
	 * @param specifier
	 * @param value
	 * @return
	 * @throws XSPFException
	 */
	private static boolean matches(XSPFTrack track, Specifier specifier, String value) throws XSPFException {
		switch (specifier) {
		case TITLE: {
			String title = value;
			return title.equals(track.getTitle());
		}
		case LOCATION: {
			URI location = URI.create(value);
			return location.equals(track.getLocation());
		}
		case BOTH: {
			String title = value;
			URI location = URI.create(value);
			return title.equals(track.getTitle()) && location.equals(track.getLocation());
		}
		default:
			throw new IllegalArgumentException(
					"Unsupported specifier: " + specifier + ", only location|title|both is valid.");
		}
	}

}
