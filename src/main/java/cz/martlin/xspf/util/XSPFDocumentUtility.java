package cz.martlin.xspf.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XSPFDocumentUtility {
	private final String nsName;
	private final String nsURL;

	public XSPFDocumentUtility(String nsName, String nsURL) {
		super();
		this.nsName = nsName;
		this.nsURL = nsURL;
	}

	public void init(Document document) throws XSPFException {
		setNSattribute(document);
	}

	private void setNSattribute(Document document) throws XSPFException {
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

	private String fullName(String elemName) {
		if (nsName != null) {
			return nsName + ":" + elemName;
		} else {
			return elemName;
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// get/set element content text/value

	public String getElementText(Element owner, String elemName) throws XSPFException {
		Element child = getChild(owner, elemName, true);
		return getElementValue(child);
	}

	public void setElementText(Element owner, String elemName, String value) throws XSPFException {
		Element child = getOrCreateChild(owner, elemName);
		setElementValue(child, value);
	}

	public <T> T getElementValue(Element owner, String elemName, TextToValueMapper<T> mapper) throws XSPFException {

		String text = getElementText(owner, elemName);
		return textToValue(text, mapper);
	}

	public <T> void setElementValue(Element elem, T value, ValueToTextMapper<T> mapper) throws XSPFException {
		String text = valueToText(value, mapper);
		setElementValue(elem, text);
	}

	public <T> T getElementValue(Element elem, TextToValueMapper<T> mapper) throws XSPFException {

		String text = getElementValue(elem);
		return textToValue(text, mapper);
	}

	public <T> void setElementValue(Element owner, String elemName, T value, ValueToTextMapper<T> mapper)
			throws XSPFException {

		String text = valueToText(value, mapper);
		setElementText(owner, elemName, text);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// get/set element attr text/value

	public String getElementAttr(Element owner, String attrName) throws XSPFException {
		return getAttrValue(owner, attrName);
	}

	public void setElementAttr(Element owner, String attrName, String value) throws XSPFException {
		setAttrValue(owner, attrName, value);
	}

	public <T> T getElementAttr(Element owner, String attrName, TextToValueMapper<T> mapper) throws XSPFException {

		String text = getAttrValue(owner, attrName);
		return textToValue(text, mapper);
	}

	public <T> void setElementAttr(Element owner, String attrName, T value, ValueToTextMapper<T> mapper)
			throws XSPFException {

		String text = valueToText(value, mapper);
		setAttrValue(owner, attrName, text);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// create/list children

	public List<Element> listChildrenElems(Element container, String elemName) {
		return listChildren(container, elemName);
	}

	public Element getOrCreateChildElem(Element owner, String elemName) throws XSPFException {
		return getOrCreateChild(owner, elemName);
	}

	public Element getOrCreateRootElem(Document doc, String elemName) throws XSPFException {
		return getOrCreateChild(doc, elemName);
	}

	public Element getChildElem(Element owner, String elemName) throws XSPFException {
		return getChild(owner, elemName, true);
	}

	public Element getRootElem(Document doc, String elemName) throws XSPFException {
		return getChild(doc, elemName, true);
	}

	private Element getRootElem(Document document) throws XSPFException {
		Element root = document.getDocumentElement();
		if (root == null) {
			throw new XSPFException("No root element");
		}
		return root;
	}

	public Element replaceChildElem(Element owner, String elemName) throws XSPFException {
		return replaceChild(owner, elemName);
	}
	
	public Element replaceChildElem(Element owner, String elemName, Element replacement) throws XSPFException {
		return replaceChild(owner, elemName, replacement);
	}

	public void replaceAllChildren(Element owner, String elemName, List<Element> elements) throws XSPFException {
		removeChildren(owner, elemName);
		for (Element elem: elements) {
			owner.appendChild(elem);
		}
	}


	/////////////////////////////////////////////////////////////////////////////////////
	// create/remove/get child

	private Element createChild(Node owner, String elemName) {
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
	
	private void removeChildren(Element owner, String elemName) throws XSPFException {
		List<Element> children = listChildren(owner, elemName);
		for (Element child: children)  {
			owner.removeChild(child);
		}
	}
	
	private void removeChild(Element owner, String elemName) throws XSPFException {
		Element child = getChild(owner, elemName, true);
		owner.removeChild(child);
	}

	private Element getOrCreateChild(Node owner, String elemName) throws XSPFException {
		Element child = getChild(owner, elemName, false);
		if (child == null) {
			child = createChild(owner, elemName);
		}
		return child;
	}

	private Element replaceChild(Element owner, String elemName) throws XSPFException {
		if (hasChild(owner, elemName)) {
			removeChild(owner, elemName);
		}

		return createChild(owner, elemName);
	}
	
	private Element replaceChild(Element owner, String elemName, Element replacement) throws XSPFException {
		if (hasChild(owner, elemName)) {
			removeChild(owner, elemName);
		}

		owner.appendChild(replacement);
		return replacement;
	}

	private boolean hasChild(Node owner, String elemName) {
		List<Element> children = listChildren(owner, elemName);
		return !children.isEmpty();
	}

	private Element getChild(Node owner, String elemName, boolean failOnMissing) throws XSPFException {
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

	private List<Element> listChildren(Node container, String elemName) {
		NodeList children = container.getChildNodes();
		return listElems(children, elemName);
	}

	private List<Element> listElems(NodeList children, String elemName) {
		return IntStream.range(0, children.getLength()) //
				.mapToObj(i -> children.item(i)) //
				.filter(n -> n.getNodeType() == Node.ELEMENT_NODE) //
				.map(n -> (Element) n) //
				.filter(e -> e.getTagName().equals(fullName(elemName))) //
				.collect(Collectors.toList());
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// get/set element content/attr

	private String getElementValue(Element elem) throws XSPFException {
		String content = elem.getTextContent();
		if (content == null) {
			throw new XSPFException("The element " + elem.getTagName() + " has no content");
		}
		return content;
	}

	private void setElementValue(Element elem, String value) {
		elem.setTextContent(value);
	}

	private String getAttrValue(Element elem, String attrName) throws XSPFException {
		String content = elem.getAttribute(/* NS(nsURL, */ fullName(attrName));
		if (content == null) {
			throw new XSPFException("The attribute " + attrName + " does not exist");
		}
		return content;
	}

	private void setAttrValue(Element elem, String attrName, String value) {
		elem.setAttribute(/* NS(nsURL, */ fullName(attrName), value);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// text <-> value

	private <T> T textToValue(String text, TextToValueMapper<T> mapper) throws XSPFException {
		try {
			return mapper.textToValue(text.trim());
		} catch (Exception e) {
			throw new XSPFException("Cannot convert " + text + " value", e);
		}
	}

	private <T> String valueToText(T value, ValueToTextMapper<T> mapper) throws XSPFException {
		try {
			return mapper.valueToText(value);
		} catch (Exception e) {
			throw new XSPFException("Cannot convert " + value + " value", e);
		}
	}

	@FunctionalInterface
	public interface TextToValueMapper<T> {
		T textToValue(String text) throws Exception;
	}

	@FunctionalInterface
	public interface ValueToTextMapper<T> {
		String valueToText(T value) throws Exception;
	}


}
