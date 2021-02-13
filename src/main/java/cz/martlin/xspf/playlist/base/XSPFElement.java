package cz.martlin.xspf.playlist.base;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

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

}
