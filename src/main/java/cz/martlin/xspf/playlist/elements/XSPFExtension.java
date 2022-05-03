package cz.martlin.xspf.playlist.elements;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFElement;

public class XSPFExtension extends XSPFElement {
	private final URI application;
	
	public XSPFExtension(Element extension, URI application) {
		super(extension);
		this.application = application;
	}

	public URI getApplication() {
		return application;
	}
	
	//TODO application
	
	
}
