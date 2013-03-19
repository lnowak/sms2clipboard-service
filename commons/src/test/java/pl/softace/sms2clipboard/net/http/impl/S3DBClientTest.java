package pl.softace.sms2clipboard.net.http.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.softace.sms2clipboard.net.http.DBVersionInfo;
import pl.softace.sms2clipboard.net.http.ErrorCode;
import pl.softace.sms2clipboard.net.http.IDBClient;
import pl.softace.sms2clipboard.net.http.IDownloadListener;
import pl.softace.sms2clipboard.template.SMSTemplateManager;

/**
 * 
 * Used for {@link S3DBClient} testing.
 * 
 * @author lkawon@gmail.com
 *
 */
public class S3DBClientTest implements IDownloadListener {

	/**
	 * Download progress.
	 */
	private int progress;
	
	/**
	 * Download error code.
	 */
	private ErrorCode errorCode;
	
	
	/**
	 * Gets latest version from S3.
	 */
	@Test
	public final void getLatestVersion() {
		// given
		IDBClient client = new S3DBClient();
		
		// when
		DBVersionInfo versionInfo = client.getAvailableVersion();
		
		// then
		Assert.assertNotNull(versionInfo);
		Assert.assertNotNull(versionInfo.getDate());
		Assert.assertNotNull(versionInfo.getVersion());
		Assert.assertNotNull(versionInfo.getDescription());
	}
	
	/**
	 * Downloads database.
	 */
	@Test
	public final void downloadDatabase() {
		// given
		IDBClient client = new S3DBClient();
		String version = "1.0.0";
		
		// when
		client.downloadDatabase(version, this);
		
		// then
		Assert.assertEquals(progress, 100);
		Assert.assertEquals(errorCode, ErrorCode.FINISHED);
	}

	/**
	 * Replace version.
	 */
	@Test(dependsOnMethods = "downloadDatabase")
	public final void replaceVersion() {		
		// when
		boolean replaced = SMSTemplateManager.getInstance().replaceVersion("1.0.0");
		
		// then		
		Assert.assertTrue(replaced);
	}
	
	/* (non-Javadoc)
	 * @see pl.softace.sms2clipboard.net.http.IDownloadListener#updateProgress(int)
	 */
	@Override
	public final boolean updateProgress(int value) {
		progress = value;
		return true;
	}

	/* (non-Javadoc)
	 * @see pl.softace.sms2clipboard.net.http.IDownloadListener#finished(pl.softace.sms2clipboard.net.http.ErrorCode)
	 */
	@Override
	public final void finished(ErrorCode errorCode) {
		this.errorCode = errorCode;		
	}
}
