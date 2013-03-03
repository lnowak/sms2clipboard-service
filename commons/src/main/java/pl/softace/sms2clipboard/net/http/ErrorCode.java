package pl.softace.sms2clipboard.net.http;

/**
 * 
 * Error code for download status indication.
 * 
 * @author lkawon@gmail.com
 *
 */
public enum ErrorCode {

	/**
	 * Finished successfully.
	 */
	FINISHED,
	
	/**
	 * Database not found.
	 */
	NOT_FOUND,
	
	/**
	 * Undefined download error.
	 */
	DOWNLOAD_ERROR;
}
