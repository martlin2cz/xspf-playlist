package cz.martlin.xspf.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The xml document utility helper, the actual implementor of the DOM
 * manipulation on the low-level.
 * 
 * @author martin
 *
 */
public class XMLDocumentUtilityHelper {
	/**
	 * The namespace name (prefix). Null value represents the default namespace.
	 */
	private final String nsName;
	/**
	 * The namespace URL.
	 */
	private final String nsURL;

	/**
	 * Creates instance.
	 * 
	 * @param nsName the namespace name (prefix), null to use default namespace
	 * @param nsURL  the namespace URL
	 */
	public XMLDocumentUtilityHelper(String nsName, String nsURL) {
		super();
		this.nsName = nsName;
		this.nsURL = nsURL;
	}

	/**
	 * Sets the namespace declaration attribute to the given element.
	 * 
	 * @param elem
	 * @throws XSPFException
	 */
	public void specifyNSattribute(Element elem) throws XSPFException {
		String attrName = namespaceAttrName();
		String atrrValue = nsURL;

		// we cannot add attribute via the method as it adds namespace to it.
		elem.setAttribute(attrName, atrrValue);
	}

	/**
	 * Creates the namespace declaration attribute name.
	 * 
	 * @return
	 */
	private String namespaceAttrName() {
		String attrName;
		if (nsName != null) {
			attrName = "xmlns:" + nsName;
		} else {
			attrName = "xmlns";
		}
		return attrName;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// create/remove/get child (public)

	/**
	 * Creates new element of the given name in the given context (any node or the
	 * whole document).
	 * 
	 * @param context
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Element createNew(Node context, String elemName) throws XSPFException {
		try {
			Document document = documentOfNode(context);
			return createNewElement(document, elemName);
		} catch (Exception e) {
			throw new XSPFException("Canot create new element", e);
		}
	}

	/**
	 * Creates (and appends) child element of the given owner.
	 * 
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Element createChild(Node owner, String elemName) throws XSPFException {
		try {
			return createChildElement(owner, elemName);
		} catch (Exception e) {
			throw new XSPFException("Canot create child element", e);
		}
	}

	/**
	 * Returns the child element of the given owner. Returns null if no such, fails
	 * if more.
	 * 
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Element getChildOrNull(Node owner, String elemName) throws XSPFException {
		try {
			return getChildElement(owner, elemName, false);
		} catch (Exception e) {
			throw new XSPFException("Canot get (or null) child element", e);
		}
	}

	/**
	 * Returns the child element of the given owner. Creates and appends if no such,
	 * fails if more.
	 * 
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Element getOrCreateChild(Node owner, String elemName) throws XSPFException {
		try {
			Element child = getChildElement(owner, elemName, false);
			if (child == null) {
				child = createChildElement(owner, elemName);
			}
			return child;
		} catch (Exception e) {
			throw new XSPFException("Canot get or create child element", e);
		}
	}

	/**
	 * Returns the child elements of the given owner (having given name).
	 * 
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Stream<Element> getChildren(Node owner, String elemName) throws XSPFException {
		try {
			return getChildrenElements(owner, elemName);
		} catch (Exception e) {
			throw new XSPFException("Canot get children elements", e);
		}
	}

	/**
	 * Returns true whether the given owner has any child elements having the given
	 * name.
	 * 
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public boolean hasChildren(Node owner, String elemName) throws XSPFException {
		try {
			return getChildrenElements(owner, elemName) //
					.findAny().isPresent();
		} catch (Exception e) {
			throw new XSPFException("Canot figure out existence of children elements", e);
		}
	}

	/**
	 * Adds the given child element.
	 * 
	 * @param owner
	 * @param child
	 * @throws XSPFException
	 */
	public void addChild(Node owner, Element child) throws XSPFException {
		try {
			addChildElement(owner, child);
		} catch (Exception e) {
			throw new XSPFException("Canot add child element", e);
		}
	}

	/**
	 * Removes the given child element.
	 * 
	 * @param owner
	 * @param child
	 * @throws XSPFException
	 */
	public void removeChild(Node owner, Element child) throws XSPFException {
		try {
			removeChildElement(owner, child);
		} catch (Exception e) {
			throw new XSPFException("Canot remove child element", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// create/remove/get child (internal)

	/**
	 * Returns the document owning the given context node.
	 * 
	 * @param node
	 * @return
	 */
	private Document documentOfNode(Node node) {
		if (node.getNodeType() == Node.DOCUMENT_NODE) {
			return (Document) node;
		}

		// TODO if doc already, use the doc
		// TODO if attr, test
		return node.getOwnerDocument();
	}

	/**
	 * Creates new element of the given name.
	 * 
	 * @param document
	 * @param elemName
	 * @return
	 */
	private Element createNewElement(Document document, String elemName) {
		return document.createElement(fullName(elemName));
	}

	/**
	 * Creates (and appends) the new child element of the given name in the given
	 * owner.
	 * 
	 * @param owner
	 * @param elemName
	 * @return
	 */
	private Element createChildElement(Node owner, String elemName) {
		Document document = documentOfNode(owner);
		Element elem = createNewElement(document, elemName);
		addChildElement(owner, elem);
		return elem;
	}

	/**
	 * Adds (appends) the given element to the given owner.
	 * 
	 * @param owner
	 * @param elem
	 */
	private void addChildElement(Node owner, Element elem) {
		owner.appendChild(elem);
	}

	/**
	 * Removes the given element from the given owner.
	 * 
	 * @param owner
	 * @param elem
	 */
	private void removeChildElement(Node owner, Element elem) {
		owner.removeChild(elem);
	}

	/**
	 * Returns one child element of given name (if not null) of the given owner. If
	 * fail on missing is true, fails if no such. Otherwise returns null. If more
	 * than one of such elements, fails anyway.
	 * 
	 * @param owner
	 * @param elemName
	 * @param failOnMissing
	 * @return
	 * @throws XSPFException
	 */
	private Element getChildElement(Node owner, String elemName, boolean failOnMissing) throws XSPFException {
		Stream<Element> children = listChildren(owner, elemName);
		List<Element> list = children.collect(Collectors.toList());

		if (list.size() < 1) {
			if (failOnMissing) {
				throw new XSPFException("No such element " + elemName);
			} else {
				return null;
			}
		}
		if (list.size() > 1) {
			throw new XSPFException("More than one " + elemName + " elements");
		}

		Node child = list.get(0);
		Element childElem = (Element) child;
		return childElem;
	}

	/**
	 * Returns the all child elements of given name (if not null) of the given
	 * owner.
	 * 
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	private Stream<Element> getChildrenElements(Node owner, String elemName) throws XSPFException {
		return listChildren(owner, elemName);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// list children

	/**
	 * Lists all the children nodes of the given container, having the given name
	 * (if provided).
	 * 
	 * @param container
	 * @param elemName
	 * @return
	 */
	private Stream<Element> listChildren(Node container, String elemName) {
		NodeList children = container.getChildNodes();

		return IntStream.range(0, children.getLength()) //
				.mapToObj(i -> children.item(i)) //
				.filter(n -> n.getNodeType() == Node.ELEMENT_NODE) //
				.map(n -> (Element) n) //
				.filter(e -> isElemOfName(e, elemName)) //
				.collect(Collectors.toList()).stream(); // sorryjako
	}

	/**
	 * Returns true, whether the given element has the specified name. If no name
	 * specified, returns true anyway.
	 * 
	 * @param elem
	 * @param elemName
	 * @return
	 */
	private boolean isElemOfName(Element elem, String elemName) {
		if (elemName == null) {
			return true;
		} else {
			return elem.getTagName().equals(fullName(elemName));
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// get/set element content/attr

	/**
	 * Returns the value of the given element's content. Fails if no such.
	 * 
	 * @param elem
	 * @return
	 * @throws XSPFException
	 */
	public String getElementValue(Element elem) throws XSPFException {
		String content = elem.getTextContent();
		if (content == null) {
			throw new XSPFException("The element " + elem.getTagName() + " has no content");
		}
		return content;
	}

	/**
	 * Returns the value of the given element's content. Returns null if no such.
	 * 
	 * @param elem
	 * @return
	 * @throws XSPFException
	 */
	public String getElementValueOrNull(Element elem) throws XSPFException {
		return elem.getTextContent();
	}

	/**
	 * Sets the value of the given element's content.
	 * 
	 * @param elem
	 * @param value
	 */
	public void setElementValue(Element elem, String value) {
		elem.setTextContent(value);
	}

	/**
	 * Returns the value of the given element's attribute. Fails if no such.
	 * 
	 * @param elem
	 * @param attrName
	 * @return
	 * @throws XSPFException
	 */
	public String getAttrValue(Element elem, String attrName) throws XSPFException {
		String content = elem.getAttribute(/* NS(nsURL, */ fullName(attrName));
		if (content == null) {
			throw new XSPFException("The attribute " + attrName + " does not exist");
		}
		return content;
	}

	/**
	 * Returns the value of the given element's attribute. Returns null if no such.
	 * 
	 * @param elem
	 * @param attrName
	 * @return
	 * @throws XSPFException
	 */
	public String getAttrValueOrNull(Element elem, String attrName) throws XSPFException {
		if (elem.hasAttribute(fullName(attrName))) {
			return elem.getAttribute(/* NS(nsURL, */ fullName(attrName));
		} else {
			return null;
		}
	}

	/**
	 * Sets the value of the given element's attribute.
	 * 
	 * @param elem
	 * @param attrName
	 * @param value
	 */
	public void setAttrValue(Element elem, String attrName, String value) {
		elem.setAttribute(/* NS(nsURL, */ fullName(attrName), value);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// text <-> value

	/**
	 * Converts the given text to value of particular type by the given mapper. If
	 * the text is null, null is returned immediatelly.
	 * 
	 * @param <T>
	 * @param text
	 * @param mapper
	 * @return
	 * @throws XSPFException
	 */
	public <T> T textToValue(String text, NonNullTextToValueMapper<T> mapper) throws XSPFException {
		try {
			if (text == null) {
				return null;
			}

			return mapper.textToValue(text.trim());
		} catch (Exception e) {
			throw new XSPFException("Cannot convert " + text + " value", e);
		}
	}

	/**
	 * Converts the given value of particular type to text by the given mapper. If
	 * the value is null, null is returned immediatelly.
	 * 
	 * @param <T>
	 * @param value
	 * @param mapper
	 * @return
	 * @throws XSPFException
	 */
	public <T> String valueToText(T value, NonNullValueToTextMapper<T> mapper) throws XSPFException {
		try {
			if (value == null) {
				return null;
			}

			return mapper.valueToText(value);
		} catch (Exception e) {
			throw new XSPFException("Cannot convert " + value + " value", e);
		}
	}

	/**
	 * An mapping of the text (never null) obtained from the XML file to some particular java
	 * type. To convert to string, you can use {@link #TEXT_TO_STRING} mapping.
	 * 
	 * @author martin
	 *
	 * @param <T>
	 */
	@FunctionalInterface
	public interface NonNullTextToValueMapper<T> {
		/**
		 * An identity (i.e. empty) mapping.
		 */
		public static final NonNullTextToValueMapper<String> TEXT_TO_STRING = (s) -> s;

		/**
		 * Converts the given (non-null) text to the particular value.
		 * 
		 * @param text
		 * @return
		 * @throws Exception
		 */
		T textToValue(String text) throws Exception;
	}

	/**
	 * An mapping of the particular java object (never null) to text to fill into the XML file.
	 * To convert a string, you can use {@link #STRING_TO_TEXT} mapping.
	 * 
	 * @author martin
	 *
	 * @param <T>
	 */
	@FunctionalInterface
	public interface NonNullValueToTextMapper<T> {
		/**
		 * An identity (i.e. empty) mapping.
		 */
		public static final NonNullValueToTextMapper<String> STRING_TO_TEXT = (s) -> s;

		/**
		 * Converts the given (non-null) java object to the xml text.
		 * 
		 * @param value
		 * @return
		 * @throws Exception
		 */
		String valueToText(T value) throws Exception;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the full name, i.e. with the namespace prefix, if any, of the element
	 * or attribute.
	 * 
	 * @param elemName
	 * @return
	 */
	private String fullName(String elemName) {
		if (nsName != null) {
			return nsName + ":" + elemName;
		} else {
			return elemName;
		}
	}

	/**
	 * Clones the given elment. Returns the clone.
	 * 
	 * @param elem
	 * @return
	 */
	public Element clone(Element elem) {
		return (Element) elem.cloneNode(true);
	}

}
