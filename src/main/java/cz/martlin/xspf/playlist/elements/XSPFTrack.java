package cz.martlin.xspf.playlist.elements;

import java.time.Duration;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

public class XSPFTrack extends XSPFCommon {

	public XSPFTrack(Element track) {
		super(track);
	}
	///////////////////////////////////////////////////////////////////////////


	public Duration getDuration()throws XSPFException {
		return getDuration(Names.DURATION);
	}

	public void setDuration(Duration duration)throws XSPFException {
		setDuration(Names.DURATION, duration);
	}

	public String getAlbum()throws XSPFException {
		return getStr(Names.ALBUM);
	}

	public void setAlbum(String album)throws XSPFException {
		setStr(Names.ALBUM, album);
	}

	public Integer getTrackNum()throws XSPFException {
		return getInt(Names.TRACK_NUM);
	}

	public void setTrackNum(int trackNum)throws XSPFException {
		setInt(Names.TRACK_NUM, trackNum);
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
