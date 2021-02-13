package cz.martlin.xspf.playlist.collections;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;

public class XSPFLinks extends XSPFCollection<XSPFLink> {

	public XSPFLinks(Element links) {
		super("link", links);
	}

	@Override
	protected XSPFLink create(Element link) {
		return new XSPFLink(link);
	}

}
