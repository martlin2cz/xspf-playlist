package cz.martlin.xspf.playlist.elements;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

/**
 * The meta element.
 * 
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12
 * @author martin
 *
 */
public class XSPFMeta extends XSPFElement {

	/**
	 * Creates instance.
	 * 
	 * @param meta the meta element.
	 */
	public XSPFMeta(Element meta) {
		super(meta);
	}

///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the meta rel.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12.1.1
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12.1.1
	 * @return
	 * @throws XSPFException
	 */
	public URI getRel() throws XSPFException {
		return getUriAttr(Names.REL);
	}

	/**
	 * Sets the meta rel.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12.1.1
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12.1.1
	 * @param rel
	 * @throws XSPFException
	 */
	public void setRel(URI rel) throws XSPFException {
		setUriAttr(Names.REL, rel);
	}

	/**
	 * Returns the meta content.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12.2
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12.2
	 * @return
	 * @throws XSPFException
	 */
	public String getContent() throws XSPFException {
		return getStr();
	}

	/**
	 * Sets the meta content.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12.2
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12.2
	 * @param content
	 * @throws XSPFException
	 */
	public void setContent(String content) throws XSPFException {
		setStr(content);
	}

///////////////////////////////////////////////////////////////////////////

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

}
