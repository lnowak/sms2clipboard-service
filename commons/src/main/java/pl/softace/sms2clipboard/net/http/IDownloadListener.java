package pl.softace.sms2clipboard.net.http;

/**
 * 
 * Interface used to support progress bar.
 * 
 * @author lkawon@gmail.com
 *
 */
public interface IDownloadListener {

	/**
	 * Invoked when progress is changed.
	 * 
	 * @param value		new value of the progress
	 * @return			true to continue or false to stop downloading
	 */
	boolean updateProgress(int value);
	
	/**
	 * Method used to indicate that download process finished.
	 * 
	 * @param errorCode		status of the download
	 */
	void finished(ErrorCode errorCode);
}
