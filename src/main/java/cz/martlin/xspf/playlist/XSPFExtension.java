package cz.martlin.xspf.playlist;

import java.net.URI;

import org.w3c.dom.Element;

public class XSPFExtension extends XSPFBase {
	private final Element element;
	private final URI application;
	
	public XSPFExtension(Element element, URI application) {
		super();
		this.element = element;
		this.application = application;
	}
	
	@Override
	public Element getElement() {
		return this.element;
	}
	
	public URI getApplication() {
		return application;
	}
	
	//TODO application
	
	
}
