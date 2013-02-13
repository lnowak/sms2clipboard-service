package pl.softace.passwordless.utils;

/**
 * 
 * Utility class for strings.
 * 
 * @author lkawon@gmail.com
 *
 */
public final class StringUtils {

	/**
	 * Hidden constructor.
	 */
	private StringUtils() {
		
	}
	
	
	/**
	 * Prints byte array as hex string.
	 * 
	 * @param bytes				byte array
	 * @return					String
	 */
	public static final String byteArrayToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for(byte b: bytes) {
			sb.append(String.format("0x%02x ", b&0xff));
		}
   
		return sb.toString();
	}
}
