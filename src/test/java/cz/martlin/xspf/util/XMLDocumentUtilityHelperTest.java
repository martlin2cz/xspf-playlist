package cz.martlin.xspf.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An test for the {@link XMLDocumentUtilityHelper}.
 * 
 * @author martin
 *
 */
class XMLDocumentUtilityHelperTest {

	private final XMLDocumentUtilityHelper helper = new XMLDocumentUtilityHelper("test", "what:/ev.er");
	private Document document;

	@BeforeEach
	void setUp() throws Exception {
		document = prepare();
	}

	@AfterEach
	void tearDown() throws Exception {
		document = null;
	}

	@Test
	void testCreateNew() throws XSPFException {
		Element lorem = helper.createNew(document, "lorem");

		assertEquals("test:lorem", lorem.getTagName());
	}

	@Test
	void testCreateChild() throws XSPFException {
		Element foo = document.getDocumentElement();
		Element lorem = helper.createChild(foo, "lorem");

		assertEquals("test:lorem", lorem.getTagName());
		assertEquals(foo, lorem.getParentNode());
	}

	@Test
	void testGetChildOrNull() throws XSPFException {
		Element foo = helper.getChildOrNull(document, "foo");
		Element bar = helper.getChildOrNull(foo, "bar");

		assertEquals("test:bar", bar.getTagName());
		assertEquals(foo, bar.getParentNode());

		assertThrows(XSPFException.class, () -> helper.getChildOrNull(foo, "aux"));
		assertNull(helper.getChildOrNull(foo, "lorem"));
	}

	@Test
	void testGetOrCreateChild() throws XSPFException {
		Element foo = helper.getOrCreateChild(document, "foo");
		Element bar = helper.getOrCreateChild(foo, "bar");

		assertEquals("test:bar", bar.getTagName());
		assertEquals(foo, bar.getParentNode());

		assertThrows(XSPFException.class, () -> helper.getChildOrNull(foo, "aux"));

		Element lorem = helper.getOrCreateChild(foo, "lorem");

		assertEquals("test:lorem", lorem.getTagName());
		assertEquals(foo, lorem.getParentNode());
	}

	@Test
	void testGetChildren() throws XSPFException {
		Element foo = document.getDocumentElement();
		List<Element> auxs = helper.getChildren(foo, "aux").collect(Collectors.toList());

		Element aux1 = (Element) foo.getChildNodes().item(5);
		Element aux2 = (Element) foo.getChildNodes().item(7);

		assertIterableEquals(List.of(aux1, aux2), auxs);

		List<Element> lorems = helper.getChildren(foo, "lorem").collect(Collectors.toList());
		assertIterableEquals(List.of(), lorems);
	}

	@Test
	void testGetRoot() throws XSPFException {
		Element foo = helper.getChildOrNull(document, "foo");
		assertEquals("test:foo", foo.getTagName());

		assertNull(helper.getChildOrNull(document, "lorem"));
	}

	@Test
	void testAddChild() {
		// TODO ...
	}

	@Test
	void testRemoveChild() {
		// TODO ...
	}

	@Test
	void testGetElementValue() throws XSPFException {
		Element foo = document.getDocumentElement();
		Element aux1 = (Element) foo.getChildNodes().item(5);

		String auxValue = helper.getElementValue(aux1);
		assertEquals("AUX 1", auxValue);
	}

	@Test
	void testSetElementValue() throws XSPFException {
		Element foo = document.getDocumentElement();
		Element aux1 = (Element) foo.getChildNodes().item(5);

		helper.setElementValue(aux1, "reAUXed");
		assertEquals("reAUXed", aux1.getFirstChild().getTextContent());
	}

	@Test
	void testGetAttrValue() throws XSPFException {
		Element foo = document.getDocumentElement();
		Element bar = (Element) foo.getChildNodes().item(3);

		String bazValue = helper.getAttrValue(bar, "baz");
		assertEquals("42", bazValue);
	}

	@Test
	void testSetAttrValue() {
		Element foo = document.getDocumentElement();
		Element bar = (Element) foo.getChildNodes().item(3);

		helper.setAttrValue(bar, "baz", "99");
		assertEquals("99", bar.getAttribute("test:baz"));
	}

	/**
	 * Prepares the testing file.
	 * 
	 * @return
	 */
	private Document prepare() {
		try {
			File file = TestingFiles.fileToRead("util", "testing.xml");
			return XMLFileLoaderStorer.loadDocument(file);
		} catch (Exception e) {
			assumeFalse(true, e.toString());
			return null;
		}
	}

}
