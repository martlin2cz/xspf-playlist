package cz.martlin.xspf.playlist.elements;

import java.io.File;
import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.playlist.base.XSPFNode;
import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.collections.XSPFLinks;
import cz.martlin.xspf.playlist.collections.XSPFMetas;
import cz.martlin.xspf.playlist.collections.XSPFTracks;
import cz.martlin.xspf.util.Names;
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
		return getOne(Names.PLAYLIST, (e) -> new XSPFPlaylist(e));
	}

	public XSPFPlaylist playlist() throws XSPFException {
		return one(Names.PLAYLIST, (e) -> new XSPFPlaylist(e));
	}

	public void setPlaylist(XSPFPlaylist playlist) throws XSPFException {
		setOne(Names.PLAYLIST, playlist);
	}

/////////////////////////////////////////////////////////////////////////////////////
	public XSPFMetas newMetas() throws XSPFException {
		Element root = UTIL.createNewElement(document, Names.PLAYLIST);
		return new XSPFMetas(root);
	}

	public XSPFLinks newLinks() throws XSPFException {
		Element root = UTIL.createNewElement(document, Names.PLAYLIST);
		return new XSPFLinks(root);
	}

	public XSPFExtensions newExtensions() throws XSPFException {
		Element root = UTIL.createNewElement(document, Names.PLAYLIST);
		return new XSPFExtensions(root);
	}

	public XSPFTracks newTracks() throws XSPFException {
		Element trackList = UTIL.createNewElement(document, Names.TRACK_LIST);
		return new XSPFTracks(trackList);
	}

	public XSPFAttribution newAttribution() throws XSPFException {
		Element attribution = UTIL.createNewElement(document, Names.ATTRIBUTION);
		return new XSPFAttribution(attribution);
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
		Element root = UTIL.getOrCreateChildElem(document, Names.PLAYLIST);
		UTIL.register(root);

		XSPFNode.UTIL.setElementAttr(root, Names.VERSION, XSPF_STANDART_VERSION,
				XMLDocumentUtilityHelper.ValueToTextMapper.STRING_TO_TEXT);
	}

	private static void verify(Document document) throws XSPFException {
		Element root = UTIL.getChildElemOrNull(document, Names.PLAYLIST);
		if (root == null) {
			throw new XSPFException("No root element.");
		}
		verifyVersion(root);
	}

	private static void verifyVersion(Element root) throws XSPFException {
		String version = XSPFNode.UTIL.getElementAttrOrNull(root, Names.VERSION,
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
			return Objects.hash(/*document, */playlist());
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
			return /*Objects.equals(document, other.document) &&*/ Objects.equals(playlist(), other.playlist());
		} catch (XSPFException e) {
			return false;
		}
	}

}
