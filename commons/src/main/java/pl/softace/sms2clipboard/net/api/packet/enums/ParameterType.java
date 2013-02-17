package pl.softace.sms2clipboard.net.api.packet.enums;

/**
 * 
 * Data type of the parameter.
 * 
 * @author lkawon@gmail.com
 *
 */
public enum ParameterType {
	
	/**
	 * Integer type.
	 */
	INTEGER				(4),
	
	/**
	 * Long type.
	 */
	LONG				(8),
	
	/**
	 * String type.
	 */
	STRING				(0),
	
	/**
	 * String secured with AES encryption.
	 */
	SECURED_STRING		(0),
	
	/**
	 * List of strings.
	 */
	STRING_LIST			(0),
	
	/**
	 * Boolean type.
	 */
	BOOLEAN				(1),
	
	/**
	 * Character type.
	 */
	CHARACTER			(1);
	
	
	/**
	 * Length of the type.
	 */
	private int length;
		
	/**
	 * Constructor.
	 * 
	 * @param length		length of the type
	 */
	private ParameterType(int length) {
		this.length = length;
	}

	public final int getLength() {
		return length;
	}

	public final void setLength(int length) {
		this.length = length;
	}		
}