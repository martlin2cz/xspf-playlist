package cz.martlin.xspf.playlist;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFException;

public class XSPFLink extends XSPFBase {

	private final Element link;

	public XSPFLink(Element link) {
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

	public URI getContent() throws XSPFException {
		return getUri();
	}

	public void setContent(URI rel) throws XSPFException {
		setUri(rel);
	}

}
