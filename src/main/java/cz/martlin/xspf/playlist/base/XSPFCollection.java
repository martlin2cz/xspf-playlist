package cz.martlin.xspf.playlist.base;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public Iterable<T> iterate()throws XSPFException {
		return list().collect(Collectors.toUnmodifiableList());
	}

	public Stream<T> list()throws XSPFException {
		Element container = getElement();
		String elemName = elemName();
		return UTIL.listChildrenElems(container, elemName) //
				.map(e -> create(e));
	}

	public int size()throws XSPFException {
		return (int) list().count();
	}

	// TODO isEmpty, hasSome

///////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSPFCollection[");
		try {
			for (T item : iterate()) {
				builder.append(item);
				builder.append(", ");
			}
		} catch (XSPFException e) {
			builder.append(e);
		}
		builder.append("]");
		return builder.toString();
	}

	/////////////////////////////////////////////////////////////////////////////////////

}
