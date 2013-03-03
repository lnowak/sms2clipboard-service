package pl.softace.sms2clipboard.net.http.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.softace.sms2clipboard.net.http.DBVersionInfo;
import pl.softace.sms2clipboard.net.http.IDBClient;

/**
 * 
 * Used for {@link S3DBClient} testing.
 * 
 * @author lkawon@gmail.com
 *
 */
public class S3DBClientTest {

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
		String version = "2.0.0";
		
		// when
		client.downloadDatabase(version, null);
		
		
	}
}
