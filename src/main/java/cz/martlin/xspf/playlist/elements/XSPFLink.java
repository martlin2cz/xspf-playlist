package cz.martlin.xspf.playlist.elements;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

/**
 * The link element.
 * 
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11
 * @author martin
 *
 */
public class XSPFLink extends XSPFElement {

	/**
	 * Create instance.
	 * 
	 * @param link the link element.
	 */
	public XSPFLink(Element link) {
		super(link);
	}

///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the link rel.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11.1.1
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11.1.1
	 * @return
	 * @throws XSPFException
	 */
	public URI getRel() throws XSPFException {
		return getUriAttr(Names.REL);
	}

	/**
	 * Sets the link rel.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11.1.1
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11.1.1
	 * @param rel
	 * @throws XSPFException
	 */
	public void setRel(URI rel) throws XSPFException {
		setUriAttr(Names.REL, rel);
	}

	/**
	 * Gets the link content.
	 *
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11.2
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11.2
	 * @return
	 * @throws XSPFException
	 */
	public URI getContent() throws XSPFException {
		return getUri();
	}

	/**
	 * Sets the link content.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11.2
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11.2
	 * @param rel
	 * @throws XSPFException
	 */
	public void setContent(URI rel) throws XSPFException {
		setUri(rel);
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
		XSPFLink other = (XSPFLink) obj;
		try {
			return Objects.equals(getContent(), other.getContent()) && Objects.equals(getRel(), other.getRel());
		} catch (XSPFException e) {
			return false;
		}
	}

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

}
