package cz.martlin.xspf.util;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;

import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.collections.XSPFLinks;
import cz.martlin.xspf.playlist.collections.XSPFMetas;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.playlist.elements.XSPFAttribution;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFLink;
import cz.martlin.xspf.playlist.elements.XSPFMeta;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;

public class Printer {

	private static final String PADDING_STEP = "  ";

	public static void print(int padding, String label, XSPFFile file)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;
		print(p, "playlist", file.getPlaylist());
	}

	public static void print(int padding, String label, XSPFPlaylist playlist)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		print(p, "annotation", playlist.getAnnotation());
		print(p, "creator", playlist.getCreator());
		print(p, "label", playlist.getTitle());
		print(p, "attribution", playlist.getAttribution());
		print(p, "date", playlist.getDate());
		print(p, "extensions", playlist.getExtensions());
		print(p, "identifier", playlist.getIdentifier());
		print(p, "image", playlist.getImage());
		print(p, "info", playlist.getInfo());
		print(p, "licence", playlist.getLicense());
		print(p, "links", playlist.getLinks());
		print(p, "location", playlist.getLocation());
		print(p, "metas", playlist.getMetas());
		print(p, "tracks", playlist.getTracks());
	}

	private static void print(int padding, String label, XSPFTracks tracks)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		for (XSPFTrack track : tracks.iterate()) {
			print(p, "track", track);
			printBlankLine();
		}
	}

	private static void print(int padding, String label, XSPFTrack track)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		print(p, "album", track.getAlbum());
		print(p, "annotation", track.getAnnotation());
		print(p, "creator", track.getCreator());
		print(p, "extensions", track.getExtensions());
		print(p, "identifier", track.getIdentifier());
		print(p, "image", track.getImage());
		print(p, "info", track.getInfo());
		print(p, "links", track.getLinks());
		print(p, "location", track.getLocation());
		print(p, "metas", track.getMetas());
		print(p, "title", track.getTitle());
		print(p, "duration", track.getDuration());
		print(p, "trackNum", track.getTrackNum());
	}

	private static void print(int padding, String label, XSPFMetas metas)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		for (XSPFMeta meta : metas.iterate()) {
			print(p, "meta", meta);
			printBlankLine();
		}
	}

	private static void print(int padding, String label, XSPFMeta meta)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		print(p, "content", meta.getContent());
		print(p, "rel", meta.getRel());
	}

	private static void print(int padding, String label, XSPFExtensions extensions)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		for (XSPFExtension extension : extensions.iterate()) {
			print(p, "extension", extension);
			printBlankLine();
		}
	}

	private static void print(int padding, String label, XSPFExtension extension)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		print(p, "application", extension.getApplication());
		// TODO extension node?
	}

	private static void print(int padding, String label, XSPFLinks links)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		for (XSPFLink link : links.iterate()) {
			print(p, "link", link);
			printBlankLine();
		}
	}

	private static void print(int padding, String label, XSPFLink link)throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		print(p, "content", link.getContent());
		print(p, "rel", link.getRel());
	}

	private static void print(int padding, String label, XSPFAttribution attribution) {
		printLabel(padding, label);
		int p = padding + 1;
		
		//TODO attribution ?

	}

	/////////////////////////////////////////////////////////////////////////////////////

	private static void print(int padding, String label, String text) {
		printLabeled(padding, label, text);
	}

	private static void print(int padding, String label, LocalDateTime date) {
		printLabeled(padding, label, date);
	}

	private static void print(int padding, String label, URI uri) {
		printLabeled(padding, label, uri);
	}

	private static void print(int padding, String label, Integer trackNum) {
		printLabeled(padding, label, trackNum);
	}

	private static void print(int padding, String label, Duration duration) {
		printLabeled(padding, label, duration);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private static void printLabel(int padding, String label) {
		String pad = PADDING_STEP.repeat(padding);

		System.out.println(pad + " - " + label + ":");
	}

	private static void printLabeled(int padding, String label, Object value) {
		String pad = PADDING_STEP.repeat(padding);
		String valueStr = String.valueOf(value);
		
		System.out.println(pad + " - " + label + ": " + valueStr);
	}

	private static void printBlankLine() {
		System.out.println();
	}

}
