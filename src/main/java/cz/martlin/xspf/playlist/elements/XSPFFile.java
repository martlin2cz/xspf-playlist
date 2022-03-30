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

/**
 * The whole xspf file. Contains {@link XSPFPlaylist} instance.
 * 
 * Can be created ({@link #create()}) or loaded ({@link #load(File)}). Then can
 * be saved ({@link #save(File)}).
 * 
 * @author martin
 *
 */
public class XSPFFile extends XSPFNode {

	/**
	 * The supported version of the XSPF file standart.
	 */
	protected static final String XSPF_STANDART_VERSION = "1";

	/**
	 * The actual XSPF file XML document.
	 */
	private final Document document;

	/**
	 * Creates instance. To create brand new playlist, use {@link #create()}.
	 * 
	 * @param document the XSPF file XML document.
	 */
	protected XSPFFile(Document document) {
		super();
		this.document = document;
	}

	/**
	 * Returns the playlist XML document.
	 * 
	 * @return
	 */
	public Document getDocument() {
		return document;
	}

	@Override
	public Node getNode() {
		return document;
	}

/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the (copy of) the playlist.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1
	 * @return
	 * @throws XSPFException
	 */
	public XSPFPlaylist getPlaylist() throws XSPFException {
		return getOne(Names.PLAYLIST, (e) -> new XSPFPlaylist(e));
	}

	/**
	 * Return the (view of) the playlist.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1
	 * @return
	 * @throws XSPFException
	 */
	public XSPFPlaylist playlist() throws XSPFException {
		return one(Names.PLAYLIST, (e) -> new XSPFPlaylist(e));
	}

	/**
	 * Sets the (copy of) the playlist.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1
	 * @param playlist
	 * @throws XSPFException
	 */
	public void setPlaylist(XSPFPlaylist playlist) throws XSPFException {
		setOne(Names.PLAYLIST, playlist);
	}

/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Creates new collection of metas.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.12
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.12
	 * @return
	 * @throws XSPFException
	 */
	public XSPFMetas newMetas() throws XSPFException {
		Element root = UTIL.createNewElement(document, Names.PLAYLIST);
		return new XSPFMetas(root);
	}

	/**
	 * Creates new collection of links.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.11
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.11
	 * @return
	 * @throws XSPFException
	 */
	public XSPFLinks newLinks() throws XSPFException {
		Element root = UTIL.createNewElement(document, Names.PLAYLIST);
		return new XSPFLinks(root);
	}

	/**
	 * Creates new collection of extensions.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.13
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14.1.1.1.13
	 * @return
	 * @throws XSPFException
	 */
	public XSPFExtensions newExtensions() throws XSPFException {
		Element root = UTIL.createNewElement(document, Names.PLAYLIST);
		return new XSPFExtensions(root);
	}

	/**
	 * Creates new collection of tracks.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.14
	 * @return
	 * @throws XSPFException
	 */
	public XSPFTracks newTracks() throws XSPFException {
		Element trackList = UTIL.createNewElement(document, Names.TRACK_LIST);
		return new XSPFTracks(trackList);
	}

	/**
	 * Creates new playlist.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1
	 * @return
	 * @throws XSPFException
	 */
	public XSPFPlaylist newPlaylist() throws XSPFException {
		Element playlist = UTIL.createNewElement(document, Names.PLAYLIST);
		return new XSPFPlaylist(playlist);
	}

	/**
	 * Creates new attribution.
	 * 
	 * @see https://xspf.org/xspf-v1.html#rfc.section.4.1.1.2.10
	 * @return
	 * @throws XSPFException
	 */
	public XSPFAttribution newAttribution() throws XSPFException {
		Element attribution = UTIL.createNewElement(document, Names.ATTRIBUTION);
		return new XSPFAttribution(attribution);
	}
/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Loads the XSPF playlist from the given file.
	 * 
	 * @param file
	 * @return
	 * @throws XSPFException
	 */
	public static XSPFFile load(File file) throws XSPFException {
		Document document = XMLFileLoaderStorer.loadDocument(file);
		verify(document);
		return new XSPFFile(document);
	}

	/**
	 * Creates empty xspf file.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	public static XSPFFile create() throws XSPFException {
		Document document = XMLFileLoaderStorer.createEmptyDocument();
		prepare(document);
		return new XSPFFile(document);
	}

	/**
	 * Saves this xspf file to the given file.
	 * 
	 * @param file
	 * @throws XSPFException
	 */
	public void save(File file) throws XSPFException {
		XMLFileLoaderStorer.saveDocument(document, file);
	}

/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Prepares the (empty) document to be used as XSPF document.
	 * 
	 * @param document
	 * @throws XSPFException
	 */
	private static void prepare(Document document) throws XSPFException {
		Element root = UTIL.getOrCreateChildElem(document, Names.PLAYLIST);
		UTIL.register(root);

		specifyVersion(root);
	}

	/**
	 * Sets the version attribute of the given root element to the supported one.
	 * 
	 * @param root
	 * @throws XSPFException
	 */
	private static void specifyVersion(Element root) throws XSPFException {
		XSPFNode.UTIL.setElementAttr(root, Names.VERSION, XSPF_STANDART_VERSION,
				XMLDocumentUtilityHelper.NullableValueToTextMapper.STRING_TO_TEXT);
	}

	/**
	 * Verifies whether the (existing) document is valid XSPF document.
	 * 
	 * @param document
	 * @throws XSPFException
	 */
	private static void verify(Document document) throws XSPFException {
		Element root = UTIL.getChildElemOrNull(document, Names.PLAYLIST);
		if (root == null) {
			throw new XSPFException("No root element.");
		}
		verifyVersion(root);
	}

	/**
	 * Verifies whether the version attribute of the given root element has the
	 * expected (supported) value.
	 * 
	 * @param root
	 * @throws XSPFException
	 */
	private static void verifyVersion(Element root) throws XSPFException {
		String version = XSPFNode.UTIL.getElementAttrOrNull(root, Names.VERSION,
				XMLDocumentUtilityHelper.NullableTextToValueMapper.TEXT_TO_STRING);

		if (!version.equals(XSPF_STANDART_VERSION)) {
			throw new XSPFException("The supported version of XSPF is " + XSPF_STANDART_VERSION);
		}
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		try {
			return Objects.hash(/* document, */playlist());
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
			return /* Objects.equals(document, other.document) && */ Objects.equals(playlist(), other.playlist());
		} catch (XSPFException e) {
			return false;
		}
	}

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

}
