package cz.martlin.xspf.examples;

import java.io.File;
import java.net.URI;

import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;

public class TrackAdderRemover {

	public static void main(String[] args) throws XSPFException {
		if (args.length < 4) {
			System.err.println("Usage: TrackAdderRemover <PLAYIST_FILE> <add|remove> <title|location|both> <VALUE>");
			System.exit(1);
		}

		String filePath = args[0];
		String addRemoveStr = args[1];
		String titleLocationStr = args[2];
		String value = args[3];

		File file = new File(filePath);

		performAddRemoveAction(file, addRemoveStr, titleLocationStr, value);
	}

	public static void performAddRemoveAction(File file, String action, String specifier, String value)
			throws XSPFException {
		XSPFFile xspf = XSPFFile.load(file);

		XSPFPlaylist playlist = xspf.playlist();
		XSPFTracks tracks = playlist.tracks();

		switch (action) {
		case "add":
			performAddAction(tracks, specifier, value);
			break;
		case "remove":
			performRemoveAction(tracks, specifier, value);
			break;
		default:
			throw new IllegalArgumentException("Unsupported action: " + action + ", only add|remove is valid.");
		}

		xspf.save(file);
	}

	private static void performAddAction(XSPFTracks tracks, String specifier, String value) throws XSPFException {
		XSPFTrack track = tracks.createNew();
		initTrack(specifier, value, track);

		tracks.add(track);
	}

	private static void initTrack(String specifier, String value, XSPFTrack track) throws XSPFException {
		switch (specifier) {
		case "title": {
			String title = value;
			track.setTitle(title);
			break;
		}
		case "location": {
			URI location = URI.create(value);
			track.setLocation(location);
			break;
		}
		case "both": {
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

	private static void performRemoveAction(XSPFTracks tracks, String specifier, String value) throws XSPFException {

		for (XSPFTrack track : tracks.iterate()) {
			if (matches(track, specifier, value)) {
				tracks.remove(track);
			}
		}
	}

	private static boolean matches(XSPFTrack track, String specifier, String value) throws XSPFException {
		switch (specifier) {
		case "title": {
			String title = value;
			return title.equals(track.getTitle());
		}
		case "location": {
			URI location = URI.create(value);
			return location.equals(track.getLocation());
		}
		case "both": {
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
