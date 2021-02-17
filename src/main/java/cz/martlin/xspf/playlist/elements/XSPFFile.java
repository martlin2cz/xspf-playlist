package cz.martlin.xspf.playlist.elements;

import java.io.File;
import java.util.Objects;

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

	public XSPFPlaylist getPlaylist()throws XSPFException {
		return getOne("playlist", (e) -> new XSPFPlaylist(e));
	}

	public XSPFPlaylist playlist()throws XSPFException {
		return one("playlist", (e) -> new XSPFPlaylist(e));
	}

	public void setPlaylist(XSPFPlaylist playlist)throws XSPFException {
		setOne("playlist", playlist);
	}

/////////////////////////////////////////////////////////////////////////////////////

	public static XSPFFile load(File file)throws XSPFException {
		Document document = XMLFileLoaderStorer.loadDocument(file);
		verify(document);
		return new XSPFFile(document);
	}

	public static XSPFFile create()throws XSPFException {
		Document document = XMLFileLoaderStorer.createEmptyDocument();
		prepare(document);
		return new XSPFFile(document);
	}

	public void save(File file)throws XSPFException {
		XMLFileLoaderStorer.saveDocument(document, file);
	}

/////////////////////////////////////////////////////////////////////////////////////

	private static void prepare(Document document)throws XSPFException {
		Element root = UTIL.getOrCreateChildElem(document, "playlist");
		UTIL.register(root);

		XSPFNode.UTIL.setElementAttr(root, "version", XSPF_STANDART_VERSION,
				XMLDocumentUtilityHelper.ValueToTextMapper.STRING_TO_TEXT);
	}

	private static void verify(Document document)throws XSPFException {
		Element root = UTIL.getChildElemOrNull(document, "playlist");
		if (root == null) {
			throw new XSPFException("No root element.");
		}
		verifyVersion(root);
	}

	private static void verifyVersion(Element root)throws XSPFException {
		String version = XSPFNode.UTIL.getElementAttrOrNull(root, "version",
				XMLDocumentUtilityHelper.TextToValueMapper.TEXT_TO_STRING);
		
		if (!version.equals(XSPF_STANDART_VERSION)) {
			throw new XSPFException("The supported version of XSPF is " + XSPF_STANDART_VERSION);
		}
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("XSPFFile [");
		try {
			builder.append(getPlaylist());
		} catch (XSPFException e) {
			builder.append(e);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		try {
			return Objects.hash(document, playlist());
		} catch (XSPFException e) {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XSPFFile other = (XSPFFile) obj;
		try {
			return Objects.equals(document, other.document) && Objects.equals(playlist(), other.playlist());
		} catch (XSPFException e) {
			return false;
		}
	}
	
	
}
