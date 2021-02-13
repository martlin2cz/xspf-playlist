package cz.martlin.xspf.playlist;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.playlist.base.XSPFElement;

public class XSPFAttribution extends XSPFElement {

	private final Element attribution;

	public XSPFAttribution(Element element) {
		super();
		this.attribution = element;
	}

	@Override
	protected Element getElement() {
		return attribution;
	}
	
	//TODO ?
	

}
