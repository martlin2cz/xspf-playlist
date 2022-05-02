package cz.martlin.xspf.playlist;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFException;

public class XSPFMeta extends XSPFBase {

	private final Element link;

	public XSPFMeta(Element link) {
		super();
		Objects.requireNonNull(link, "The link element has to be specified");
		this.link = link;
	}

	@Override
	public Element getElement() {
		return link;
	}

///////////////////////////////////////////////////////////////////////////

	public URI getRel() throws XSPFException {
		return getUri("rel");
	}

	public void setRel(URI rel) throws XSPFException {
		setUri("rel", rel);
	}

	public String getContent() throws XSPFException {
		return getStr();
	}

	public void setContent(String content) throws XSPFException {
		setStr(content);
	}
}
