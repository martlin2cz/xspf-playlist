package cz.martlin.xspf.playlist;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFException;
import cz.martlin.xspf.util.XSPFDocumentUtility;

public interface BaseXSPFElement {

	XSPFDocumentUtility getUtil();
	
	String getTitle() throws XSPFException;

	void setTitle(String title) throws XSPFException;

	Element getExtension() throws XSPFException;



}