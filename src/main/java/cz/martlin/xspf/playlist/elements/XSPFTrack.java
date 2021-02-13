package cz.martlin.xspf.playlist.elements;

import java.time.Duration;
import java.util.Objects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.util.XMLDocumentUtility;
import cz.martlin.xspf.util.XSPFException;

public class XSPFTrack extends XSPFCommon {

	public XSPFTrack(Element track) {
		super(track);
	}
	
	///////////////////////////////////////////////////////////////////////////


	public Duration getDuration() throws XSPFException {
		return getDuration("duration");
	}

	public void setDuration(Duration duration) throws XSPFException {
		setDuration("duration", duration);
	}
	
	public String getAlbum() throws XSPFException {
		return getStr("album");
	}
	
	public void setAlbum(String album) throws XSPFException {
		setStr("album", album);
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
