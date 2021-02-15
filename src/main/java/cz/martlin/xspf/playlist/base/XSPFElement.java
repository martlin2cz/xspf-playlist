package cz.martlin.xspf.playlist.base;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.util.XMLDocumentUtilityHelper;
import cz.martlin.xspf.util.XSPFException;

public abstract class XSPFElement extends XSPFNode {
	private final Element element;

	public XSPFElement(Element element) {
		super();
		Objects.requireNonNull(element, "The element has to be specified");
		this.element = element;
	}

	protected Element getElement() {
		return element;
	}
	
	
	private Element getElementClone() {
		Element container = getElement();
		return UTIL.getElemClone(container);
	}


	@Override
	public Node getNode() {
		return getElement();
	}

///////////////////////////////////////////////////////////////////////////

	private static String uriToStr(URI uri) {
		return uri.toASCIIString();
	}

	private static URI strToUri(String text) {
		return URI.create(text);
	}

	private Duration milisStrToDuration(String text) {
		long milis = Long.parseLong(text);
		return Duration.ofMillis(milis);
	}

	private static String durationToMilisStr(Duration duration) {
		long milis = duration.toMillis();
		return Long.toString(milis);
	}

	private LocalDateTime textToDate(String text) {
		TemporalAccessor ta = DateTimeFormatter.ISO_DATE_TIME.parse(text);
		return LocalDateTime.from(ta);
	}

	private static String dateToText(LocalDateTime date) {
		TemporalAccessor ta = date;
		return DateTimeFormatter.ISO_DATE_TIME.format(ta);
	}

	private static String intToStr(int num) {
		return Integer.toString(num);
	}

	private static int strToInt(String text) {
		return Integer.parseInt(text);
		// TODO verify it's non-negative
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	protected String getStr() throws XSPFException {
		Element elem = getElement();
		return UTIL.getElementValue(elem, XMLDocumentUtilityHelper.TextToValueMapper.TEXT_TO_STRING);
	}

	protected void setStr(String value) throws XSPFException {
		Element elem = getElement();
		UTIL.setElementValue(elem, value, XMLDocumentUtilityHelper.ValueToTextMapper.STRING_TO_TEXT);
	}

	protected void setStr(String name, String value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, XMLDocumentUtilityHelper.ValueToTextMapper.STRING_TO_TEXT);
	}

	protected String getStr(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValue(elem, name, XMLDocumentUtilityHelper.TextToValueMapper.TEXT_TO_STRING);
	}

	protected URI getUri(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValue(elem, name, (t) -> strToUri(t));
	}

	protected void setUri(String name, URI value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, (v) -> uriToStr(v));
	}
	
	protected URI getUriAttr(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getElementAttr(elem, name, (t) -> strToUri(t));
	}

	protected void setUriAttr(String name, URI value) throws XSPFException {
		Element elem = getElement();
		UTIL.setElementAttr(elem, name, value, (v) -> uriToStr(v));
	}

	protected LocalDateTime getDate(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValue(elem, name, (t) -> textToDate(t));
	}

	protected void setDate(String name, LocalDateTime value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, (v) -> dateToText(v));
	}

	protected Duration getDuration(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValue(elem, name, (t) -> milisStrToDuration(t));
	}

	protected void setDuration(String name, Duration value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, (v) -> durationToMilisStr(v));
	}

	protected int getInt(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValue(elem, name, (t) -> strToInt(t));
	}

	protected void setInt(String name, int value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, (v) -> intToStr(v));
	}

	protected URI getUri() throws XSPFException {
		Element elem = getElement();
		return UTIL.getElementValue(elem, (t) -> strToUri(t));
	}

	protected void setUri(URI value) throws XSPFException {
		Element elem = getElement();
		UTIL.setElementValue(elem, value, (v) -> uriToStr(v));
	}

///////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////

	protected <E extends XSPFElement> XSPFCollection<E> getCollection(XSPFCollectionFactory<E> factory)
			throws XSPFException {

		Element clone = getElementClone();
		return factory.create(clone);
	}


	protected <E extends XSPFElement> XSPFCollection<E> collection(XSPFCollectionFactory<E> factory)
			throws XSPFException {

		Element container = getElement();
		return factory.create(container);
	}

	protected <E extends XSPFElement, C extends XSPFCollection<E>> void setCollection(XSPFCollection<E> collection)
			throws XSPFException {

		Element container = getElement();
		String name = collection.elemName();

		// TODO FIXME niceie!
		Stream<Element> newElems = collection.list()//
				.map(x -> x.getElement());
				//.collect(Collectors.toList());
		UTIL.replaceChildElementsByClone(container, name, newElems);
	}

///////////////////////////////////////////////////////////////////////////////////////////////

	protected <E extends XSPFElement> XSPFCollection<E> getCollection(String name, XSPFCollectionFactory<E> factory)
			throws XSPFException {

		Element owner = getElement();
		Element coontainerClone = UTIL.getChildElemClone(owner, name);
		return factory.create(coontainerClone);
	}

	protected <E extends XSPFElement> XSPFCollection<E> collection(String name, XSPFCollectionFactory<E> factory)
			throws XSPFException {

		Element owner = getElement();
		Element container = UTIL.getOrCreateChildElem(owner, name);
		return factory.create(container);
	}

	protected <E extends XSPFElement, C extends XSPFCollection<E>> void setCollection(String name,
			XSPFCollection<E> collection) throws XSPFException {

		//TODO get(OrCreate?)(Tracks)list elem, work iwth that

		Element owner = getElement();
		Element container = UTIL.getOrCreateChildElem(owner, name);
		String childrenName = collection.elemName();

		// TODO FIXME niceie!
		Stream<Element> newElems = collection.list() //
				.map(x -> x.getElement()) //
				.map(e -> UTIL.getElemClone(e)); //
//				.collect(Collectors.toList()); //
		UTIL.replaceChildElementsByClone(container, childrenName, newElems);
	}

///////////////////////////////////////////////////////////////////////////////////////////////

	@FunctionalInterface
	public static interface XSPFCollectionFactory<E extends XSPFElement> {
//		public <C extends XSPFCollection<E>> C create(Element container);
		public XSPFCollection<E> create(Element container);
	}

}
