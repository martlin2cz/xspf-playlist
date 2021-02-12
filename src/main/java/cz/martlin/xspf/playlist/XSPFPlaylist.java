package cz.martlin.xspf.playlist;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFException;

public class XSPFPlaylist extends XSPFCommon {

	private final Element playlist;

	public XSPFPlaylist(Element element) {
		super();

		Objects.requireNonNull(element, "The element must be provided");
		this.playlist = element;
	}
	
	@Override
	protected Element getElement() {
		return playlist;
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
	
	public void setAttribution(XSPFAttribution attribution) throws XSPFException {
		setOne("attribution", attribution);
	}

	public List<XSPFTrack> getTracks() throws XSPFException {
		return getAll("trackList", (e) -> new XSPFTrack(e));
	}

	public XSPFTrack newTrack() {
		return createOne("track", (e) -> new XSPFTrack(e));
	}

	public void setTracks(List<XSPFTrack> tracks) throws XSPFException {
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
