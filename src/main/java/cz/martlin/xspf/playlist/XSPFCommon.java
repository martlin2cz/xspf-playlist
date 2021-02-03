package cz.martlin.xspf.playlist;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFDocumentUtility;
import cz.martlin.xspf.util.XSPFException;

public abstract class XSPFCommon {
	protected static final XSPFDocumentUtility util = new XSPFDocumentUtility(null, "http://xspf.org/ns/0/");

	public XSPFCommon() {
		// TODO Auto-generated constructor stub
	}

	public XSPFDocumentUtility getUtil() {
		return util;
	}

	public abstract Element getElement();

	///////////////////////////////////////////////////////////////////////////

	public URI getLocation() throws XSPFException {
		return getUri("location");
	}

	public void setLocation(URI location) throws XSPFException {
		setUri("location", location);
	}

	public URI getIdentifier() throws XSPFException {
		return getUri("identifier");
	}

	public void setIdentifier(URI identifier) throws XSPFException {
		setUri("info", identifier);
	}

	public String getTitle() throws XSPFException {
		return get("title");
	}

	public void setTitle(String title) throws XSPFException {
		set("title", title);
	}

	public String getCreator() throws XSPFException {
		return get("creator");
	}

	public void setCreator(String creator) throws XSPFException {
		set("creator", creator);
	}

	public String getAnnotation() throws XSPFException {
		return get("annotation");
	}

	public void setAnnotation(String annotation) throws XSPFException {
		set("annotation", annotation);
	}

	public URI getInfo() throws XSPFException {
		return getUri("info");
	}

	public void setInfo(URI info) throws XSPFException {
		setUri("info", info);
	}

	public URI getImage() throws XSPFException {
		return getUri("image");
	}

	public void setImage(URI image) throws XSPFException {
		setUri("image", image);
	}

	public List<Element> getLinks() throws XSPFException {
		Element elem = getElement();
		return util.listChildrenElems(elem, "link");
	}

	public List<Element> getMetas() throws XSPFException {
		Element elem = getElement();
		return util.listChildrenElems(elem, "meta");
	}

	public Element getExtension() throws XSPFException {
		Element elem = getElement();
		return util.getOrCreateChildElem(elem, "extension");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private String uriToText(URI uri) {
		// TODO Auto-generated method stub
		return null;
	}

	private URI textToUri(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	private Duration milisStrToDuration(String text) {
		long milis = Long.parseLong(text);
		return Duration.ofMillis(milis);
	}

	private String durationToMilisStr(Duration duration) {
		long milis = duration.toMillis();
		return Long.toString(milis);
	}

	private LocalDateTime textToDate(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	private String dateToText(LocalDateTime date) {
		// TODO Auto-generated method stub
		return null;
	}

	private String intToStr(int num) {
		return Integer.toString(num);
	}

	private int strToInt(String text) {
		return Integer.parseInt(text);
		// TODO verify it's non-negative
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	protected void set(String name, String value) throws XSPFException {
		Element root = getElement();
		util.setElementText(root, name, value);
	}

	protected String get(String name) throws XSPFException {
		Element root = getElement();
		return util.getElementText(root, name);
	}

	protected URI getUri(String name) throws XSPFException {
		Element root = getElement();
		return util.getElementValue(root, name, (t) -> textToUri(t));
	}

	protected void setUri(String name, URI value) throws XSPFException {
		Element root = getElement();
		util.setElementValue(root, name, value, (v) -> uriToText(v));
	}

	protected LocalDateTime getDate(String name) throws XSPFException {
		Element root = getElement();
		return util.getElementValue(root, name, (t) -> textToDate(t));
	}

	protected void setDate(String name, LocalDateTime value) throws XSPFException {
		Element root = getElement();
		util.setElementValue(root, name, value, (v) -> dateToText(v));
	}

	protected Duration getDuration(String name) throws XSPFException {
		Element root = getElement();
		return util.getElementValue(root, name, (t) -> milisStrToDuration(t));
	}

	protected void setDuration(String name, Duration value) throws XSPFException {
		Element root = getElement();
		util.setElementValue(root, name, value, (v) -> durationToMilisStr(v));
	}

	protected int getInt(String name) throws XSPFException {
		Element root = getElement();
		return util.getElementValue(root, name, (t) -> strToInt(t));
	}

	protected void setInt(String name, int value) throws XSPFException {
		Element root = getElement();
		util.setElementValue(root, name, value, (v) -> intToStr(v));
	}

}
