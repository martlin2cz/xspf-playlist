package cz.martlin.xspf.playlist.base;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import java.util.stream.Stream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableTextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableValueToTextMapper;
import cz.martlin.xspf.util.XSPFException;

/**
 * The base element in the xspf-playlist implementation. Encapsulates some XML
 * element and provides some helpfull methods, like to list children or set
 * attribute value.
 * 
 * @author martin
 *
 */
public abstract class XSPFElement extends XSPFNode {
	/**
	 * The actual XML element.
	 */
	private final Element element;

	/**
	 * Creates instance for the given element.
	 * 
	 * @param element
	 */
	public XSPFElement(Element element) {
		super();
		Objects.requireNonNull(element, "The element has to be specified");
		this.element = element;
	}

	/**
	 * Returns the element.
	 * 
	 * @return
	 */
	protected Element getElement() {
		return element;
	}

	/**
	 * Returns the clone of this element.
	 * 
	 * @return
	 */
	private Element getElementClone() {
		Element container = getElement();
		return UTIL.getElemClone(container);
	}

	@Override
	public Node getNode() {
		return getElement();
	}

///////////////////////////////////////////////////////////////////////////

	/**
	 * Converts uri to its textual representation.
	 * 
	 * @param uri
	 * @return
	 */
	private static String uriToStr(URI uri) {
		return uri.toASCIIString();
	}

	/**
	 * Converts uri textual representation to uri.
	 * 
	 * @param text
	 * @return
	 */
	private static URI strToUri(String text) {
		return URI.create(text);
	}

	/**
	 * Converts string with duration in miliseconds to actual duration.
	 * 
	 * @param text
	 * @return
	 */
	private static Duration milisStrToDuration(String text) {
		long milis = Long.parseLong(text);
		return Duration.ofMillis(milis);
	}

	/**
	 * Converts duration to string with duration in miliseconds.
	 * 
	 * @param duration
	 * @return
	 */
	private static String durationToMilisStr(Duration duration) {
		long milis = duration.toMillis();
		return Long.toString(milis);
	}

	/**
	 * Converts text with ISO datetime format to local date time.
	 * 
	 * @param text
	 * @return
	 */
	private static LocalDateTime textToDate(String text) {
		TemporalAccessor ta = DateTimeFormatter.ISO_DATE_TIME.parse(text);
		return LocalDateTime.from(ta);
	}

	/**
	 * Converts the local date time to ISO formatted datetime.
	 * 
	 * @param date
	 * @return
	 */
	private static String dateToText(LocalDateTime date) {
		TemporalAccessor ta = date;
		return DateTimeFormatter.ISO_DATE_TIME.format(ta);
	}

	/**
	 * Converts the integer to the text.
	 * 
	 * @param num
	 * @return
	 */
	private static String intToStr(int num) {
		return Integer.toString(num);
	}

	/**
	 * Converts the text to the integer.
	 * 
	 * @param text
	 * @return
	 */
	private static int strToInt(String text) {
		return Integer.parseInt(text);
		// TODO verify it's non-negative
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns actual element's string contents.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	protected String getStr() throws XSPFException {
		Element elem = getElement();
		return UTIL.getElementValueOrNull(elem, // 
				NullableTextToValueMapper.TEXT_TO_STRING);
	}

	/**
	 * Get string contents of this element's child of given name.
	 * 
	 * @param name
	 * @return
	 * @throws XSPFException
	 */
	protected String getStr(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValueOrNull(elem, name, // 
				NullableTextToValueMapper.TEXT_TO_STRING);
	}

	/**
	 * Sets actual element's string contents.
	 * 
	 * @param value
	 * @throws XSPFException
	 */
	protected void setStr(String value) throws XSPFException {
		Element elem = getElement();
		UTIL.setElementValue(elem, value, // 
				NullableValueToTextMapper.STRING_TO_TEXT);
	}

