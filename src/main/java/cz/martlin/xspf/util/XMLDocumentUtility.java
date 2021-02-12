package cz.martlin.xspf.util;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.xspf.util.XMLDocumentUtilityHelper.TextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.ValueToTextMapper;

public class XMLDocumentUtility {
	private final XMLDocumentUtilityHelper helper;

	public XMLDocumentUtility(String nsName, String nsURL) {
		super();
		this.helper = new XMLDocumentUtilityHelper(nsName, nsURL);
	}

	public void init(Document document) throws XSPFException {
		helper.setNSattribute(document);
	}



	/////////////////////////////////////////////////////////////////////////////////////
	// get/set element content text/value

	/**
	 * @deprecated use {@link #getElementValue(Element, TextToValueMapper)} and
	 *             {@link TextToValueMapper#TEXT_TO_STRING}
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public String getChildElementText(Element owner, String elemName) throws XSPFException {
		Element child = helper.getChild(owner, elemName, true);
		return helper.getElementValue(child);
	}

	/**
	 * @deprecated use
	 *             {@link #setChildElementValue(Element, String, Object, ValueToTextMapper)
	 *             and {@link ValueToTextMapper#STRING_TO_TEXT}.
	 * @param owner
	 * @param elemName
	 * @param value
	 * @throws XSPFException
	 */
	public void setChildElementText(Element owner, String elemName, String value) throws XSPFException {
		Element child = helper.getOrCreateChild(owner, elemName);
		helper.setElementValue(child, value);
	}

	public <T> T getChildElementValue(Element owner, String elemName, TextToValueMapper<T> mapper)
			throws XSPFException {
		Element elem = helper.getChild(owner, elemName, true);
		String text = helper.getElementValue(elem);
		return helper.textToValue(text, mapper);
	}

	public <T> void setElementValue(Element elem, T value, ValueToTextMapper<T> mapper) throws XSPFException {
		String text = helper.valueToText(value, mapper);
		helper.setElementValue(elem, text);
	}

	public <T> T getElementValue(Element elem, TextToValueMapper<T> mapper) throws XSPFException {
		String text = helper.getElementValue(elem);
		return helper.textToValue(text, mapper);
	}

	public <T> void setChildElementValue(Element owner, String elemName, T value, ValueToTextMapper<T> mapper)
			throws XSPFException {

		Element elem = helper.getOrCreateChild(owner, elemName);
		String text = helper.valueToText(value, mapper);
		helper.setElementValue(elem, text);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// get/set element attr text/value

	/**
	 * @deprecated use {@link #getElementAttr(Element, String, TextToValueMapper)} and
	 *             {@link TextToValueMapper#TEXT_TO_STRING}
	 * @param owner
	 * @param attrName
	 * @return
	 * @throws XSPFException
	 */
	public String getElementAttr(Element owner, String attrName) throws XSPFException {
		return helper.getAttrValue(owner, attrName);
	}

	/**
	 * @deprecated use
	 *             {@link #getElementAttr(Element, String, TextToValueMapper)}
	 *             and {@link ValueToTextMapper#STRING_TO_TEXT}.
	 * @param owner
	 * @param attrName
	 * @param value
	 * @throws XSPFException
	 */
	public void setElementAttr(Element owner, String attrName, String value) throws XSPFException {
		helper.setAttrValue(owner, attrName, value);
	}

	public <T> T getElementAttr(Element owner, String attrName, TextToValueMapper<T> mapper) throws XSPFException {

		String text = helper.getAttrValue(owner, attrName);
		return helper.textToValue(text, mapper);
	}

	public <T> void setElementAttr(Element owner, String attrName, T value, ValueToTextMapper<T> mapper)
			throws XSPFException {

		String text = helper.valueToText(value, mapper);
		helper.setAttrValue(owner, attrName, text);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// list/get/get or create child(ren)

	public List<Element> listChildrenElems(Element container, String elemName) {
		return helper.listChildren(container, elemName);
	}

	public Element getRootElem(Document doc, String elemName) throws XSPFException {
		return helper.getChild(doc, elemName, true);
	}

	public Element getOrCreateRootElem(Document doc, String elemName) throws XSPFException {
		return helper.getOrCreateChild(doc, elemName);
	}

	public Element getChildElem(Element owner, String elemName) throws XSPFException {
		return helper.getChild(owner, elemName, true);
	}

	public Element getOrCreateChildElem(Element owner, String elemName) throws XSPFException {
		return helper.getOrCreateChild(owner, elemName);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// add/remove/replace child(ren)

	public Element replaceChildElem(Element owner, String elemName) throws XSPFException {
		return helper.replaceChild(owner, elemName);
	}

	public void replaceChildElem(Element owner, String elemName, Element replacement) throws XSPFException {
		helper.removeChild(owner, elemName);
		owner.appendChild(replacement); // TODO use internal method
	}

	public void replaceAllChildren(Element owner, String elemName, List<Element> elements) throws XSPFException {
		helper.removeChildren(owner, elemName);
		for (Element elem : elements) {
			owner.appendChild(elem); // TODO use internal method
		}
	}

}
