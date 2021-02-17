package cz.martlin.xspf.playlist.collections;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFLink;
import cz.martlin.xspf.util.XSPFException;

public class XSPFLinks extends XSPFCollection<XSPFLink> {

	public XSPFLinks(Element links) {
		super(links);
	}

	@Override
	protected String elemName() {
		return "link";
	}

	@Override
	protected XSPFLink create(Element link) {
		return new XSPFLink(link);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public XSPFLink createLink() {
		return createNew();
	}

	public XSPFLink createLink(URI rel, URI content)throws XSPFException {
		XSPFLink link = createNew();
		link.setRel(rel);
		link.setContent(content);
		return link;
	}

}
