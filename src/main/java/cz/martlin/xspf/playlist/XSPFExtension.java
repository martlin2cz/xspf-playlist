package cz.martlin.xspf.playlist;

import java.net.URI;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XSPFExtension extends XSPFElement {
	private final Element extension;
	private final URI application;
	
	public XSPFExtension(Element element, URI application) {
		super();
		this.extension = element;
		this.application = application;
	}
	
	@Override
	protected Element getElement() {
		return extension;
	}
	
	public URI getApplication() {
		return application;
	}
	
	//TODO application
	
	
}
