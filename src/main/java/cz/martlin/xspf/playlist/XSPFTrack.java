package cz.martlin.xspf.playlist;

import java.time.Duration;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFDocumentUtility;
import cz.martlin.xspf.util.XSPFException;

public class XSPFTrack extends XSPFCommon {
	protected static final XSPFDocumentUtility util = XSPFPlaylist.util;

	private final Element element;

	public XSPFTrack(Element element) {
		super();
		Objects.requireNonNull(element, "The element must be provided");
		this.element = element;
	}

	@Override
	public Element getElement() {
		return element;
	}

	///////////////////////////////////////////////////////////////////////////


	public Duration getDuration() throws XSPFException {
		return getDuration("duration");
	}

	public void setDuration(Duration duration) throws XSPFException {
		setDuration("duration", duration);
	}
	
	public String getAlbum() throws XSPFException {
		return get("album");
	}
	
	public void setAlbum(String album) throws XSPFException {
		set("album", album);
	}
	
	public int getTrackNum() throws XSPFException {
		return getInt("trackNum");
	}
	
	public void setTrackNum(int trackNum) throws XSPFException {
		setInt("trackNum", trackNum);
	}

///////////////////////////////////////////////////////////////////////////

	

	

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
