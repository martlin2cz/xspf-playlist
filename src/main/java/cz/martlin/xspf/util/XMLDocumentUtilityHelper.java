package cz.martlin.xspf.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

//  FIXME: idk ...	
//	public void setNSattribute(Document document) throws XSPFException {
	
//		String attrName;
//		if (nsName != null) {
//			attrName = "xmlns:" + nsName;
//		} else {
//			attrName = "xmlns";
//		}
//
//		String atrrValue = nsURL;
//
//		Element root = getRootElem(document);
//		root.setAttribute(attrName, atrrValue);
	//  root.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, attrName, atrrValue); 
//	}

	/////////////////////////////////////////////////////////////////////////////////////
	// create/remove/get child (public)

	public Element createNew(Node context, String elemName) {
		Document document = documentOfNode(context);
		return createNewElement(document, elemName);
	}

	public Element createChild(Node owner, String elemName) {
		return createChildElement(owner, elemName);
	}

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
	
	public List<Element> getChildren(Node owner, String elemName) throws XSPFException {
		return getChildrenElements(owner, elemName);
	}

	public boolean hasChildren(Node owner, String elemName) throws XSPFException {
		return !getChildrenElements(owner, elemName).isEmpty();
	}

	@Deprecated
	public Element getRoot(Document document, String elemName) throws XSPFException {
		return getChildElement(document, elemName, true);
	}

//	public Element getOrCreateRoot(Document document, String elemName) throws XSPFException {
//		Element child = getChildElement(document, elemName, false);
//		
//		if (child == null) {
//			if (hasRoot(document)) {
//				throw new XSPFException("Document has different root than " + elemName + "");
//			} else {
//				child = createChildElement(document, elemName);
//			}
//		}
//		
//		return child;
//	}
	
//	public boolean hasRoot(Document document) throws XSPFException {
//		return !getChildrenElements(document, null).isEmpty();
//	}

	public void addChild(Node owner, Element child) {
		addChildElement(owner, child);
	}

	public void removeChild(Node owner, Element child) {
		removeChildElement(owner, child);
	}

//	//TODO elemName, verify
//	//TODO getOrCreateRootElem ?
//	public Element getRootElem(Document document) throws XSPFException {
//		Element root = document.getDocumentElement();
//		if (root == null) {
//			throw new XSPFException("No root element");
//		}
//		return root;
//	}
//
//	//TODO separate the Element and Document's child
//	public Element createChild(Node owner, String elemName) {
//		Document document;
//		if (owner.getNodeType() == Node.DOCUMENT_NODE) {
//			document = (Document) owner;
//		} else {
//			document = owner.getOwnerDocument();
//		}
//
//		Element child = document.createElement(/* NS(nsURL, */ fullName(elemName));
//		owner.appendChild(child);
//		return child;
//	}

//	public void removeChildren(Element owner, String elemName) throws XSPFException {
//		List<Element> children = listChildren(owner, elemName);
//		for (Element child : children) {
//			owner.removeChild(child);
//		}
//	}
//
//	public void removeChild(Element owner, String elemName) throws XSPFException {
//		Element child = getChild(owner, elemName, true);
//		owner.removeChild(child);
//	}

//	public Element getOrCreateChild(Node owner, String elemName) throws XSPFException {
//		Element child = getChild(owner, elemName, false);
//		if (child == null) {
//			child = createChild(owner, elemName);
//		}
//		return child;
//	}

//	public Element replaceChild(Element owner, String elemName) throws XSPFException {
//		if (hasChild(owner, elemName)) {
//			removeChild(owner, elemName);
//		}
//
//		return createChild(owner, elemName);
//	}

//	public boolean hasChild(Node owner, String elemName) {
//		List<Element> children = listChildren(owner, elemName);
//		return !children.isEmpty();
//	}

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
		List<Element> children = listChildren(owner, elemName);
		if (children.size() < 1) {
			if (failOnMissing) {
				throw new XSPFException("No such element " + elemName);
			} else {
				return null;
			}
		}
		if (children.size() > 1) {
			throw new XSPFException("More than one " + elemName + " elements");
		}

		Node child = children.get(0);
		Element childElem = (Element) child;
		return childElem;
	}
	
	private List<Element> getChildrenElements(Node owner, String elemName) throws XSPFException {
		return listChildren(owner, elemName);
	}
		

	/////////////////////////////////////////////////////////////////////////////////////
	// list children

	private List<Element> listChildren(Node container, String elemName) {
		NodeList children = container.getChildNodes();
		return listElems(children, elemName);
	}

	private List<Element> listElems(NodeList children, String elemName) {
		//TODO if elemName is null, do not filter agains the elemName.
		return IntStream.range(0, children.getLength()) //
				.mapToObj(i -> children.item(i)) //
				.filter(n -> n.getNodeType() == Node.ELEMENT_NODE) //
				.map(n -> (Element) n) //
				.filter(e -> e.getTagName().equals(fullName(elemName))) //
				.collect(Collectors.toList());
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

	public void setAttrValue(Element elem, String attrName, String value) {
		elem.setAttribute(/* NS(nsURL, */ fullName(attrName), value);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// text <-> value

	public <T> T textToValue(String text, TextToValueMapper<T> mapper) throws XSPFException {
		try {
			return mapper.textToValue(text.trim());
		} catch (Exception e) {
			throw new XSPFException("Cannot convert " + text + " value", e);
		}
	}

	public <T> String valueToText(T value, ValueToTextMapper<T> mapper) throws XSPFException {
		try {
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

}
