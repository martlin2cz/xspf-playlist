package cz.martlin.xspf.util;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;

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

/**
 * Helper class for printing all the {@link XSPFElement}s or
 * {@link XSPFCollection}s.
 * 
 * @author martin
 *
 */
public class Printer {
	/**
	 * The padding spacing. Two spaces, to be specific.
	 */
	private static final String PADDING_STEP = "  ";

	/**
	 * Prints the given file with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param file
	 * @throws XSPFException
	 */
	public static void print(int padding, String label, XSPFFile file) throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;
		print(p, "playlist", file.getPlaylist());
	}

	/**
	 * Prints the given plůaylist with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param playlist
	 * @throws XSPFException
	 */
	public static void print(int padding, String label, XSPFPlaylist playlist) throws XSPFException {
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

	/**
	 * Prints the given tracks with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param tracks
	 * @throws XSPFException
	 */
	private static void print(int padding, String label, XSPFTracks tracks) throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		for (XSPFTrack track : tracks.iterate()) {
			print(p, "track", track);
			printBlankLine();
		}
	}

	/**
	 * Prints the given track with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param track
	 * @throws XSPFException
	 */
	private static void print(int padding, String label, XSPFTrack track) throws XSPFException {
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

	/**
	 * Prints the given metas with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param metas
	 * @throws XSPFException
	 */
	private static void print(int padding, String label, XSPFMetas metas) throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		for (XSPFMeta meta : metas.iterate()) {
			print(p, "meta", meta);
			printBlankLine();
		}
	}

	/**
	 * Prints the given meta with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param meta
	 * @throws XSPFException
	 */
	private static void print(int padding, String label, XSPFMeta meta) throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		print(p, "content", meta.getContent());
		print(p, "rel", meta.getRel());
	}

	/**
	 * Prints the given extensions with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param extensions
	 * @throws XSPFException
	 */
	private static void print(int padding, String label, XSPFExtensions extensions) throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		for (XSPFExtension extension : extensions.iterate()) {
			print(p, "extension", extension);
			printBlankLine();
		}
	}

	/**
	 * Prints the given extension with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param extension
	 * @throws XSPFException
	 */
	private static void print(int padding, String label, XSPFExtension extension) throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		print(p, "application", extension.getApplication());
		// TODO extension node?
	}

	/**
	 * Prints the given links with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param links
	 * @throws XSPFException
	 */
	private static void print(int padding, String label, XSPFLinks links) throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		for (XSPFLink link : links.iterate()) {
			print(p, "link", link);
			printBlankLine();
		}
	}

	/**
	 * Prints the given link with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param link
	 * @throws XSPFException
	 */
	private static void print(int padding, String label, XSPFLink link) throws XSPFException {
		printLabel(padding, label);
		int p = padding + 1;

		print(p, "content", link.getContent());
		print(p, "rel", link.getRel());
	}

	/**
	 * Prints the given attribution with given label and padding.
	 * 
	 * @param padding
	 * @param label
	 * @param attribution
	 * @throws XSPFException
	 */
	private static void print(int padding, String label, XSPFAttribution attribution) throws XSPFException {
		if (attribution == null) {
			printLabeled(padding, label, null);
			return;
		}

		printLabel(padding, label);
		int p = padding + 1;

		for (XSPFAttributionItem item : attribution.list()) {
			print(p, item.element, item.value);
		}
		printBlankLine();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Prints padded and labeled given text.
	 * 
	 * @param padding
	 * @param label
	 * @param text
	 */
	private static void print(int padding, String label, String text) {
		printLabeled(padding, label, text);
	}

	/**
	 * Prints padded and labeled given date.
	 * 
	 * @param padding
	 * @param label
	 * @param date
	 */
	private static void print(int padding, String label, LocalDateTime date) {
		printLabeled(padding, label, date);
	}

	/**
	 * Prints padded and labeled given uri.
	 * 
	 * @param padding
	 * @param label
	 * @param uri
	 */
	private static void print(int padding, String label, URI uri) {
		printLabeled(padding, label, uri);
	}

	/**
	 * Prints padded and labeled given trackNum.
	 * 
	 * @param padding
	 * @param label
	 * @param trackNum
	 */
	private static void print(int padding, String label, Integer trackNum) {
		printLabeled(padding, label, trackNum);
	}

	/**
	 * Prints padded and labeled given duration.
	 * 
	 * @param padding
	 * @param label
	 * @param duration
	 */
	private static void print(int padding, String label, Duration duration) {
		printLabeled(padding, label, duration);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Prints just the padded label.
	 * 
	 * @param padding
	 * @param label
	 */
	private static void printLabel(int padding, String label) {
		String pad = PADDING_STEP.repeat(padding);

		System.out.println(pad + " - " + label + ":");
	}

	/**
	 * Prints the padded label and its value.
	 * 
	 * @param padding
	 * @param label
	 * @param value
	 */
	private static void printLabeled(int padding, String label, Object value) {
		String pad = PADDING_STEP.repeat(padding);
		String valueStr = String.valueOf(value);

		System.out.println(pad + " - " + label + ": " + valueStr);
	}

	/**
	 * Just prints the blank line.
	 */
	private static void printBlankLine() {
		System.out.println();
	}

}
