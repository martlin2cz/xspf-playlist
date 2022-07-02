package cz.martlin.xspf.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The {@link XMLDocumentUtilityHelper} test, but testing specifically the
 * failurar cases.
 * 
 * @author martin
 *
 */
public class XMLDocumentUtilityHelperFailurarsTest {

	private final XMLDocumentUtilityHelper helper = new XMLDocumentUtilityHelper("test", "what:/ev.er");

	/**
	 * Tests various illegal additions.
	 */
	@Test
	public void testAdd() {
		Document first = prepare();
		Document second = prepare();

		Element firstFoo = first.getDocumentElement();
		Element secondFoo = second.getDocumentElement();

		assertFails(() -> helper.addChild(firstFoo, firstFoo));
		assertFails(() -> helper.addChild(first, firstFoo));
		assertFails(() -> helper.addChild(second, firstFoo));
		assertFails(() -> helper.addChild(secondFoo, firstFoo));
	}

	/**
	 * Tests various illegal removals.
	 */
	@Test
	public void testRemove() {
		Document first = prepare();
		Document second = prepare();

		Element firstFoo = first.getDocumentElement();
		Element secondFoo = second.getDocumentElement();

		assertFails(() -> helper.removeChild(firstFoo, firstFoo));
//		assertFails(() -> helper.removeChild(first, firstFoo));// okay in fact
		assertFails(() -> helper.removeChild(second, firstFoo));
		assertFails(() -> helper.removeChild(secondFoo, firstFoo));
	}

	/**
	 * Checks the given action and checks it fails. Prints the caught eception to
	 * stderr.
	 * 
	 * @param action
	 */
	private void assertFails(Executable action) {
		XSPFException ex = assertThrows(XSPFException.class, action);
		System.err.println(ex);
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
