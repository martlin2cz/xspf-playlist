package cz.martlin.xspf.playlist;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFException;
import cz.martlin.xspf.util.XMLFileLoaderStorer;
import cz.martlin.xspf.util.XSPFDocumentUtility;

public class XSPFPlaylist extends XSPFCommon /* implements BaseXSPFElement */ {
	protected static final String XSPF_STANDART_VERSION = "1";
	private final Document document;

	public XSPFPlaylist(Document document) throws XSPFException {
		super();
		
		Objects.requireNonNull(document, "The document must be provided");
		this.document = document;
		util.init(document);
	}

	public Document getDocument() {
		return document;
	}
	
	@Override
	public Element getElement() {
		return getRootElement();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	public LocalDateTime getDate() throws XSPFException {
		return getDate("date");
	}

	public void setDate(LocalDateTime date) throws XSPFException {
		setDate("date", date);
	}

	public URI getLicence() throws XSPFException {
		return getUri("info");
	}

	public void setLicence(URI licence) throws XSPFException {
		setUri("licence", licence);
	}
	
	public Element getAttribution() throws XSPFException {
		Element root = getRootElement();
		return util.getChildElem(root, "attribution");
	}

	public List<XSPFTrack> getTracks() throws XSPFException {
		Element root = getRootElement();
		Element trackList = util.getChildElem(root, "trackList");
		return util.listChildrenElems(trackList, "track").stream() //
				.map(e -> new XSPFTrack(e)) //
				.collect(Collectors.toList()); //
	}

	public XSPFTrack newTrack() {
		Element element = document.createElement("track");
		return new XSPFTrack(element);
	}

	public void setTracks(List<XSPFTrack> tracks) throws XSPFException {
		Element root = getRootElement();
		Element trackList = util.replaceChildElem(root, "trackList");
		for (XSPFTrack track : tracks) {
			Element trackElem = track.getElement();
			trackList.appendChild(trackElem);
		}
	}


	/////////////////////////////////////////////////////////////////////////////////////

	private URI textToUri(String value) {
		return URI.create(value);
	}
	
	private String uriToText(URI uri) {
		return uri.toASCIIString();
	}

	private LocalDateTime textToDate(String value) {
		return LocalDateTime.parse(value);
	}
	
	private String dateToText(LocalDateTime date) {
		return date.toString();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		try {
			return "XSPFPlaylist [getTitle()=" + getTitle() + ", getTracks()=" + getTracks() + "]";
		} catch (XSPFException e) {
			return "XSPFPlaylist [Error: " + e + "]";
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public static XSPFPlaylist load(File file) throws XSPFException {
		Document document = XMLFileLoaderStorer.loadDocument(file);
		verify(document);
		return new XSPFPlaylist(document);
	}

	public static XSPFPlaylist create() throws XSPFException {
		Document document = XMLFileLoaderStorer.createEmptyDocument();
		prepare(document);
		return new XSPFPlaylist(document);
	}

	public void save(File file) throws XSPFException {
		XMLFileLoaderStorer.saveDocument(document, file);
	}

	private static void prepare(Document document) throws XSPFException {
		Element root = util.getOrCreateRootElem(document, "playlist");
		util.setElementAttr(root, "version", XSPF_STANDART_VERSION);
	}

	private static void verify(Document document) throws XSPFException {
		Element root = util.getRootElem(document, "playlist");
		String version = util.getElementAttr(root, "version");
		if (!version.equals(XSPF_STANDART_VERSION)) {
			throw new XSPFException("The supported version of XSPF is " + XSPF_STANDART_VERSION);
		}
	}
	

	/////////////////////////////////////////////////////////////////////////////////////

	private Element getRootElement() {
		return document.getDocumentElement();
	}
	
	
}
