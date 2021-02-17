package cz.martlin.xspf.playlist.elements;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.XSPFException;

public class XSPFExtension extends XSPFElement {
	
	public XSPFExtension(Element extension) {
		super(extension);
	}

	public URI getApplication() throws XSPFException {
		return getUriAttr("application");
	}

	public void setApplication(URI application) throws XSPFException {
		setUriAttr("application", application);
	}
	
	
}
