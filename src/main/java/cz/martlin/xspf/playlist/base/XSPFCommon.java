package cz.martlin.xspf.playlist.base;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.collections.XSPFLinks;
import cz.martlin.xspf.playlist.collections.XSPFMetas;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

/**
 * The abstract superclass containing the common properties of the
 * {@link XSPFPlaylist} and {@link XSPFTrack}.
 * 
 * @author martin
 *
 */
public abstract class XSPFCommon extends XSPFElement {

	/**
	 * Creates instance for the given playlist/track element.
	 * 
	 * @param element
	 */
	public XSPFCommon(Element element) {
		super(element);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the playlist/track location.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.5
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.1
	 * @return
	 * @throws XSPFException
	 */
	public URI getLocation() throws XSPFException {
		return getUri(Names.LOCATION);
	}

	/**
	 * Sets the playlist/track location.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.5
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.1
	 * @param location
	 * @throws XSPFException
	 */
	public void setLocation(URI location) throws XSPFException {
		setUri(Names.LOCATION, location);
	}

	/**
	 * Returns the playlist/track identifier.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.6
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.2
	 * @return
	 * @throws XSPFException
	 */
	public URI getIdentifier() throws XSPFException {
		return getUri(Names.IDENTIFIER);
	}

	/**
	 * Sets the playlist/track identifier.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.6
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.2
	 * @param identifier
	 * @throws XSPFException
	 */
	public void setIdentifier(URI identifier) throws XSPFException {
		setUri(Names.IDENTIFIER, identifier);
	}

	/**
	 * Returns the playlist/track title.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.1
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.3
	 * @return
	 * @throws XSPFException
	 */
	public String getTitle() throws XSPFException {
		return getStr(Names.TITLE);
	}

	/**
	 * Sets the playlist/track title.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.1
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.3
	 * @param title
	 * @throws XSPFException
	 */
	public void setTitle(String title) throws XSPFException {
		setStr(Names.TITLE, title);
	}

	/**
	 * Returns the playlist/track creator.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.2
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.4
	 * @return
	 * @throws XSPFException
	 */
	public String getCreator() throws XSPFException {
		return getStr(Names.CREATOR);
	}

	/**
	 * Sets the playlist/track creator.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.2
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.4
	 * @param creator
	 * @throws XSPFException
	 */
	public void setCreator(String creator) throws XSPFException {
		setStr(Names.CREATOR, creator);
	}

	/**
	 * Returns the playlist/track annotation.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.3
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.5
	 * @return
	 * @throws XSPFException
	 */
	public String getAnnotation() throws XSPFException {
		return getStr(Names.ANNOTATION);
	}

	/**
	 * Sets the playlist/track annotation.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.3
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.5
	 * @param annotation
	 * @throws XSPFException
	 */
	public void setAnnotation(String annotation) throws XSPFException {
		setStr(Names.ANNOTATION, annotation);
	}

	/**
	 * Returns the playlist/track info.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.4
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.6
	 * @return
	 * @throws XSPFException
	 */
	public URI getInfo() throws XSPFException {
		return getUri(Names.INFO);
	}

	/**
	 * Sets the playlist/track info.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.4
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.6
	 * @param info
	 * @throws XSPFException
	 */
	public void setInfo(URI info) throws XSPFException {
		setUri(Names.INFO, info);
	}

	/**
	 * Returns the playlist/track image.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.7
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.7
	 * @return
	 * @throws XSPFException
	 */
	public URI getImage() throws XSPFException {
		return getUri(Names.IMAGE);
	}

	/**
	 * Sets the playlist/track image.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.7
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.7
	 * @param image
	 * @throws XSPFException
	 */
	public void setImage(URI image) throws XSPFException {
		setUri(Names.IMAGE, image);
	}

	/**
	 * Returns the playlist/track links (as a copy).
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11
	 * @return
	 * @throws XSPFException
	 */
	public XSPFLinks getLinks() throws XSPFException {
		return (XSPFLinks) getCollection(XSPFLinks::new);
	}

	/**
	 * Returns the playlist/track links (as a view).
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11
	 * @return
	 * @throws XSPFException
	 */
	public XSPFLinks links() throws XSPFException {
		return (XSPFLinks) collection(XSPFLinks::new);
	}

	/**
	 * Sets the (copy of) the playlist/track links.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11
	 * @param links
	 * @throws XSPFException
	 */
	public void setLinks(XSPFLinks links) throws XSPFException {
		setCollection(links);
	}

	/**
	 * Returns the playlist/track metas (as a copy).
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12
	 * @return
	 * @throws XSPFException
	 */
	public XSPFMetas getMetas() throws XSPFException {
		return (XSPFMetas) getCollection(XSPFMetas::new);
	}

	/**
	 * Returns the playlist/track metas (as a view).
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12
	 * @return
	 * @throws XSPFException
	 */
	public XSPFMetas metas() throws XSPFException {
		return (XSPFMetas) collection(XSPFMetas::new);
	}

	/**
	 * Sets (copy of) the playlist/track metas.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12
	 * @param metas
	 * @throws XSPFException
	 */
	public void setMetas(XSPFMetas metas) throws XSPFException {
		setCollection(metas);
	}

	/**
	 * Returns the playlist/track extensions (as copy).
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.13
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.13
	 * @return
	 * @throws XSPFException
	 */
	public XSPFExtensions getExtensions() throws XSPFException {
		return (XSPFExtensions) getCollection(XSPFExtensions::new);
	}

	/**
	 * Returns the playlist/track extensions (as view).
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.13
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.13
	 * @return
	 * @throws XSPFException
	 */
	public XSPFExtensions extensions() throws XSPFException {
		return (XSPFExtensions) collection(XSPFExtensions::new);
	}

	/**
	 * Sets the (copy of the) playlist/track extensions.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.13
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.13
	 * @param extensions
	 * @throws XSPFException
	 */
	public void setExtensions(XSPFExtensions extensions) throws XSPFException {
		setCollection(extensions);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		try {
			return Objects.hash(getAnnotation(), getCreator(), extensions(), getIdentifier(), getImage(), getInfo(),
					links(), getLocation(), metas(), getTitle());
		} catch (XSPFException e) {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XSPFCommon other = (XSPFCommon) obj;
		try {
			return Objects.equals(getAnnotation(), other.getAnnotation())
					&& Objects.equals(getCreator(), other.getCreator())
					&& Objects.equals(extensions(), other.extensions())
					&& Objects.equals(getIdentifier(), other.getIdentifier())
					&& Objects.equals(getImage(), other.getImage()) && Objects.equals(getInfo(), other.getInfo())
					&& Objects.equals(links(), other.links()) && Objects.equals(getLocation(), other.getLocation())
					&& Objects.equals(metas(), other.metas()) && Objects.equals(getLocation(), other.getLocation());
		} catch (XSPFException e) {
			return false;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

}
