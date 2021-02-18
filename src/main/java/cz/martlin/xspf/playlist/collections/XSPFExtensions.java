package cz.martlin.xspf.playlist.collections;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.util.Names;
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
		return Names.EXTENSION;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public XSPFExtension createExtension(URI application) throws XSPFException {
		XSPFExtension extension = createNew();
		extension.setApplication(application);
		return extension;
	}

	public XSPFExtension find(URI application) throws XSPFException {
		return list() //
				.filter(e -> {
					try {
						return application.equals(e.getApplication());
					} catch (XSPFException ex) {
						throw new RuntimeException(ex);
					}
				}) //
				.findAny().get(); //
	}

}
