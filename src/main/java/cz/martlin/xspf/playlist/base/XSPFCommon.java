package cz.martlin.xspf.playlist.base;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.collections.XSPFLinks;
import cz.martlin.xspf.playlist.collections.XSPFMetas;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.util.XSPFException;

public abstract class XSPFCommon extends XSPFElement {

	public XSPFCommon(Element element) {
		super(element);
	}

	///////////////////////////////////////////////////////////////////////////

	public URI getLocation() throws XSPFException {
		return getUri("location");
	}

	public void setLocation(URI location) throws XSPFException {
		setUri("location", location);
	}

	public URI getIdentifier() throws XSPFException {
		return getUri("identifier");
	}

	public void setIdentifier(URI identifier) throws XSPFException {
		setUri("identifier", identifier);
	}

	public String getTitle() throws XSPFException {
		return getStr("title");
	}

	public void setTitle(String title) throws XSPFException {
		setStr("title", title);
	}

	public String getCreator() throws XSPFException {
		return getStr("creator");
	}

	public void setCreator(String creator) throws XSPFException {
		setStr("creator", creator);
	}

	public String getAnnotation() throws XSPFException {
		return getStr("annotation");
	}

	public void setAnnotation(String annotation) throws XSPFException {
		setStr("annotation", annotation);
	}

	public URI getInfo() throws XSPFException {
		return getUri("info");
	}

	public void setInfo(URI info) throws XSPFException {
		setUri("info", info);
	}

	public URI getImage() throws XSPFException {
		return getUri("image");
	}

	public void setImage(URI image) throws XSPFException {
		setUri("image", image);
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
	
	public void setMetas(XSPFExtensions extensions) throws XSPFException {
		setCollection(extensions);
	}

	@Deprecated
	public XSPFExtension extension(URI application) throws XSPFException {
		Element elem = getElement();
		//TODO getOrCreateElem with application attr set to specified value
		Element extensionElem = UTIL.getOrCreateChildElem(elem, "extension");
		return new XSPFExtension(extensionElem, application);
	}
	
//	public XSPFExtension setExtension(String application) throws XSPFException {
//		Element elem = getElement();
//		return util.getOrCreateChildElem(elem, "extension");
//	}

	///////////////////////////////////////////////////////////////////////////////////////////////

}
