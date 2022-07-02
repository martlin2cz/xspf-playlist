package cz.martlin.xspf.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * Just an separated helper class for the XML document IO, thus loading
 * (parsing) and saving (serializing).
 * 
 * TODO: add load from InputStream/Reader/String?, write to
 * OutputStream/Writer/String?
 * 
 * @author martin
 *
 */
public class XMLFileLoaderStorer {

	/**
	 * Loads the XML DOM document from the given file.
	 * 
	 * @param file
	 * @return
	 * @throws XSPFException
	 */
	public static Document loadDocument(File file) throws XSPFException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			return document;
		} catch (Exception e) {
			throw new XSPFException("Cannot load file", e);
		}
	}

	/**
	 * Creates the empty XML DOM document instance.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	public static Document createEmptyDocument() throws XSPFException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			return document;
		} catch (Exception e) {
			throw new XSPFException("Cannot create document", e);
		}
	}

	/**
	 * Saves the XML DOM document to the given file.
	 * 
	 * @param document
	 * @param file
	 * @throws XSPFException
	 */
	public static void saveDocument(Document document, File file) throws XSPFException {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new XSPFException("Cannot save file", e);
		}
	}
}
