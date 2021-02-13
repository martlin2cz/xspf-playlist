package cz.martlin.xspf.playlist.elements;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.util.XSPFException;

public class XSPFPlaylist extends XSPFCommon {

	public XSPFPlaylist(Element playlist) {
		super(playlist);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	public LocalDateTime getDate() throws XSPFException {
		return getDate("date");
	}

	public void setDate(LocalDateTime date) throws XSPFException {
		setDate("date", date);
	}

	public URI getLicense() throws XSPFException {
		return getUri("license");
	}

	public void setLicense(URI license) throws XSPFException {
		setUri("license", license);
	}
	
	public XSPFAttribution getAttribution() throws XSPFException {
		return getOne("attribution", (e) -> new XSPFAttribution(e));
	}
	
	public XSPFAttribution attribution() throws XSPFException {
		return one("attribution", (e) -> new XSPFAttribution(e));
	}
	
	public void setAttribution(XSPFAttribution attribution) throws XSPFException {
		setOne("attribution", attribution);
	}

	public XSPFTracks getTracks() throws XSPFException {
		return  (XSPFTracks) getAll("trackList", XSPFTracks::new);
	}

	public XSPFTracks tracks() throws XSPFException {
		return  (XSPFTracks) all("trackList", XSPFTracks::new);
	}
	
	
	public void setTracks(XSPFTracks tracks) throws XSPFException {
		setAll("trackList", tracks);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		try {
			return "XSPFPlaylist [getTitle()=" + getTitle() + ", getTracks()=" + getTracks() + "]";
		} catch (XSPFException e) {
			return "XSPFPlaylist [Error: " + e + "]";
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	

}
