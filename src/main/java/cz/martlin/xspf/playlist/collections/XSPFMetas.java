package cz.martlin.xspf.playlist.collections;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFMeta;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

/**
 * An collection fo the {@link XSPFMeta}s.
 * 
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12
 * @author martin
 *
 */
public class XSPFMetas extends XSPFCollection<XSPFMeta> {

	/**
	 * Creates instance.
	 * 
	 * @param container either the playlist or track element
	 */
	public XSPFMetas(Element container) {
		super(container);
	}

	@Override
	protected String elemName() {
		return Names.META;
	}

	@Override
	protected XSPFMeta create(Element meta) {
		return new XSPFMeta(meta);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates (empty) meta.
	 * 
	 * @return
	 * @throws XSPFException 
	 */
	public XSPFMeta createMeta() throws XSPFException {
		return createNew();
	}

	/**
	 * Creates the meta with given rel and content.
	 * 
	 * @param rel
	 * @param content
	 * @return
	 * @throws XSPFException
	 */
	public XSPFMeta createMeta(URI rel, String content) throws XSPFException {
		XSPFMeta meta = createNew();
		meta.setRel(rel);
		meta.setContent(content);
		return meta;
	}
}
