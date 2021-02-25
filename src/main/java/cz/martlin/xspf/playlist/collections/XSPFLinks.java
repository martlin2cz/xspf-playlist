package cz.martlin.xspf.playlist.collections;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFLink;
import cz.martlin.xspf.util.ExceptionWrapper;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

/**
 * An collection of the {@link XSPFLink}s.
 * 
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11
 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11
 * @author martin
 *
 */
public class XSPFLinks extends XSPFCollection<XSPFLink> {

	/**
	 * Creates instance.
	 * 
	 * @param container either the playlist or track element
	 */
	public XSPFLinks(Element container) {
		super(container);
	}

	@Override
	protected String elemName() {
		return Names.LINK;
	}

	@Override
	protected XSPFLink create(Element link) {
		return new XSPFLink(link);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates new (empty) link.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	public XSPFLink createLink() throws XSPFException {
		return createNew();
	}

	/**
	 * Creates link with given rel and content.
	 * 
	 * @param rel
	 * @param content
	 * @return
	 * @throws XSPFException
	 */
	public XSPFLink createLink(URI rel, URI content) throws XSPFException {
		XSPFLink link = createNew();
		link.setRel(rel);
		link.setContent(content);
		return link;
	}
/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns link having the given rel. Null if no such.
	 * 
	 * @param rel
	 * @return
	 * @throws XSPFException
	 */
	public XSPFLink link(URI rel) throws XSPFException {
		return list() //
				.filter(ExceptionWrapper.wrapPredicate( //
						l -> rel.equals(l.getRel()))) //
				.findAny().orElse(null);
	}

}
