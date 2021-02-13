package cz.martlin.xspf.playlist.base;

import java.net.URI;
import java.util.List;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.collections.XSPFLink;
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
		return (XSPFLinks) getAll("link", XSPFLinks::new);
	}
	
	public XSPFLinks links() throws XSPFException {
		return (XSPFLinks) all("link", XSPFLinks::new);
	}

	public void setLinks(XSPFLinks links) throws XSPFException {
		setAll("link", links);
	}

	public XSPFMetas getMetas() throws XSPFException {
		return (XSPFMetas) getAll("meta", XSPFMetas::new);
	}
	
	public XSPFMetas metas() throws XSPFException {
		return (XSPFMetas) all("meta", XSPFMetas::new);
	}
	
	public void setMetas(XSPFMetas metas) throws XSPFException {
		setAll("meta", metas);
	}

	//TODO getExtension, setExtension
	
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
