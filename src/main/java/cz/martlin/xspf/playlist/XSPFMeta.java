package cz.martlin.xspf.playlist;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.util.XSPFException;

public class XSPFMeta extends XSPFElement {

	private final Element meta;

	public XSPFMeta(Element meta) {
		super();
		Objects.requireNonNull(meta, "The meta element has to be specified");
		this.meta = meta;
	}

	@Override
	protected Element getElement() {
		return meta;
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
