package cz.martlin.xspf.playlist.collections;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFExtension;

public class XSPFExtensions extends XSPFCollection<XSPFExtension> {

	public XSPFExtensions(Element container) {
		super(container);
	}

	@Override
	protected XSPFExtension create(Element child) {
		return new XSPFExtension(child, null); //FIXME application
	}

	@Override
	protected String elemName() {
		return "extension";
	}

	/////////////////////////////////////////////////////////////////////////////////////

	//TODDO createExtensionFor(application)

}
