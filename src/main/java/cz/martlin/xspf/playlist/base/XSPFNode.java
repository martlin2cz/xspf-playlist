package cz.martlin.xspf.playlist.base;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.util.XMLDocumentUtility;
import cz.martlin.xspf.util.XSPFException;

public abstract class XSPFNode {

	protected static final XMLDocumentUtility UTIL = new XMLDocumentUtility(null, "http://xspf.org/ns/0/");

	public XSPFNode() {
		super();
	}
	

	public abstract Node getNode();
	
/////////////////////////////////////////////////////////////////////////////////////
	

	protected <T extends XSPFElement> T createOne(String name, Function<Element, T> mapper) {
		Node context = getNode();
		Element elem = UTIL.createNewElement(context, name);
		return mapper.apply(elem);
	}

	protected <T extends XSPFElement> T getOne(String name, Function<Element, T> mapper) throws XSPFException {
		Node owner = getNode();
		Element child = UTIL.getChildElem(owner, name);
		return mapper.apply(child);
	}

	protected <T extends XSPFElement> T one(String name, Function<Element, T> mapper) throws XSPFException {
		Node owner = getNode();
		Element child = UTIL.getChildElem(owner, name);
		return mapper.apply(child);
	}
	
	protected <T extends XSPFElement> void setOne(String name, T value) throws XSPFException {
		Node owner = getNode();
		Element newElem = value.getElement();
		UTIL.replaceChildElement(owner, name, newElem);
	}

	protected <T extends XSPFElement> List<T> getAll(String name, Function<Element, T> mapper) throws XSPFException {
		Node owner = getNode();
		return UTIL.listChildrenElems(owner, name).stream() //
				.map(mapper) //
				.collect(Collectors.toList());
	}

	protected <T extends XSPFElement> void setAll(String name, List<T> items) throws XSPFException {
		Node owner = getNode();
		List<Element> elements = items.stream() //
				.map((i) -> i.getElement()) //
				.collect(Collectors.toList());
	
		UTIL.replaceChildElements(owner, name, elements);
	}

}