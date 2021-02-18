package cz.martlin.xspf.playlist.elements;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

/**
 * The playlist element.
 * 
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1
 * @author martin
 *
 */
public class XSPFPlaylist extends XSPFCommon {

	/**
	 * Creates instance.
	 * 
	 * @param playlist the playlist element
	 */
	public XSPFPlaylist(Element playlist) {
		super(playlist);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the playlist date.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.8
	 * @return
	 * @throws XSPFException
	 */
	public LocalDateTime getDate() throws XSPFException {
		return getDate(Names.DATE);
	}

	/**
	 * Sets the playlist date.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.8
	 * @param date
	 * @throws XSPFException
	 */
	public void setDate(LocalDateTime date) throws XSPFException {
		setDate(Names.DATE, date);
	}

	/**
	 * Returns the playlist license.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.9
	 * @return
	 * @throws XSPFException
	 */
	public URI getLicense() throws XSPFException {
		return getUri(Names.LICENSE);
	}

	/**
	 * Sets the playlist license.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.9
	 * @param license
	 * @throws XSPFException
	 */
	public void setLicense(URI license) throws XSPFException {
		setUri(Names.LICENSE, license);
	}

	/**
	 * Gets (copy of) the playlist attribution.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.10
	 * @return
	 * @throws XSPFException
	 */
	public XSPFAttribution getAttribution() throws XSPFException {
		return getOne(Names.ATTRIBUTION, (e) -> new XSPFAttribution(e));
	}

	/**
	 * Gets the (view of) the playlist attribution.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.10
	 * @return
	 * @throws XSPFException
	 */
	public XSPFAttribution attribution() throws XSPFException {
		return one(Names.ATTRIBUTION, (e) -> new XSPFAttribution(e));
	}

	/**
	 * Sets the (copy of) playlist attribution.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.10
	 * @param attribution
	 * @throws XSPFException
	 */
	public void setAttribution(XSPFAttribution attribution) throws XSPFException {
		setOne(Names.ATTRIBUTION, attribution);
	}

	/**
	 * Returns the (copy of) the tracks.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14
	 * @return
	 * @throws XSPFException
	 */
	public XSPFTracks getTracks() throws XSPFException {
		return (XSPFTracks) getCollection(Names.TRACK_LIST, XSPFTracks::new);
	}

	/**
	 * Returns the (view of) the tracks.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14
	 * @return
	 * @throws XSPFException
	 */
	public XSPFTracks tracks() throws XSPFException {
		return (XSPFTracks) collection(Names.TRACK_LIST, XSPFTracks::new);
	}

	/**
	 * Set the (copy of) the tracks.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14
	 * @param tracks
	 * @throws XSPFException
	 */
	public void setTracks(XSPFTracks tracks) throws XSPFException {
		setCollection(Names.TRACK_LIST, tracks);
	}

///////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		try {
			result = prime * result + Objects.hash(attribution(), getDate(), getLicense(), tracks());
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

	/////////////////////////////////////////////////////////////////////////////////////

}
