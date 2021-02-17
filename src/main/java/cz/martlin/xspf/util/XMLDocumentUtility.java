package cz.martlin.xspf.util;

import java.util.stream.Stream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.util.XMLDocumentUtilityHelper.TextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.ValueToTextMapper;

public class XMLDocumentUtility {
	private final XMLDocumentUtilityHelper helper;

	public XMLDocumentUtility(String nsName, String nsURL) {
		super();
		this.helper = new XMLDocumentUtilityHelper(nsName, nsURL);
	}

	public void register(Element elem) throws XSPFException {
		helper.specifyNSattribute(elem);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// get/set element content text/value

	public <T> T getChildElementValueOrNull(Element owner, String elemName, TextToValueMapper<T> mapper)
			throws XSPFException {

		Element elem = helper.getChildOrNull(owner, elemName);
		if (elem == null) {
			return null;
		}
		return getElementValueOrNull(elem, mapper);
	}

	public <T> void setChildElementValue(Element owner, String elemName, T value, ValueToTextMapper<T> mapper)
			throws XSPFException {

		Element elem = helper.getOrCreateChild(owner, elemName);
		setElementValue(elem, value, mapper);
	}

	public <T> T getElementValueOrNull(Element elem, TextToValueMapper<T> mapper) throws XSPFException {

		String text = helper.getElementValueOrNull(elem);
		return helper.textToValue(text, mapper);
	}

	public <T> void setElementValue(Element elem, T value, ValueToTextMapper<T> mapper) throws XSPFException {

		String text = helper.valueToText(value, mapper);
		if (text != null) {
			helper.setElementValue(elem, text);
		}
	}

	public <T> T getElementAttrOrNull(Element owner, String attrName, TextToValueMapper<T> mapper)
			throws XSPFException {

		String text = helper.getAttrValueOrNull(owner, attrName);
		return helper.textToValue(text, mapper);
	}

	public <T> void setElementAttr(Element owner, String attrName, T value, ValueToTextMapper<T> mapper)
			throws XSPFException {

		String text = helper.valueToText(value, mapper);
		if (text != null) {
			helper.setAttrValue(owner, attrName, text);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// list/get/get or create child(ren)

	public Stream<Element> listChildrenElems(Node container) throws XSPFException {
		return helper.getChildren(container, null);
	}

	public Stream<Element> listChildrenElems(Node container, String elemName) throws XSPFException {
		return helper.getChildren(container, elemName);
	}

	public Stream<Element> listChildrenElemsClones(Node owner, String elemName) throws XSPFException {
		return helper.getChildren(owner, elemName)//
				.map(e -> helper.clone(e));
	}

	public Element getChildElemOrNull(Node owner, String elemName) throws XSPFException {
		return helper.getChildOrNull(owner, elemName);
	}

	public Element getOrCreateChildElem(Node owner, String elemName) throws XSPFException {
		return helper.getOrCreateChild(owner, elemName);
	}

	public Element getChildElemClone(Node owner, String elemName) throws XSPFException {
		Element elem = helper.getChildOrNull(owner, elemName);
		if (elem == null) {
			return null;
		}
		return helper.clone(elem);
	}

	public Element getElemClone(Element elem) {
		return helper.clone(elem);
	}
	/////////////////////////////////////////////////////////////////////////////////////
	// add/remove/replace by element name

	public void removeChildElement(Node owner, String elemName) throws XSPFException {
		Element elem = helper.getChildOrNull(owner, elemName);
		if (elem == null) {
			return;
		}
		removeChildElement(owner, elem);
	}

	public void removeChildElements(Node owner, String elemName) throws XSPFException {
		Stream<Element> elems = helper.getChildren(owner, elemName);
		removeChildElements(owner, elems);
	}

	public void replaceChildElement(Node owner, String elemName, Element replacement) throws XSPFException {
		Element elem = helper.getChildOrNull(owner, elemName);
		if (elem == null) {
			addChildElement(owner, replacement);
		} else {
			replaceChildElement(owner, elem, replacement);
		}
	}

	public void replaceChildElements(Node owner, String elemName, Stream<Element> replacements) throws XSPFException {
		Stream<Element> elems = helper.getChildren(owner, elemName);
		replaceChildElements(owner, elems, replacements);
	}

	public void replaceChildElementByClone(Node owner, String elemName, Element replacement) throws XSPFException {
		Element elem = helper.getChildOrNull(owner, elemName);
		Element clone = helper.clone(replacement);
		if (elem == null) {
			addChildElement(owner, clone);
		} else {
			replaceChildElement(owner, elem, clone);
		}
	}

	public void replaceChildElementsByClone(Node owner, String elemName, Stream<Element> replacements)
			throws XSPFException {
		Stream<Element> elems = helper.getChildren(owner, elemName);
		Stream<Element> clones = replacements.map((e) -> helper.clone(e));
		replaceChildElements(owner, elems, clones);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// add/remove/replace existing childr(en) element(s)

	public Element createNewElement(Node context, String name) {
		return helper.createNew(context, name);
	}

	public void addChildElement(Node owner, Element elem) {
		helper.addChild(owner, elem);
	}

	public void addChildElements(Node owner, Stream<Element> elems) {
		elems.forEach((e) -> {
			helper.addChild(owner, e);
		});
	}

	public void removeChildElement(Node owner, Element elem) {
		helper.removeChild(owner, elem);
	}

	public void removeChildElements(Node owner, Stream<Element> elems) {
		elems.forEach((e) -> {
			helper.removeChild(owner, e);
		});
	}

	public void replaceChildElement(Node owner, Element original, Element replacement) throws XSPFException {
		helper.removeChild(owner, original);
		helper.addChild(owner, replacement);
	}

	public void replaceChildElements(Node owner, Stream<Element> originals, Stream<Element> replacements)
			throws XSPFException {
		originals.forEach((e) -> {
			helper.removeChild(owner, e);
		});
		replacements.forEach((e) -> {
			helper.addChild(owner, e);
		});
	}

}