	/**
	 * Sets string contents of this element's child of given name.
	 * 
	 * @param name
	 * @param value
	 * @throws XSPFException
	 */
	protected void setStr(String name, String value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, // 
				NullableValueToTextMapper.STRING_TO_TEXT);
	}

	/**
	 * Gets uri contents of this element's child of given name.
	 * 
	 * @param name
	 * @return
	 * @throws XSPFException
	 */
	protected URI getUri(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValueOrNull(elem, name, // 
				NullableTextToValueMapper.checked(XSPFElement::strToUri));
	}

	/**
	 * Sets uri contents of this element's child of given name.
	 * 
	 * @param name
	 * @param value
	 * @throws XSPFException
	 */
	protected void setUri(String name, URI value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, //
				NullableValueToTextMapper.checked(XSPFElement::uriToStr));
	}

	/**
	 * Gets uri contents of this element's attribute of given name.
	 * 
	 * @param name
	 * @return
	 * @throws XSPFException
	 */
	protected URI getUriAttr(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getElementAttrOrNull(elem, name, //
				NullableTextToValueMapper.checked(XSPFElement::strToUri));
	}

	/**
	 * Sets uri contents of this element's attribute of given name.
	 * 
	 * @param name
	 * @param value
	 * @throws XSPFException
	 */
	protected void setUriAttr(String name, URI value) throws XSPFException {
		Element elem = getElement();
		UTIL.setElementAttr(elem, name, value, //
				NullableValueToTextMapper.checked(XSPFElement::uriToStr));
	}

	/**
	 * Gets datetime contents of this element's child of given name.
	 * 
	 * @param name
	 * @return
	 * @throws XSPFException
	 */
	protected LocalDateTime getDate(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValueOrNull(elem, name, //
				NullableTextToValueMapper.checked(XSPFElement::textToDate));
	}

	/**
	 * Sets datetime contents of this element's child of given name.
	 * 
	 * @param name
	 * @param value
	 * @throws XSPFException
	 */
	protected void setDate(String name, LocalDateTime value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, //
				NullableValueToTextMapper.checked(XSPFElement::dateToText));
	}

	/**
	 * Gets duration contents of this element's child of given name.
	 * 
	 * @param name
	 * @return
	 * @throws XSPFException
	 */
	protected Duration getDuration(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValueOrNull(elem, name, //
				NullableTextToValueMapper.checked(XSPFElement::milisStrToDuration));
	}

	/**
	 * Sets duration contents of this element's child of given name.
	 * 
	 * @param name
	 * @param value
	 * @throws XSPFException
	 */
	protected void setDuration(String name, Duration value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, //
				NullableValueToTextMapper.checked(XSPFElement::durationToMilisStr));
	}

	/**
	 * Gets integer contents of this element's child of given name.
	 * 
	 * @param name
	 * @return
	 * @throws XSPFException
	 */
	protected Integer getInt(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementValueOrNull(elem, name, //
				NullableTextToValueMapper.checked(XSPFElement::strToInt));
	}

	/**
	 * Set integer contents of this element's child of given name.
	 * 
	 * @param name
	 * @param value
	 * @throws XSPFException
	 */
	protected void setInt(String name, int value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementValue(elem, name, value, //
				NullableValueToTextMapper.checked(XSPFElement::intToStr));
	}

	/**
	 * Gets uri contents of this element.
	 * 
	 * @return
	 * @throws XSPFException
	 */
	protected URI getUri() throws XSPFException {
		Element elem = getElement();
		return UTIL.getElementValueOrNull(elem, //
				NullableTextToValueMapper.checked(XSPFElement::strToUri));
	}

	/**
	 * Sets uri contents of this element.
	 * 
	 * @param value
	 * @throws XSPFException
	 */
	protected void setUri(URI value) throws XSPFException {
		Element elem = getElement();
		UTIL.setElementValue(elem, value, //
				NullableValueToTextMapper.checked(XSPFElement::uriToStr));
	}

///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Returns the collection (clone) mapped to this element, constructed by the
	 * given factory.
	 * 
	 * @param <E>
	 * @param factory
	 * @return
	 * @throws XSPFException
	 */
	protected <E extends XSPFElement> XSPFCollection<E> getCollection(XSPFCollectionFactory<E> factory)
			throws XSPFException {

		Element clone = getElementClone();
		return factory.create(clone);
	}

	/**
	 * Returns the collection (view) mapped to this element, constructed by the
	 * given factory.
	 * 
	 * @param <E>
	 * @param factory
	 * @return
	 * @throws XSPFException
	 */
	protected <E extends XSPFElement> XSPFCollection<E> collection(XSPFCollectionFactory<E> factory)
			throws XSPFException {

		Element container = getElement();
		return factory.create(container);
	}

	/**
	 * Sets the collection (clone) mapped to this element.
	 * 
	 * @param <E>
	 * @param <C>
	 * @param collection
	 * @throws XSPFException
	 */
	protected <E extends XSPFElement, C extends XSPFCollection<E>> void setCollection(XSPFCollection<E> collection)
			throws XSPFException {

		Element container = getElement();
		String name = collection.elemName();

		// TODO FIXME niceie!
		Stream<Element> newElems = collection.list()//
				.map(x -> x.getElement());
		// .collect(Collectors.toList());
		UTIL.replaceChildElementsByClone(container, name, newElems);
	}

///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the collection (clone) mapped to this element's child of given name,
	 * constructed by the given factory.
	 * 
	 * @param <E>
	 * @param name
	 * @param factory
	 * @return
	 * @throws XSPFException
	 */
	protected <E extends XSPFElement> XSPFCollection<E> getCollection(String name, XSPFCollectionFactory<E> factory)
			throws XSPFException {

		Element owner = getElement();
		Element coontainerClone = UTIL.getChildElemCloneOrNull(owner, name);
		if (coontainerClone == null) {
			return null;
		}
		return factory.create(coontainerClone);
	}

	/**
	 * Returns the collection (view) mapped to this element's child of given name,
	 * constructed by the given factory.
	 * 
	 * @param <E>
	 * @param name
	 * @param factory
	 * @return
	 * @throws XSPFException
	 */
	protected <E extends XSPFElement> XSPFCollection<E> collection(String name, XSPFCollectionFactory<E> factory)
			throws XSPFException {

		Element owner = getElement();
		Element container = UTIL.getOrCreateChildElem(owner, name);
		return factory.create(container);
	}

	/**
	 * Sets the collection (clone) mapped to this element's child of given name.
	 * 
	 * @param <E>
	 * @param <C>
	 * @param name
	 * @param collection
	 * @throws XSPFException
	 */
	protected <E extends XSPFElement, C extends XSPFCollection<E>> void setCollection(String name,
			XSPFCollection<E> collection) throws XSPFException {

		// TODO get(OrCreate?)(Tracks)list elem, work iwth that

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

	/**
	 * The mapping of element to {@link XSPFCollection} instance. In the most of
	 * cases just constructor of such collection class.
	 * 
	 * @author martin
	 *
	 * @param <E>
	 */
	@FunctionalInterface
	public static interface XSPFCollectionFactory<E extends XSPFElement> {
//		public <C extends XSPFCollection<E>> C create(Element container);
		public XSPFCollection<E> create(Element container);
	}

}
