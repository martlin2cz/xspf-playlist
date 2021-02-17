package cz.martlin.xspf.playlist.elements;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.playlist.base.XSPFNode;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper;
import cz.martlin.xspf.util.XMLFileLoaderStorer;
import cz.martlin.xspf.util.XSPFException;

public class XSPFFile extends XSPFNode {

	protected static final String XSPF_STANDART_VERSION = "1";
	
	private final Document document;

	public XSPFFile(Document document) {
		super();
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}
	@Override
	public Node getNode() {
		return document;
	}
	
/////////////////////////////////////////////////////////////////////////////////////
	
	

	
	public XSPFPlaylist getPlaylist() throws XSPFException {
		return getOne("playlist", (e) -> new XSPFPlaylist(e));
	}
	
	public XSPFPlaylist playlist() throws XSPFException {
		return one("playlist", (e) -> new XSPFPlaylist(e));
	}

	public void setPlaylist(XSPFPlaylist playlist) throws XSPFException {
		setOne("playlist", playlist);
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
		//FIXME: XSPFBase.UTIL.init(document);

		Element root = UTIL.getOrCreateChildElem(document, "playlist");
		XSPFNode.UTIL.setElementAttr(root, "version", XSPF_STANDART_VERSION, XMLDocumentUtilityHelper.ValueToTextMapper.STRING_TO_TEXT);
	}

	private static void verify(Document document) throws XSPFException {
		Element root = UTIL.getChildElemOrNull(document, "playlist");
		if (root == null) {
			throw new XSPFException("No root element.");
		}
		String version = XSPFNode.UTIL.getElementAttrOrNull(root, "version", XMLDocumentUtilityHelper.TextToValueMapper.TEXT_TO_STRING);
		if (!version.equals(XSPF_STANDART_VERSION)) {
			throw new XSPFException("The supported version of XSPF is " + XSPF_STANDART_VERSION);
		}
	}


/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		try {
			return "XSPFFile [playlist=" + playlist() + "]";
		} catch (XSPFException e) {
			return "XSPFFile [playlist=" + e + "]";
		}
	}
	
}
