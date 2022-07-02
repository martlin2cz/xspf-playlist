package cz.martlin.xspf.playlist;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.w3c.dom.Element;

import cz.martlin.xspf.util.XSPFException;
import cz.martlin.xspf.util.XSPFDocumentUtility;

// replaced by XSPFCommon
@Deprecated
public interface BaseXSPFElement {

	public XSPFDocumentUtility getUtil();
	
	public String getTitle() throws XSPFException;

	public void setTitle(String title) throws XSPFException;

	public String getCreator() throws XSPFException;

	public void setCreator(String creator) throws XSPFException;
	
	public String getAnnotation() throws XSPFException;

	public void setAnnotation(String annotation) throws XSPFException;
	
	public URI getInfo() throws XSPFException;

	public void setInfo(URI info) throws XSPFException;

	public URI getLocation() throws XSPFException;

	public void setLocation(URI location) throws XSPFException;
	
	public URI getIdentifier() throws XSPFException;

	public void setIdentifier(URI identifier) throws XSPFException;
	
	public URI getImage() throws XSPFException;

	public void setImage(URI image) throws XSPFException;
	
	public LocalDateTime getDate() throws XSPFException;

	public void setDate(LocalDateTime date) throws XSPFException;
	
	public URI getLicence() throws XSPFException;

	public void setLicence(URI licence) throws XSPFException;
	
	public Element getAttribution() throws XSPFException;

	public List<Element> getLinks() throws XSPFException;
	
	public List<Element> getMetas() throws XSPFException;
	

	public Element getExtension() throws XSPFException;



}