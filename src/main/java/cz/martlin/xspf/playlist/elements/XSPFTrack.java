package cz.martlin.xspf.playlist.elements;

import java.time.Duration;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.util.XSPFException;

public class XSPFTrack extends XSPFCommon {

	public XSPFTrack(Element track) {
		super(track);
	}
	///////////////////////////////////////////////////////////////////////////


	public Duration getDuration()throws XSPFException {
		return getDuration("duration");
	}

	public void setDuration(Duration duration)throws XSPFException {
		setDuration("duration", duration);
	}

	public String getAlbum()throws XSPFException {
		return getStr("album");
	}

	public void setAlbum(String album)throws XSPFException {
		setStr("album", album);
	}

	public Integer getTrackNum()throws XSPFException {
		return getInt("trackNum");
	}

	public void setTrackNum(int trackNum)throws XSPFException {
		setInt("trackNum", trackNum);
	}

///////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSPFTrack [");
		try {
			builder.append("title=");
			builder.append(getTitle());
			builder.append(", location=");
			builder.append(getLocation());
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
			result = prime * result + Objects.hash(getAlbum(), getDuration(), getTrackNum());
		} catch (XSPFException eIgnore) {
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
		XSPFTrack other = (XSPFTrack) obj;
		try {
			return Objects.equals(getAlbum(), other.getAlbum()) && Objects.equals(getDuration(), other.getDuration())
					&& Objects.equals(getTrackNum(), other.getTrackNum());
		} catch (XSPFException e) {
			return false;
		}
	}

}
