package cz.martlin.xspf.playlist;

import org.w3c.dom.Element;

public class XSPFAttribution extends XSPFBase {

	private final Element element;

	public XSPFAttribution(Element element) {
		super();
		this.element = element;
	}

	@Override
	public Element getElement() {
		return this.element;
	}
	
	//TODO ?
	

}
