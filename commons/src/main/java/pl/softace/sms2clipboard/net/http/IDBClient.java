package pl.softace.sms2clipboard.net.http;


/**
 * 
 * Interface used to access templates database on S3.
 * 
 * @author lkawon@gmail.com
 *
 */
public interface IDBClient {

	/**
	 * Gets available version.
	 * 
	 * @return	object representing latest version
	 */
	DBVersionInfo getAvailableVersion();
	
	/**
	 * Download DB version.
	 * 
	 * @param version		string representing version
	 * @param listener	used for indicating the progress
	 */
	void downloadDatabase(String version, IDownloadListener listener);
}
