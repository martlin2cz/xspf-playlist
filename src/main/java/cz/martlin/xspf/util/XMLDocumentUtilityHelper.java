package cz.martlin.xspf.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLDocumentUtilityHelper {
	private final String nsName;
	private final String nsURL;

	public XMLDocumentUtilityHelper(String nsName, String nsURL) {
		super();
		this.nsName = nsName;
		this.nsURL = nsURL;
	}

	public void specifyNSattribute(Element elem)throws XSPFException {
		String attrName = namespaceAttrName();
		String atrrValue = nsURL;

		setAttrValue(elem, attrName, atrrValue); 
	}

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

	public Element createNew(Node context, String elemName) {
		Document document = documentOfNode(context);
		return createNewElement(document, elemName);
	}

	public Element createChild(Node owner, String elemName) {
		return createChildElement(owner, elemName);
	}

	public Element getChildOrNull(Node owner, String elemName) throws XSPFException {
		return getChildElement(owner, elemName, false);
	}
	
	/**
	 * Replaced by {@link #getChildOrNull(Node, String)}.
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	@Deprecated
	public Element getChild(Node owner, String elemName) throws XSPFException {
		return getChildElement(owner, elemName, true);
	}

	public Element getOrCreateChild(Node owner, String elemName) throws XSPFException {
		Element child = getChildElement(owner, elemName, false);
		if (child == null) {
			child = createChildElement(owner, elemName);
		}
		return child;
	}
	
	public Stream<Element> getChildren(Node owner, String elemName)throws XSPFException {
		return getChildrenElements(owner, elemName);
	}

	public boolean hasChildren(Node owner, String elemName)throws XSPFException {
		return getChildrenElements(owner, elemName) //
				.findAny().isPresent();
	}

	@Deprecated
	public Element getRoot(Document document, String elemName) throws XSPFException {
		return getChildElement(document, elemName, true);
	}

	public void addChild(Node owner, Element child) {
		addChildElement(owner, child);
	}

	public void removeChild(Node owner, Element child) {
		removeChildElement(owner, child);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// create/remove/get child (internal)
	
	private Document documentOfNode(Node node) {
		if (node.getNodeType() == Node.DOCUMENT_NODE) {
			return (Document) node;
		}
		
		// TODO if doc already, use the doc
		// TODO if attr, test
		return node.getOwnerDocument();
	}

	private Element createNewElement(Document document, String elemName) {
		return document.createElement(fullName(elemName));
	}

	private Element createChildElement(Node owner, String elemName) {
		Document document = documentOfNode(owner);
		Element elem = createNewElement(document, elemName);
		addChildElement(owner, elem);
		return elem;
	}

	private void addChildElement(Node owner, Element elem) {
		owner.appendChild(elem);
	}

	private void removeChildElement(Node owner, Element elem) {
		owner.removeChild(elem);
	}

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
	
	private Stream<Element> getChildrenElements(Node owner, String elemName)throws XSPFException {
		return listChildren(owner, elemName);
	}
		

	/////////////////////////////////////////////////////////////////////////////////////
	// list children

	private Stream<Element> listChildren(Node container, String elemName) {
		//TODO if elemName is null, do not filter agains the elemName.
		NodeList children = container.getChildNodes();

		return IntStream.range(0, children.getLength()) //
				.mapToObj(i -> children.item(i)) //
				.filter(n -> n.getNodeType() == Node.ELEMENT_NODE) //
				.map(n -> (Element) n) //
				.filter(e -> isElemOfName(e, elemName)) //
				.collect(Collectors.toList()).stream(); // sorryjako
	}

	private boolean isElemOfName(Element e, String elemName) {
		if (elemName == null) {
			return true;
		} else {
			return e.getTagName().equals(fullName(elemName));
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// get/set element content/attr

	public String getElementValue(Element elem) throws XSPFException {
		String content = elem.getTextContent();
		if (content == null) {
			throw new XSPFException("The element " + elem.getTagName() + " has no content");
		}
		return content;
	}
	
	public String getElementValueOrNull(Element elem)throws XSPFException {
		return elem.getTextContent();
	}

	public void setElementValue(Element elem, String value) {
		elem.setTextContent(value);
	}

	public String getAttrValue(Element elem, String attrName) throws XSPFException {
		String content = elem.getAttribute(/* NS(nsURL, */ fullName(attrName));
		if (content == null) {
			throw new XSPFException("The attribute " + attrName + " does not exist");
		}
		return content;
	}
	
	public String getAttrValueOrNull(Element elem, String attrName)throws XSPFException {
		return elem.getAttribute(/* NS(nsURL, */ fullName(attrName));
	}

	public void setAttrValue(Element elem, String attrName, String value) {
		elem.setAttribute(/* NS(nsURL, */ fullName(attrName), value);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// text <-> value

	public <T> T textToValue(String text, TextToValueMapper<T> mapper) throws XSPFException {
		try {
			if (text == null) {
				return null;
			}
			
			return mapper.textToValue(text.trim());
		} catch (Exception e) {
			throw new XSPFException("Cannot convert " + text + " value", e);
		}
	}

	public <T> String valueToText(T value, ValueToTextMapper<T> mapper) throws XSPFException {
		try {
			if (value == null) {
				return null;
			}
			
			return mapper.valueToText(value);
		} catch (Exception e) {
			throw new XSPFException("Cannot convert " + value + " value", e);
		}
	}

	@FunctionalInterface
	public interface TextToValueMapper<T> {
		public static final TextToValueMapper<String> TEXT_TO_STRING = (s) -> s;

		T textToValue(String text) throws Exception;
	}

	@FunctionalInterface
	public interface ValueToTextMapper<T> {
		public static final ValueToTextMapper<String> STRING_TO_TEXT = (s) -> s;

		String valueToText(T value) throws Exception;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private String fullName(String elemName) {
		if (nsName != null) {
			return nsName + ":" + elemName;
		} else {
			return elemName;
		}
	}

	public Element clone(Element elem) {
		return (Element) elem.cloneNode(true);
	}

}
