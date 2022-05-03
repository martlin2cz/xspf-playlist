package cz.martlin.xspf.playlist.base;

import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFException;

public abstract class XSPFCollection<T extends XSPFElement> extends XSPFElement {

	public XSPFCollection(Element container) {
		super(container);
	}

	public T createNew() {
		Element container = getElement();
		String elemName = elemName();
		Element elem = UTIL.createNewElement(container, elemName);
		return create(elem);
	}

	protected abstract T create(Element child);

	protected abstract String elemName();
	
	/////////////////////////////////////////////////////////////////////////////////////

	public void add(T element) {
		Element container = getElement();
		Element elem = element.getElement();
		UTIL.addChildElement(container, elem);
	}

	public void remove(T element) {
		Element container = getElement();
		Element elem = element.getElement();
		UTIL.removeChildElement(container, elem);
	}

	public List<T> list() throws XSPFException {
		Element container = getElement();
		String elemName = elemName();
		return UTIL.listChildrenElems(container, elemName).stream().map(e -> create(e)).collect(Collectors.toList());
	}
	
	public int size() throws XSPFException {
		return list().size();
	}

	@Override
	public String toString() {
		try {
			return "XSPFCollection [list()=" + list() + "]";
		} catch (XSPFException e) {
			return "XSPFCollection [" + e.toString() + "]";
		}
	}
	
	//TODO isEmpty, hasSome

	/////////////////////////////////////////////////////////////////////////////////////

}
