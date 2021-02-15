package cz.martlin.xspf.playlist.elements;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.XSPFException;

public class XSPFMeta extends XSPFElement {

	public XSPFMeta(Element meta) {
		super(meta);
	}


///////////////////////////////////////////////////////////////////////////

	public URI getRel() throws XSPFException {
		return getUriAttr("rel");
	}

	public void setRel(URI rel) throws XSPFException {
		setUriAttr("rel", rel);
	}

	public String getContent() throws XSPFException {
		return getStr();
	}

	public void setContent(String content) throws XSPFException {
		setStr(content);
	}
}
