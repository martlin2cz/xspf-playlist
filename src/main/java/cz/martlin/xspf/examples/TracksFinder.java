package cz.martlin.xspf.examples;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;

public class TracksFinder {
	
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

	public static void findAndPrintTracks(File file, String needle) throws XSPFException {
		XSPFFile xspf = XSPFFile.load(file);

		XSPFPlaylist playlist = xspf.playlist();
		XSPFTracks tracks = playlist.tracks();
		List<XSPFTrack> tracksList = tracks.list();

		List<XSPFTrack> filteredList = filterOutNotMatching(tracksList, needle);

		printTracks(filteredList);
	}

	private static List<XSPFTrack> filterOutNotMatching(List<XSPFTrack> tracksList, String needle) {
		return tracksList.stream().filter(t -> {
			try {
				return t.getTitle().contains(needle);
			} catch (XSPFException e) {
				System.err.println(e);
				return false;
			}
		}).collect(Collectors.toList());
	}

	private static void printTracks(List<XSPFTrack> tracksList) {
		if (tracksList.isEmpty()) {
			System.err.println("No such track.");
		}
		
		for (XSPFTrack track : tracksList) {
			try {
				System.out.println(track.getTitle());
			} catch (XSPFException e) {
				System.err.println(e);
			}
		}
	}

}
