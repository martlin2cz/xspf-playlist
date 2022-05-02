package cz.martlin.xspf.playlist;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFDocumentUtility;
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

	public void setLinks(List<XSPFLink> links) throws XSPFException {
		setAll("link", links);
	}

	public List<Element> getMetas() throws XSPFException {
		Element elem = getElement();
		return util.listChildrenElems(elem, "meta");
	}

	public Element getExtension() throws XSPFException {
		Element elem = getElement();
		return util.getOrCreateChildElem(elem, "extension");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

}
