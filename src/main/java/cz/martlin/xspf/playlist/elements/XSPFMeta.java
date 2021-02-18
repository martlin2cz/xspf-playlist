package cz.martlin.xspf.playlist.elements;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

public class XSPFMeta extends XSPFElement {

	public XSPFMeta(Element meta) {
		super(meta);
	}

///////////////////////////////////////////////////////////////////////////

	public URI getRel() throws XSPFException {
		return getUriAttr(Names.REL);
	}

	public void setRel(URI rel) throws XSPFException {
		setUriAttr(Names.REL, rel);
	}

	public String getContent() throws XSPFException {
		return getStr();
	}

	public void setContent(String content) throws XSPFException {
		setStr(content);
	}

///////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSPFMeta [");
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
		XSPFMeta other = (XSPFMeta) obj;
		try {
			return Objects.equals(getContent(), other.getContent()) && Objects.equals(getRel(), other.getRel());
		} catch (XSPFException e) {
			return false;
		}
	}

}
