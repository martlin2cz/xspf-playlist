package cz.martlin.xspf.playlist.collections;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFCollection;
import cz.martlin.xspf.playlist.elements.XSPFMeta;
import cz.martlin.xspf.util.Names;
import cz.martlin.xspf.util.XSPFException;

public class XSPFMetas extends XSPFCollection<XSPFMeta> {

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
	
	public XSPFMeta createMeta() {
		return createNew();
	}
	
	public XSPFMeta createMeta(URI rel, String content)throws XSPFException {
		XSPFMeta meta = createNew();
		meta.setRel(rel);
		meta.setContent(content);
		return meta;
	}
}
