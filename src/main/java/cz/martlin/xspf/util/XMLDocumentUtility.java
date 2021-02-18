package cz.martlin.xspf.util;

import java.util.stream.Stream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.util.XMLDocumentUtilityHelper.TextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.ValueToTextMapper;

/**
 * An xml document utility. Provides some usefull methods to simplify
 * manipulation with the XML file(s).
 * 
 * @author martin
 *
 */
public class XMLDocumentUtility {
	/**
	 * An instance of the utility helper performing the actual DOM nodes manipulation.
	 */
	private final XMLDocumentUtilityHelper helper;

	/**
	 * Creates instance for the given xml namespace.
	 * @param nsName
	 * @param nsURL
	 */
	public XMLDocumentUtility(String nsName, String nsURL) {
		super();
		this.helper = new XMLDocumentUtilityHelper(nsName, nsURL);
	}

	/**
	 * Registers given element to this utility (populates he namespace).
	 * @param elem
	 * @throws XSPFException
	 */
	public void register(Element elem) throws XSPFException {
		helper.specifyNSattribute(elem);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// get/set element content text/value

	/**
	 * Returns the value of the given element child of given name, converted by given mapper. If no such child, returns null.
	 * @param <T>
	 * @param owner
	 * @param elemName
	 * @param mapper
	 * @return
	 * @throws XSPFException
	 */
	public <T> T getChildElementValueOrNull(Element owner, String elemName, TextToValueMapper<T> mapper)
			throws XSPFException {

		Element elem = helper.getChildOrNull(owner, elemName);
		if (elem == null) {
			return null;
		}
		return getElementValueOrNull(elem, mapper);
	}

	/**
	 * Sets the value of the given element child of given name, converted by given mapper. If no such child, creates it.
	 * @param <T>
	 * @param owner
	 * @param elemName
	 * @param value
	 * @param mapper
	 * @throws XSPFException
	 */
	public <T> void setChildElementValue(Element owner, String elemName, T value, ValueToTextMapper<T> mapper)
			throws XSPFException {

		Element elem = helper.getOrCreateChild(owner, elemName);
		setElementValue(elem, value, mapper);
	}

	/**
	 * Returns the value of the given element, converted by given mapper. If no content, returns null.
	 * @param <T>
	 * @param elem
	 * @param mapper
	 * @return
	 * @throws XSPFException
	 */
	public <T> T getElementValueOrNull(Element elem, TextToValueMapper<T> mapper) throws XSPFException {

		String text = helper.getElementValueOrNull(elem);
		return helper.textToValue(text, mapper);
	}

	/**
	 * Sets the value of the given element, converted by given mapper. If no content, fails.
	 * @param <T>
	 * @param elem
	 * @param value
	 * @param mapper
	 * @throws XSPFException
	 */
	public <T> void setElementValue(Element elem, T value, ValueToTextMapper<T> mapper) throws XSPFException {

		String text = helper.valueToText(value, mapper);
		if (text != null) {
			helper.setElementValue(elem, text);
		}
	}

	/**
	 * Returns the value of the given element attribute of given name, converted by given mapper. If no such attribute, returns null.
	 * @param <T>
	 * @param owner
	 * @param attrName
	 * @param mapper
	 * @return
	 * @throws XSPFException
	 */
	public <T> T getElementAttrOrNull(Element owner, String attrName, TextToValueMapper<T> mapper)
			throws XSPFException {

		String text = helper.getAttrValueOrNull(owner, attrName);
		return helper.textToValue(text, mapper);
	}

	/**
	 * Sets the value of the given element attribute of given name, converted by given mapper. If no such attribute, creates.
	 * @param <T>
	 * @param owner
	 * @param attrName
	 * @param value
	 * @param mapper
	 * @throws XSPFException
	 */
	public <T> void setElementAttr(Element owner, String attrName, T value, ValueToTextMapper<T> mapper)
			throws XSPFException {

		String text = helper.valueToText(value, mapper);
		if (text != null) {
			helper.setAttrValue(owner, attrName, text);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// list/get/get or create child(ren)

	/**
	 * Lists all the children of the given container node (element or document).
	 * @param container
	 * @return
	 * @throws XSPFException
	 */
	public Stream<Element> listChildrenElems(Node container) throws XSPFException {
		return helper.getChildren(container, null);
	}

	/**
	 * Lists the children of the given name of the given container node (element or document).
	 * @param container
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Stream<Element> listChildrenElems(Node container, String elemName) throws XSPFException {
		return helper.getChildren(container, elemName);
	}

	/**
	 * Lists the copies of the children of the given name of the given container node (element or document).
	 * @param container
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Stream<Element> listChildrenElemsClones(Node container, String elemName) throws XSPFException {
		return helper.getChildren(container, elemName)//
				.map(e -> helper.clone(e));
	}

	/**
	 * Returns the child element of the given name of the given owner. Returns null if none, fails if more.
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Element getChildElemOrNull(Node owner, String elemName) throws XSPFException {
		return helper.getChildOrNull(owner, elemName);
	}

	/**
	 * Returns the child element of the given name of the given owner. Creates and returns if none.
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Element getOrCreateChildElem(Node owner, String elemName) throws XSPFException {
		return helper.getOrCreateChild(owner, elemName);
	}

	/**
	 * Returns the copy of the child element of the given name of the given owner. Fails if none or more.
	 * @param owner
	 * @param elemName
	 * @return
	 * @throws XSPFException
	 */
	public Element getChildElemClone(Node owner, String elemName) throws XSPFException {
		Element elem = helper.getChildOrNull(owner, elemName);
		if (elem == null) {
			return null;
		}
		return helper.clone(elem);
	}

	/**
	 * Returns the copy of the given element.
	 * @param elem
	 * @return
	 */
	public Element getElemClone(Element elem) {
		return helper.clone(elem);
	}
	/////////////////////////////////////////////////////////////////////////////////////
	// add/remove/replace by element name

	/**
	 * Removes one child element of the given name of the given owner. Fails if none or more.
	 * @param owner
	 * @param elemName
	 * @throws XSPFException
	 */
	public void removeChildElement(Node owner, String elemName) throws XSPFException {
		Element elem = helper.getChildOrNull(owner, elemName);
		if (elem == null) {
			return;
		}
		removeChildElement(owner, elem);
	}

	/**
	 * Removes all child elements of the given name of the given owner.
	 * @param owner
	 * @param elemName
	 * @throws XSPFException
	 */
	public void removeChildElements(Node owner, String elemName) throws XSPFException {
		Stream<Element> elems = helper.getChildren(owner, elemName);
		removeChildElements(owner, elems);
	}

	/**
	 * Replaces one child element of the given name of the given owner with the another one. If none just adds, fails if more.
	 * @param owner
	 * @param elemName
	 * @param replacement
	 * @throws XSPFException
	 */
	public void replaceChildElement(Node owner, String elemName, Element replacement) throws XSPFException {
		Element elem = helper.getChildOrNull(owner, elemName);
		if (elem == null) {
			addChildElement(owner, replacement);
		} else {
			replaceChildElement(owner, elem, replacement);
		}
	}

	/**
	 * Replaces all child elements of the given name of the given owner with the another ones.
	 * @param owner
	 * @param elemName
	 * @param replacements
	 * @throws XSPFException
	 */
	public void replaceChildElements(Node owner, String elemName, Stream<Element> replacements) throws XSPFException {
		Stream<Element> elems = helper.getChildren(owner, elemName);
		replaceChildElements(owner, elems, replacements);
	}

	/**
	 * Replaces one child element of the given name of the given owner with the clone of another one. If none just adds, fails if more.
	 * @param owner
	 * @param elemName
	 * @param replacement
	 * @throws XSPFException
	 */
	public void replaceChildElementByClone(Node owner, String elemName, Element replacement) throws XSPFException {
		Element elem = helper.getChildOrNull(owner, elemName);
		Element clone = helper.clone(replacement);
		if (elem == null) {
			addChildElement(owner, clone);
		} else {
			replaceChildElement(owner, elem, clone);
		}
	}

	/**
	 * Replaces all child elements of the given name of the given owner with the clone of another ones.
	 * @param owner
	 * @param elemName
	 * @param replacements
	 * @throws XSPFException
	 */
	public void replaceChildElementsByClone(Node owner, String elemName, Stream<Element> replacements)
			throws XSPFException {
		Stream<Element> elems = helper.getChildren(owner, elemName);
		Stream<Element> clones = replacements.map((e) -> helper.clone(e));
		replaceChildElements(owner, elems, clones);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// add/remove/replace existing childr(en) element(s)

	/**
	 * Just creates new element in the document given by the context (the whole document including).
	 * @param context
	 * @param name
	 * @return
	 * @throws XSPFException 
	 */
	public Element createNewElement(Node context, String name) throws XSPFException {
		return helper.createNew(context, name);
	}

	/**
	 * Just adds the given element to the given owner.
	 * @throws XSPFException 
	 */
	public void addChildElement(Node owner, Element elem) throws XSPFException {
		helper.addChild(owner, elem);
	}

	/**
	 * Just adds all the given elements to the given owner.
	 * @param owner
	 * @param elems
	 */
	public void addChildElements(Node owner, Stream<Element> elems) {
		elems.forEach((e) -> {
			try {
				helper.addChild(owner, e);
			} catch (XSPFException ex) {
				throw new RuntimeException(ex);
			}
		});
	}

	/**
	 * Just removes the given element from the given owner.
	 * @param owner
	 * @param elem
	 * @throws XSPFException 
	 */
	public void removeChildElement(Node owner, Element elem) throws XSPFException {
		helper.removeChild(owner, elem);
	}

	/**
	 * Just removes the given elements from the given owner.
	 * @param owner
	 * @param elems
	 */
	public void removeChildElements(Node owner, Stream<Element> elems) {
		elems.forEach((e) -> {
			try {
				helper.removeChild(owner, e);
			} catch (XSPFException ex) {
				throw new RuntimeException(ex);
			}
		});
	}

	/**
	 * Just replaces the given element with another one.
	 * @param owner
	 * @param original
	 * @param replacement
	 * @throws XSPFException
	 */
	public void replaceChildElement(Node owner, Element original, Element replacement) throws XSPFException {
		helper.removeChild(owner, original);
		helper.addChild(owner, replacement);
	}

	/**
	 * Just replaces the given elements by another ones.
	 * @param owner
	 * @param originals
	 * @param replacements
	 * @throws XSPFException
	 */
	public void replaceChildElements(Node owner, Stream<Element> originals, Stream<Element> replacements)
			throws XSPFException {
		originals.forEach((e) -> {
			try {
				helper.removeChild(owner, e);
			} catch (XSPFException ex) {
				throw new RuntimeException(ex);
			}
		});
		replacements.forEach((e) -> {
			try {
				helper.addChild(owner, e);
			} catch (XSPFException ex) {
				throw new RuntimeException(ex);
			}
		});
	}

}
