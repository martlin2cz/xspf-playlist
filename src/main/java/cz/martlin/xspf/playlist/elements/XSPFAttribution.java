package cz.martlin.xspf.playlist.elements;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.ExceptionWrapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NonNullTextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NonNullValueToTextMapper;
import cz.martlin.xspf.util.XSPFException;

/**
 * An attribution element.
 * 
 * @author martin
 *
 */
public class XSPFAttribution extends XSPFElement {

	/**
	 * Creates instance.
	 * 
	 * @param attribution the attribution element
	 */
	public XSPFAttribution(Element attribution) {
		super(attribution);
	}
/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Lists the items.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	public List<XSPFAttributionItem> list() throws XSPFException {
		Element owner = getElement();
		return UTIL.listChildrenElems(owner) //
				.map(ExceptionWrapper.wrapFunction(e -> createItem(e))) //
				.collect(Collectors.toList());
	}

	/**
	 * Creates new item.
	 * 
	 * @param elem
	 * @return
	 * @throws XSPFException
	 */
	private XSPFAttributionItem createItem(Element elem) throws XSPFException {
		String element = elem.getTagName();
		String value = UTIL.getElementValueOrNull(elem, NonNullTextToValueMapper.TEXT_TO_STRING);

		return new XSPFAttributionItem(element, value);
	}

	/**
	 * Adds given item.
	 * 
	 * @param item
	 * @throws XSPFException
	 */
	public void add(XSPFAttributionItem item) throws XSPFException {
		add(item.element, item.value);
	}

	/**
	 * Adds item with given element name and value.
	 * 
	 * @param element
	 * @param value
	 * @throws XSPFException
	 */
	public void add(String element, String value) throws XSPFException {
		Element owner = getElement();
		Element child = UTIL.getOrCreateChildElem(owner, element);

		// FIXME the specification says it shall be inserted at the top, not appended

		UTIL.setElementValue(child, value, NonNullValueToTextMapper.STRING_TO_TEXT);
	}

	// TODO remove item

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSPFAttribution [");
		try {
			builder.append(list());
		} catch (XSPFException e) {
			builder.append(e);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		try {
			return Objects.hash(list());
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
		XSPFAttribution other = (XSPFAttribution) obj;
		try {
			return Objects.equals(list(), other.list());
		} catch (XSPFException e) {
			return false;
		}
	}

/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The attribution item is just an element name with such element's content.
	 * 
	 * @author martin
	 *
	 */
	public static class XSPFAttributionItem {
		/**
		 * Th element name.
		 */
		public final String element;
		/**
		 * The element value.
		 */
		public final String value;

		/**
		 * Creates the attribution item for the element of given name and contents.
		 * 
		 * @param element
		 * @param value
		 */
		public XSPFAttributionItem(String element, String value) {
			super();
			this.element = element;
			this.value = value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(element, value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			XSPFAttributionItem other = (XSPFAttributionItem) obj;
			return Objects.equals(element, other.element) && Objects.equals(value, other.value);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(element);
			builder.append("=");
			builder.append(value);
			return builder.toString();
		}

	}

}
