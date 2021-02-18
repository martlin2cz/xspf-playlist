package cz.martlin.xspf.util;

import java.io.IOException;

/**
 * An general exception occured during the processing of the XSPF file. This may
 * indicate:
 * <ul>
 * <li>the file is completelly corrupted
 * <li>there is missing some mandatory elements or attributes
 * <li>there are some extra elements
 * <li>values (numbers, uris, ...) are in invalid format
 * <li>some other DOM-related failure happened
 * </ul>
 * 
 * @author martin
 *
 */
public class XSPFException extends IOException {

	private static final long serialVersionUID = -4664670263238691619L;

	/**
	 * Creates instance.
	 * 
	 * @param message
	 * @param cause
	 */
	public XSPFException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates instance.
	 * 
	 * @param message
	 */
	public XSPFException(String message) {
		super(message);
	}

}
