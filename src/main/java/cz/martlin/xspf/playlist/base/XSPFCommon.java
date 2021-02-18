package cz.martlin.xspf.playlist.base;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.collections.XSPFLinks;
import cz.martlin.xspf.playlist.collections.XSPFMetas;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

public abstract class XSPFCommon extends XSPFElement {

	public XSPFCommon(Element element) {
		super(element);
	}

	///////////////////////////////////////////////////////////////////////////

	public URI getLocation() throws XSPFException {
		return getUri(Names.LOCATION);
	}

	public void setLocation(URI location) throws XSPFException {
		setUri(Names.LOCATION, location);
	}

	public URI getIdentifier() throws XSPFException {
		return getUri(Names.IDENTIFIER);
	}

	public void setIdentifier(URI identifier) throws XSPFException {
		setUri(Names.IDENTIFIER, identifier);
	}

	public String getTitle() throws XSPFException {
		return getStr(Names.TITLE);
	}

	public void setTitle(String title) throws XSPFException {
		setStr(Names.TITLE, title);
	}

	public String getCreator() throws XSPFException {
		return getStr(Names.CREATOR);
	}

	public void setCreator(String creator) throws XSPFException {
		setStr(Names.CREATOR, creator);
	}

	public String getAnnotation() throws XSPFException {
		return getStr(Names.ANNOTATION);
	}

	public void setAnnotation(String annotation) throws XSPFException {
		setStr(Names.ANNOTATION, annotation);
	}

	public URI getInfo() throws XSPFException {
		return getUri(Names.INFO);
	}

	public void setInfo(URI info) throws XSPFException {
		setUri(Names.INFO, info);
	}

	public URI getImage() throws XSPFException {
		return getUri(Names.IMAGE);
	}

	public void setImage(URI image) throws XSPFException {
		setUri(Names.IMAGE, image);
	}

	public XSPFLinks getLinks() throws XSPFException {
		return (XSPFLinks) getCollection(XSPFLinks::new);
	}

	public XSPFLinks links() throws XSPFException {
		return (XSPFLinks) collection(XSPFLinks::new);
	}

	public void setLinks(XSPFLinks links) throws XSPFException {
		setCollection(links);
	}

	public XSPFMetas getMetas() throws XSPFException {
		return (XSPFMetas) getCollection(XSPFMetas::new);
	}

	public XSPFMetas metas() throws XSPFException {
		return (XSPFMetas) collection(XSPFMetas::new);
	}

	public void setMetas(XSPFMetas metas) throws XSPFException {
		setCollection(metas);
	}

	public XSPFExtensions getExtensions() throws XSPFException {
		return (XSPFExtensions) getCollection(XSPFExtensions::new);
	}

	public XSPFExtensions extensions() throws XSPFException {
		return (XSPFExtensions) collection(XSPFExtensions::new);
	}

	public void setExtensions(XSPFExtensions extensions) throws XSPFException {
		setCollection(extensions);
	}

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
					&& Objects.equals(getCreator(), other.getCreator()) && Objects.equals(extensions(), other.extensions())
					&& Objects.equals(getIdentifier(), other.getIdentifier())
					&& Objects.equals(getImage(), other.getImage()) && Objects.equals(getInfo(), other.getInfo())
					&& Objects.equals(links(), other.links()) && Objects.equals(getLocation(), other.getLocation())
					&& Objects.equals(metas(), other.metas()) && Objects.equals(getLocation(), other.getLocation());
		} catch (XSPFException e) {
			return false;
		}
	}

//	public XSPFExtension setExtension(String application)throws XSPFException {
//		Element elem = getElement();
//		return util.getOrCreateChildElem(elem, "extension");
//	}

	///////////////////////////////////////////////////////////////////////////////////////////////

}
