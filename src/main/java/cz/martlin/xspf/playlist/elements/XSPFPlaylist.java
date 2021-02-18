package cz.martlin.xspf.playlist.elements;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

public class XSPFPlaylist extends XSPFCommon {

	public XSPFPlaylist(Element playlist) {
		super(playlist);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	public LocalDateTime getDate()throws XSPFException {
		return getDate(Names.DATE);
	}

	public void setDate(LocalDateTime date)throws XSPFException {
		setDate(Names.DATE, date);
	}

	public URI getLicense()throws XSPFException {
		return getUri(Names.LICENSE);
	}

	public void setLicense(URI license)throws XSPFException {
		setUri(Names.LICENSE, license);
	}

	public XSPFAttribution getAttribution() throws XSPFException {
		return getOne(Names.ATTRIBUTION, (e) -> new XSPFAttribution(e));
	}

	public XSPFAttribution attribution()throws XSPFException {
		return one(Names.ATTRIBUTION, (e) -> new XSPFAttribution(e));
	}

	public void setAttribution(XSPFAttribution attribution)throws XSPFException {
		setOne(Names.ATTRIBUTION, attribution);
	}

	public XSPFTracks getTracks()throws XSPFException {
		return (XSPFTracks) getCollection(Names.TRACK_LIST, XSPFTracks::new);
	}

	public XSPFTracks tracks()throws XSPFException {
		return (XSPFTracks) collection(Names.TRACK_LIST, XSPFTracks::new);
	}

	public void setTracks(XSPFTracks tracks)throws XSPFException {
		setCollection(Names.TRACK_LIST, tracks);
	}

	/////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSPPlaylist [");
		try {
			builder.append("title=");
			builder.append(getTitle());
			builder.append(", tracks=");
			builder.append(getTracks());
		} catch (XSPFException e) {
			builder.append(e);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		try {
			result = prime * result +Objects.hash(attribution(), getDate(), getLicense(), tracks());
		} catch (XSPFException eIgnored) {
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		XSPFPlaylist other = (XSPFPlaylist) obj;
		try {
			return Objects.equals(attribution(), other.attribution()) && Objects.equals(getDate(), other.getDate())
					&& Objects.equals(getLicense(), other.getLicense()) && Objects.equals(tracks(), other.tracks());
		} catch (XSPFException e) {
			return false;
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

}
