package cz.martlin.xspf.playlist;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XMLDocumentUtility;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper;
import cz.martlin.xspf.util.XSPFException;

public abstract class XSPFBase {
	protected static final XMLDocumentUtility UTIL = new XMLDocumentUtility(null, "http://xspf.org/ns/0/");

	public XSPFBase() {
		// TODO Auto-generated constructor stub
	}

	public abstract Element getElement();

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

	protected void setStr(String name, String value) throws XSPFException {
		Element elem = getElement();
		UTIL.setChildElementText(elem, name, value);
	}

	protected String getStr(String name) throws XSPFException {
		Element elem = getElement();
		return UTIL.getChildElementText(elem, name);
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
	
	protected String getStr() throws XSPFException {
		Element elem = getElement();
		return UTIL.getElementValue(elem, XMLDocumentUtilityHelper.TextToValueMapper.TEXT_TO_STRING);
	}

	protected void setStr(String value) throws XSPFException {
		Element elem = getElement();
		UTIL.setElementValue(elem, value, XMLDocumentUtilityHelper.ValueToTextMapper.STRING_TO_TEXT);
	}
///////////////////////////////////////////////////////////////////////////////////////////////

	protected <T extends XSPFBase> T createOne(String name, Function<Element, T> mapper) {
		Element elem = getElement().getOwnerDocument().createElement(name); // TODO use util, w.r.t NS
		return mapper.apply(elem);
	}


	protected <T extends XSPFBase> T getOne(String name, Function<Element, T> mapper) throws XSPFException {
		Element elem = getElement();
		Element child = UTIL.getChildElem(elem, name);
		return mapper.apply(child);
	}
	
	protected <T extends XSPFBase> void setOne(String name, T value) throws XSPFException {
		Element elem = getElement();
		Element newElem = value.getElement();
		UTIL.replaceChildElem(elem, name, newElem);
	}
	
	protected <T extends XSPFBase> List<T> getAll(String name, Function<Element, T> mapper) {
		Element elem = getElement();
		return UTIL.listChildrenElems(elem, name).stream() //
				.map(mapper) //
				.collect(Collectors.toList());
	}

	protected <T extends XSPFBase> void setAll(String name, List<T> items) throws XSPFException {
		Element elem = getElement();
		List<Element> elements = items.stream() //
				.map((i) -> i.getElement()) //
				.collect(Collectors.toList());
				
		UTIL.replaceAllChildren(elem, name, elements);
	}
}
