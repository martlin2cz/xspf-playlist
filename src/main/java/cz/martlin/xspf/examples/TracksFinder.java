package cz.martlin.xspf.examples;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;

/**
 * Program which finds all tracks containing specified needle in its title.
 * 
 * @author martin
 *
 */
public class TracksFinder {

	/**
	 * Just the main.
	 * 
	 * @param args
	 * @throws XSPFException
	 */
	public static void main(String[] args) throws XSPFException {
		if (args.length < 2) {
			System.err.println("Usage: TracksFinder <PLAYIST_FILE> <NEEDLE>");
			System.exit(1);
		}

		String filePath = args[0];
		String needle = args[1];

		File file = new File(filePath);

		findAndPrintTracks(file, needle);
	}

	/**
	 * Finds and prints titles of all the tracks matching the given needle.
	 * 
	 * @param file
	 * @param needle
	 * @throws XSPFException
	 */
	public static void findAndPrintTracks(File file, String needle) throws XSPFException {
		XSPFFile xspf = XSPFFile.load(file);

		XSPFPlaylist playlist = xspf.playlist();
		XSPFTracks tracks = playlist.tracks();
		Stream<XSPFTrack> tracksStream = tracks.list();

		Stream<XSPFTrack> filteredStream = filterOutNotMatching(tracksStream, needle);

		List<XSPFTrack> filteredList = filteredStream.collect(Collectors.toList());
		printTracks(filteredList);
	}

	/**
	 * Filters out all the tracks which does not contain the given needle in its
	 * title.
	 * 
	 * @param tracksList
	 * @param needle
	 * @return
	 */
	private static Stream<XSPFTrack> filterOutNotMatching(Stream<XSPFTrack> tracksList, String needle) {
		return tracksList.filter(t -> {
			try {
				return t.getTitle().contains(needle);
			} catch (XSPFException e) {
				System.err.println(e);
				return false;
			}
		});
	}

	/**
	 * Prints the given tracks (their titles).
	 * 
	 * @param tracks
	 */
	private static void printTracks(List<XSPFTrack> tracks) {
		if (tracks.isEmpty()) {
			System.err.println("No such track.");
		}

		for (XSPFTrack track : tracks) {
			try {
				System.out.println(track.getTitle());
			} catch (XSPFException e) {
				System.err.println(e);
			}
		}
	}

}
