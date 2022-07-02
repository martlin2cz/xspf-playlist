package cz.martlin.xspf.playlist.elements;

import java.time.Duration;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

/**
 * The track element.
 * 
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1
 * @author martin
 *
 */
public class XSPFTrack extends XSPFCommon {

	/**
	 * Creates instance.
	 * 
	 * @param track the track element
	 */
	public XSPFTrack(Element track) {
		super(track);
	}
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the track duration.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.10
	 * @return
	 * @throws XSPFException
	 */
	public Duration getDuration() throws XSPFException {
		return getDuration(Names.DURATION);
	}

	/**
	 * Sets the track duration.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.10
	 * @param duration
	 * @throws XSPFException
	 */
	public void setDuration(Duration duration) throws XSPFException {
		setDuration(Names.DURATION, duration);
	}

	/**
	 * Returns the track album.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.8
	 * @return
	 * @throws XSPFException
	 */
	public String getAlbum() throws XSPFException {
		return getStr(Names.ALBUM);
	}

	/**
	 * Sets the track album.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.8
	 * @param album
	 * @throws XSPFException
	 */
	public void setAlbum(String album) throws XSPFException {
		setStr(Names.ALBUM, album);
	}

	/**
	 * Returns the trackNum.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.9
	 * @return
	 * @throws XSPFException
	 */
	public Integer getTrackNum() throws XSPFException {
		return getInt(Names.TRACK_NUM);
	}

	/**
	 * Sets the trackNum.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.9
	 * @param trackNum
	 * @throws XSPFException
	 */
	public void setTrackNum(int trackNum) throws XSPFException {
		setInt(Names.TRACK_NUM, trackNum);
	}

///////////////////////////////////////////////////////////////////////////

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

}
