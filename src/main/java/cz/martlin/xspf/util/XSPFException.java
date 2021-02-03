package cz.martlin.xspf.util;

import java.io.IOException;

public class XSPFException extends IOException {

	private static final long serialVersionUID = -4664670263238691619L;

	public XSPFException(String message, Throwable cause) {
		super(message, cause);
	}

	public XSPFException(String message) {
		super(message);
	}

}
