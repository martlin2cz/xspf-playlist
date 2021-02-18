package cz.martlin.xspf.playlist.base;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFException;

/**
 * An abstract collection of sibling {@link XSPFElement}s of the same type
 * (element name, type). Features: create brand new element, add element, remove
 * element, list all elements.
 * 
 * @author martin
 *
 * @param <E>
 */
public abstract class XSPFCollection<E extends XSPFElement> extends XSPFElement {

	/**
	 * Creates instance.
	 * 
	 * @param container The common parent element of all the elements inside of this
	 *                  collection.
	 */
	public XSPFCollection(Element container) {
		super(container);
	}

	/**
	 * Creates (but does not add to this collection yet) new element.
	 * 
	 * @return
	 * @throws XSPFException 
	 */
	public E createNew() throws XSPFException {
		Element container = getElement();
		String elemName = elemName();
		Element elem = UTIL.createNewElement(container, elemName);
		return create(elem);
	}

	/**
	 * Creates the {@link XSPFElement} for the given XML element.
	 * 
	 * @param child
	 * @return
	 */
	protected abstract E create(Element child);

	/**
	 * Returns the element name of the children.
	 * 
	 * @return
	 */
	protected abstract String elemName();

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Adds the given element to this collection.
	 * 
	 * @param element
	 * @throws XSPFException 
	 */
	public void add(E element) throws XSPFException {
		Element container = getElement();
		Element elem = element.getElement();
		UTIL.addChildElement(container, elem);
	}

	/**
	 * Removes the given element from this collection.
	 * 
	 * @param element
	 * @throws XSPFException 
	 */
	public void remove(E element) throws XSPFException {
		Element container = getElement();
		Element elem = element.getElement();
		UTIL.removeChildElement(container, elem);
	}

	/**
	 * Returns iterable version of this collection.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	public Iterable<E> iterate() throws XSPFException {
		return list().collect(Collectors.toUnmodifiableList());
	}

	/**
	 * Returns the {@link Stream} containing all the elements inside of this
	 * collection.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	public Stream<E> list() throws XSPFException {
		Element container = getElement();
		String elemName = elemName();
		return UTIL.listChildrenElems(container, elemName) //
				.map(e -> create(e));
	}

	/**
	 * Returns the count of elements in this collection.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	public int size() throws XSPFException {
		return (int) list().count();
	}

	// TODO isEmpty, hasSome

///////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		try {
			return Objects.hash(list().toArray());
		} catch (XSPFException e) {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XSPFCollection<?> other = (XSPFCollection<?>) obj;
		try {
			return Objects.deepEquals(list().toArray(), other.list().toArray());
		} catch (XSPFException e) {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSPFCollection[");
		try {
			for (E item : iterate()) {
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
