package cz.martlin.xspf.playlist.elements;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.base.XSPFElement;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.TextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.ValueToTextMapper;
import cz.martlin.xspf.util.XSPFException;

public class XSPFAttribution extends XSPFElement {

	public XSPFAttribution(Element attribution) {
		super(attribution);
	}
/////////////////////////////////////////////////////////////////////////////////////

	public List<XSPFAttributionItem> list() throws XSPFException {
		Element owner = getElement();
		return UTIL.listChildrenElems(owner) //
				.map(e -> { //
					try {
						return createItem(e);
					} catch (XSPFException ex) {
						throw new RuntimeException(ex);
					}
				}) //
				.collect(Collectors.toList());
	}

	private XSPFAttributionItem createItem(Element elem) throws XSPFException {
		String element = elem.getTagName();
		String value = UTIL.getElementValueOrNull(elem, TextToValueMapper.TEXT_TO_STRING);

		return new XSPFAttributionItem(element, value);
	}

	public void add(XSPFAttributionItem item) throws XSPFException {
		add(item.element, item.value);
	}

	public void add(String element, String value) throws XSPFException {
		Element owner = getElement();
		Element child = UTIL.getOrCreateChildElem(owner, element);

		// FIXME the specification says it shall be inserted at the top, not appended

		UTIL.setElementValue(child, value, ValueToTextMapper.STRING_TO_TEXT);
	}

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

	public static class XSPFAttributionItem {
		public final String element;
		public final String value;

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
