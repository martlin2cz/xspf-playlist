package cz.martlin.xspf.playlist;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.xspf.util.XMLFileLoaderStorer;
import cz.martlin.xspf.util.XSPFException;

public class XSPFFile {

	protected static final String XSPF_STANDART_VERSION = "1";
	
	private final Document document;

	public XSPFFile(Document document) {
		super();
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}
	
	public XSPFPlaylist getPlaylist() {
		Element element = getRootElement();
		return new XSPFPlaylist(element);
	}

/////////////////////////////////////////////////////////////////////////////////////


	public static XSPFFile load(File file) throws XSPFException {
		Document document = XMLFileLoaderStorer.loadDocument(file);
		verify(document);
		return new XSPFFile(document);
	}

	public static XSPFFile create() throws XSPFException {
		Document document = XMLFileLoaderStorer.createEmptyDocument();
		prepare(document);
		return new XSPFFile(document);
	}

	public void save(File file) throws XSPFException {
		XMLFileLoaderStorer.saveDocument(document, file);
	}

/////////////////////////////////////////////////////////////////////////////////////

	
	private static void prepare(Document document) throws XSPFException {
		XSPFBase.UTIL.init(document);

		Element root = XSPFBase.UTIL.getOrCreateRootElem(document, "playlist");
		XSPFBase.UTIL.setElementAttr(root, "version", XSPF_STANDART_VERSION);
	}

	private static void verify(Document document) throws XSPFException {
		Element root = XSPFBase.UTIL.getRootElem(document, "playlist");
		String version = XSPFBase.UTIL.getElementAttr(root, "version");
		if (!version.equals(XSPF_STANDART_VERSION)) {
			throw new XSPFException("The supported version of XSPF is " + XSPF_STANDART_VERSION);
		}
	}
	

	private Element getRootElement() {
		return document.getDocumentElement();
	}


/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "XSPFFile [playlist=" + getPlaylist() + "]";
	}
	
}
