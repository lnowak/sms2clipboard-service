package pl.softace.sms2clipboard.security;

/**
 * 
 * Crypt exception.
 * 
 * @author lkawon@gmail.com
 *
 */
public class CryptException extends RuntimeException {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 5401413412635521035L;

	
	/**
	 * Constructor.
	 * 
	 * @param message	exception message
	 */
	public CryptException(String message) {
		super(message);
	}	
	
	/**
	 * Constructor.
	 * 
	 * @param message		exception message
	 * @param throwable		exception
	 */
	public CryptException(String message, Throwable throwable) {
		super(message, throwable);
	}	
}
