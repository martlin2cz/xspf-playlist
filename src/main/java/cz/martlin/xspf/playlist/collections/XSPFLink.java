package cz.martlin.xspf.playlist.collections;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.XSPFException;

public class XSPFLink extends XSPFElement {


	public XSPFLink(Element link) {
		super(link);
	}

///////////////////////////////////////////////////////////////////////////

	public URI getRel() throws XSPFException {
		return getUriAttr("rel");
	}

	public void setRel(URI rel) throws XSPFException {
		setUriAttr("rel", rel);
	}


	public URI getContent() throws XSPFException {
		return getUri();
	}

	public void setContent(URI rel) throws XSPFException {
		setUri(rel);
	}

}
