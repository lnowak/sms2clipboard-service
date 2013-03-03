package pl.softace.sms2clipboard.utils;

/**
 * 
 * Utility class for strings.
 * 
 * @author lkawon@gmail.com
 *
 */
public final class StringUtils {

	/**
	 * A HREF tag.
	 */
	private static final String A_HREF = "<a href=\"";
	
	/**
	 * Close HREF tag.
	 */
	private static final String HREF_CLOSED = "\">";
	
	
	/**
	 * HREF end tag.
	 */
	private static final String HREF_END = "</a>";
	
	/**
	 * HTML tag.
	 */
	private static final String HTML = "<html>";
	
	/**
	 * HTML end tag. 
	 */
	private static final String HTML_END = "</html>";
	
	
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
	
	/**
	 * Wraps string with a href tag.
	 * 
	 * @param string		string to wrap
	 * @return				wrapped string
	 */
	public static String linkIfy(String string) {
		return A_HREF.concat(string).concat(HREF_CLOSED).concat(string).concat(HREF_END);
	}

	/**
	 * Wrap string with html tag.
	 * 
	 * @param string		string to wrap
	 * @return				wrapped string
	 */
	public static String htmlIfy(String string) {
		return HTML.concat(string).concat(HTML_END);
	}
}
