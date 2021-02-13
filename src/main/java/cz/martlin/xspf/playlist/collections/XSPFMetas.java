package cz.martlin.xspf.playlist.collections;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFMeta;

public class XSPFMetas extends XSPFCollection<XSPFMeta> {

	public XSPFMetas(Element container) {
		super("meta", container);
	}

	@Override
	protected XSPFMeta create(Element meta) {
		return new XSPFMeta(meta);
	}

}
