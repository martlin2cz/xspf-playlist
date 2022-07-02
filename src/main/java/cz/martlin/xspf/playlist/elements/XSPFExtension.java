package cz.martlin.xspf.playlist.elements;

import java.net.URI;
import java.util.Objects;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XMLDocumentUtility;
import cz.martlin.xspf.util.XSPFException;

/**
 * An extension element.
 * 
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.13
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.13
 * @author martin
 *
 */
public class XSPFExtension extends XSPFElement {

	/**
	 * Creates instance.
	 * 
	 * @param extension an extension element
	 */
	public XSPFExtension(Element extension) {
		super(extension);
	}
/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the application.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.13.1.1
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.13.1.1
	 * @return
	 * @throws XSPFException
	 */
	public URI getApplication() throws XSPFException {
		return getUriAttr(Names.APPLICATION);
	}

	/**
	 * Sets the application.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.13.1.1
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.13.1.1
	 * @param application
	 * @throws XSPFException
	 */
	public void setApplication(URI application) throws XSPFException {
		setUriAttr(Names.APPLICATION, application);
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Element getElement() {
		return super.getElement();
	}

	/**
	 * Returns the utility for the given extension.
	 * 
	 * @param nsPrefix the XML namespace prefix
	 * @param nsURI    the XML namespace uri
	 * @return
	 * @throws XSPFException
	 */
	public XMLDocumentUtility getUtility(String nsPrefix, String nsURI) throws XSPFException {
		XMLDocumentUtility util = new XMLDocumentUtility(nsPrefix, nsURI);
		Element elem = getElement();

		util.register(elem);
		return util;
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		try {
			return Objects.hash(getApplication());
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
		XSPFExtension other = (XSPFExtension) obj;
		try {
			return Objects.equals(getApplication(), other.getApplication());
		} catch (XSPFException e) {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSPFExtension [");
		try {
			builder.append("application=");
			builder.append(getApplication());
		} catch (XSPFException e) {
			builder.append(e);
		}
		builder.append("]");
		return builder.toString();
	}

}
