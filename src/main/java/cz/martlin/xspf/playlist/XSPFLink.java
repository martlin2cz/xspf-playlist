package cz.martlin.xspf.playlist;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.util.XSPFException;

public class XSPFLink extends XSPFElement {

	private final Element link;

	public XSPFLink(Element link) {
		super();
		Objects.requireNonNull(link, "The link element has to be specified");
		this.link = link;
	}

	@Override
	protected Element getElement() {
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
