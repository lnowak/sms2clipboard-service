package pl.softace.sms2clipboard.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.SMS2Clipboard;
import pl.softace.sms2clipboard.config.ConfigurationManager;
import pl.softace.sms2clipboard.net.http.DBVersionInfo;
import pl.softace.sms2clipboard.net.http.IDBClient;
import pl.softace.sms2clipboard.net.http.impl.S3DBClient;

/**
 * 
 * Thread that is checking for updates.
 * 
 * @author lkawon@gmail.com
 *
 */
public class UpdateCheckingThread extends Thread {

	/**
	 * SLF4J logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UpdateCheckingThread.class);
	
	/**
	 * Start delay.
	 */
	private static final long START_DELAY = 1 * 1000;
	
	/**
	 * Delay used to check for updates.
	 */
	private static final long DELAY = 1 * 60 * 60 * 1000;
	
	/**
	 * Flag indicating that the thread is running. 
	 */
	private boolean isRunning;
	
	
	public final void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public final void run() {
		LOG.debug("Update checking thread started.");
		
		try {
			sleep(START_DELAY);
		} catch (InterruptedException e) {
			LOG.error("Exception occured.", e);
		}
		
		isRunning = true;		
		while (isRunning) {
			IDBClient client = new S3DBClient();
			DBVersionInfo versionInfo = client.getAvailableVersion();
			String actualVersion = ConfigurationManager.getInstance().getConfiguration().getTemplatesDBVersion();											
			
			if (versionInfo != null && !versionInfo.getVersion().equals(actualVersion)) {
				LOG.debug("New version of database found.");
				SMS2Clipboard.TRAY.setInfoIcon();
			}
			
			try {								
				sleep(DELAY);
			} catch (InterruptedException e) {
				LOG.error("Exception occured.", e);
			}
		}
		
		LOG.debug("Update checking thread stopped.");
	}
}
