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
	

	public void setNSattribute(Document document) throws XSPFException {
		String attrName;
		if (nsName != null) {
			attrName = "xmlns:" + nsName;
		} else {
			attrName = "xmlns";
		}

		String atrrValue = nsURL;

		Element root = getRootElem(document);
		root.setAttribute(attrName, atrrValue);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// create/remove/get child

	
	//TODO elemName, verify
	//TODO getOrCreateRootElem ?
	public Element getRootElem(Document document) throws XSPFException {
		Element root = document.getDocumentElement();
		if (root == null) {
			throw new XSPFException("No root element");
		}
		return root;
	}

	//TODO separate the Element and Document's child
	public Element createChild(Node owner, String elemName) {
		Document document;
		if (owner.getNodeType() == Node.DOCUMENT_NODE) {
			document = (Document) owner;
		} else {
			document = owner.getOwnerDocument();
		}

		Element child = document.createElement(/* NS(nsURL, */ fullName(elemName));
		owner.appendChild(child);
		return child;
	}

	public void removeChildren(Element owner, String elemName) throws XSPFException {
		List<Element> children = listChildren(owner, elemName);
		for (Element child : children) {
			owner.removeChild(child);
		}
	}

	public void removeChild(Element owner, String elemName) throws XSPFException {
		Element child = getChild(owner, elemName, true);
		owner.removeChild(child);
	}

	public Element getOrCreateChild(Node owner, String elemName) throws XSPFException {
		Element child = getChild(owner, elemName, false);
		if (child == null) {
			child = createChild(owner, elemName);
		}
		return child;
	}

	public Element replaceChild(Element owner, String elemName) throws XSPFException {
		if (hasChild(owner, elemName)) {
			removeChild(owner, elemName);
		}

		return createChild(owner, elemName);
	}

	public boolean hasChild(Node owner, String elemName) {
		List<Element> children = listChildren(owner, elemName);
		return !children.isEmpty();
	}

	public Element getChild(Node owner, String elemName, boolean failOnMissing) throws XSPFException {
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

	/////////////////////////////////////////////////////////////////////////////////////
	// list children

	public List<Element> listChildren(Node container, String elemName) {
		NodeList children = container.getChildNodes();
		return listElems(children, elemName);
	}

	public List<Element> listElems(NodeList children, String elemName) {
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
