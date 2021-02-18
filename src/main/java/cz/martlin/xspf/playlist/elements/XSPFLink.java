package cz.martlin.xspf.playlist.elements;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

public class XSPFLink extends XSPFElement {

	public XSPFLink(Element link) {
		super(link);
	}

///////////////////////////////////////////////////////////////////////////

	public URI getRel() throws XSPFException {
		return getUriAttr(Names.REL);
	}

	public void setRel(URI rel) throws XSPFException {
		setUriAttr(Names.REL, rel);
	}

	public URI getContent() throws XSPFException {
		return getUri();
	}

	public void setContent(URI rel) throws XSPFException {
		setUri(rel);
	}

///////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSPFLink [");
		try {
			builder.append("rel=");
			builder.append(getRel());
			builder.append(", content=");
			builder.append(getContent());
		} catch (XSPFException e) {
			builder.append(e);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		try {
			return Objects.hash(getContent(), getRel());
		} catch (XSPFException e) {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XSPFLink other = (XSPFLink) obj;
		try {
			return Objects.equals(getContent(), other.getContent()) && Objects.equals(getRel(), other.getRel());
		} catch (XSPFException e) {
			return false;
		}
	}

}
