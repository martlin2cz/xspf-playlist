package cz.martlin.xspf.playlist;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XMLDocumentUtility;
import cz.martlin.xspf.util.XSPFException;

public abstract class XSPFCommon extends XSPFBase {

	public XSPFCommon() {
		super();
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
		setUri("info", identifier);
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

	public List<XSPFLink> getLinks() throws XSPFException {
		return getAll("link", (e) -> new XSPFLink(e));
	}
	
	public XSPFLink createLink() throws XSPFException {
		return createOne("link", (e) -> new XSPFLink(e));
	}

	public void setLinks(List<XSPFLink> links) throws XSPFException {
		setAll("link", links);
	}

	public List<XSPFMeta> getMetas() throws XSPFException {
		return getAll("meta", (e) -> new XSPFMeta(e));
	}
	
	public XSPFMeta createMeta() throws XSPFException {
		return createOne("meta", (e) -> new XSPFMeta(e));
	}
	
	public void setMetas(List<XSPFMeta> metas) throws XSPFException {
		setAll("meta", metas);
	}

	public XSPFExtension getExtension(URI application) throws XSPFException {
		Element elem = getElement();
		Element extensionElem = UTIL.getOrCreateChildElem(elem, "extension");
		return new XSPFExtension(extensionElem, application);
	}
	
//	public XSPFExtension setExtension(String application) throws XSPFException {
//		Element elem = getElement();
//		return util.getOrCreateChildElem(elem, "extension");
//	}

	///////////////////////////////////////////////////////////////////////////////////////////////

}
