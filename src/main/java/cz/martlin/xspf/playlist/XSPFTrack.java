package cz.martlin.xspf.playlist;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFDocumentUtility;
import cz.martlin.xspf.util.XSPFException;

public class XSPFTrack implements BaseXSPFElement {
	protected static final XSPFDocumentUtility util = XSPFPlaylist.util;

	private final Element element;

	public XSPFTrack(Element element) {
		super();
		Objects.requireNonNull(element, "The element must be provided");
		this.element = element;
	}

	public Element getElement() {
		return element;
	}

	@Override
	public XSPFDocumentUtility getUtil() {
		return util;
	}
	///////////////////////////////////////////////////////////////////////////
	@Override
	public String getTitle() throws XSPFException {
		return util.getElementText(element, "title");
	}

	@Override
	public void setTitle(String title) throws XSPFException {
		util.setElementText(element, "title", title);
	}

	public Duration getDuration() throws XSPFException {
		return util.getElementValue(element, "duration", //
				(v) -> parseDuration(v));
	}

	public void setDuration(Duration duration) throws XSPFException {
		util.setElementValue(element, "duration", duration, //
				(d) -> stringifyDuration(d));
	}

	public String getAnnotation() throws XSPFException {
		return util.getElementText(element, "annotation");
	}

	public void setAnnotation(String annotation) throws XSPFException {
		util.setElementText(element, "annotation", annotation);
	}

	public URI getLocation() throws XSPFException {
		return util.getElementValue(element, "location", (v) -> parseUri(v));
	}

	public void setLocation(URI location) throws XSPFException {
		util.setElementValue(element, "location", location, (v) -> stringifyUri(v));
	}

	@Override
	public Element getExtension() throws XSPFException {
		return util.getOrCreateChildElem(element, "extension");
	}

///////////////////////////////////////////////////////////////////////////

	private Duration parseDuration(String text) {
		long milis = Long.parseLong(text);
		return Duration.ofMillis(milis);
	}

	private String stringifyDuration(Duration duration) {
		long milis = duration.toMillis();
		return Long.toString(milis);
	}

	private URI parseUri(String text) {
		return URI.create(text);
	}

	private String stringifyUri(URI uri) {
		return uri.toASCIIString();
	}

///////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		try {
			return "XSPFTrack [getTitle()=" + getTitle() + ", getDuration()=" + getDuration() + "]";
		} catch (XSPFException e) {
			return "XSPFTrack [Error: " + e + "]";
		}
	}

}
