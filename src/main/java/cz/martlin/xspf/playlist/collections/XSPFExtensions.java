package cz.martlin.xspf.playlist.collections;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

/**
 * An collection of the {@link XSPFExtension}.
 * 
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.13
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.13
 * @author martin
 *
 */
public class XSPFExtensions extends XSPFCollection<XSPFExtension> {

	/**
	 * Creates instance.
	 * 
	 * @param container either the playlist or track element
	 */
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

	/**
	 * Creates (empty) extension.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	public XSPFExtension createExtension() throws XSPFException {
		return createNew();
	}

	/**
	 * Creates new extension for the given application.
	 * 
	 * @param application
	 * @return
	 * @throws XSPFException
	 */
	public XSPFExtension createExtension(URI application) throws XSPFException {
		XSPFExtension extension = createNew();
		extension.setApplication(application);
		return extension;
	}

	/**
	 * Finds the extension for the given application.
	 * 
	 * @param application
	 * @return
	 * @throws XSPFException
	 */
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
