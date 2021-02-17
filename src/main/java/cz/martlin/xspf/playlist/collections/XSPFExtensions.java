package cz.martlin.xspf.playlist.collections;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.util.XSPFException;

public class XSPFExtensions extends XSPFCollection<XSPFExtension> {

	public XSPFExtensions(Element container) {
		super(container);
	}

	@Override
	protected XSPFExtension create(Element child) {
		return new XSPFExtension(child);
	}

	@Override
	protected String elemName() {
		return "extension";
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public XSPFExtension createExtension(URI application) throws XSPFException {
		XSPFExtension extension = createNew();
		extension.setApplication(application);
		return extension;
	}

}
