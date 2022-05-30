package cz.martlin.xspf.util;

/**
 * An general unchecked (runtime) exception occured during the processing of the
 * XSPF file. This exception can get thrown in the cases, where the checked
 * exception is not applicable, i.e. during the Java streams operations.
 * 
 * Do not instantite and throw directly, use the {@link ExceptionWrapper} to do
 * so.
 * 
 * @author martin
 *
 */
public class XSPFRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3017910336990473246L;

	/**
	 * Creates instance.
	 * 
	 * @param message
	 * @param cause
	 */
	public XSPFRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates instance.
	 * 
	 * @param message
	 */
	public XSPFRuntimeException(String message) {
		super(message);
	}

	/**
	 * Creates instance.
	 * 
	 * @param cause
	 */
	public XSPFRuntimeException(Throwable cause) {
		super(cause);
	}

}
