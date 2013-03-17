package pl.softace.sms2clipboard.net.http.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.locale.Translation;
import pl.softace.sms2clipboard.net.http.DBVersionInfo;
import pl.softace.sms2clipboard.net.http.ErrorCode;
import pl.softace.sms2clipboard.net.http.IDBClient;
import pl.softace.sms2clipboard.net.http.IDownloadListener;
import pl.softace.sms2clipboard.template.SMSTemplateManager;

/**
 * 
 * Implementation of the template database client. 
 * 
 * @author lkawon@gmail.com
 *
 */
public class S3DBClient implements IDBClient {

	/**
	 * SLF4j logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(S3DBClient.class);
	
	/**
	 * Version tag.
	 */
	private static final String VERSION_TAG = "${VERSION}";
	
	/**
	 * Date formatter.
	 */
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * URL of versions description.
	 */
	private static final String VERSION_URL = "https://s3.amazonaws.com/sms2clipboard/templates_db_version_";
	
	/**
	 * URL of datbase files.
	 */
	private static final String DATBASE_URL = "https://s3.amazonaws.com/sms2clipboard/templates_${VERSION}.db";
	
	
	/* (non-Javadoc)
	 * @see pl.softace.sms2clipboard.net.http.IDBClient#getAvailableVersion()
	 */
	@Override
	public final DBVersionInfo getAvailableVersion() {
		DBVersionInfo versionInfo = null;		
		HttpClient httpClient = new DefaultHttpClient();
		try {			
			HttpGet httpGet = new HttpGet(VERSION_URL + Translation.getInstance().getLanguage());
			ResponseHandler<String> responseHandler = new UTF8BasicResponseHandler();
			String responseBody = httpClient.execute(httpGet, responseHandler);
			versionInfo = parseResponse(responseBody);
		} catch (Exception e) {
			LOG.error("Exception while chcecking DB version.", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		
		return versionInfo;
	}
	
	/* (non-Javadoc)
	 * @see pl.softace.sms2clipboard.net.http.IDBClient#downloadDatabase(java.lang.String, pl.softace.sms2clipboard.net.http.IProgressListener)
	 */
	@Override
	public final void downloadDatabase(String version, IDownloadListener listener) {
		HttpClient httpClient = new DefaultHttpClient();
		try {			
			HttpGet httpGet = new HttpGet(DATBASE_URL.replace(VERSION_TAG, version));
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200 && entity != null) {
			    long length = entity.getContentLength();
			    if (length > 0) {
			    	InputStream inputStream = entity.getContent();
			    	FileOutputStream fos = new FileOutputStream(new File(SMSTemplateManager.DB_TEMP_FILE_NAME));
			    	
			    	boolean download = true;
			    	byte[] buf = new byte[1024 * 10];
			    	int sumRead = 0;
			    	int numRead = 0;
					while ((numRead = inputStream.read(buf)) >= 0 && download) {
						fos.write(buf, 0, numRead);
						sumRead += numRead;
						
						if (listener != null) {
							download = listener.updateProgress((int) (sumRead * 100.0 / length));
						}
					}
					fos.close();
					
					if (listener != null) {
						listener.finished(ErrorCode.FINISHED);
					}
			    }
			} else {
				if (listener != null) {
					listener.finished(ErrorCode.NOT_FOUND);
				}
			}
			
		} catch (Exception e) {
			LOG.error("Exception while getting database.", e);
			if (listener != null) {
				listener.finished(ErrorCode.DOWNLOAD_ERROR);
			}
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * Parses string response.
	 * 
	 * @param response		HTTP response
	 * @return				DB version
	 * @throws ParseException 
	 */
	private DBVersionInfo parseResponse(String response) throws ParseException {
		DBVersionInfo versionInfo = null;
		
		boolean inVersion = false;
		StringBuilder description = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(response, "\r\n");		
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.contains("Version") && !inVersion) {	
				versionInfo = new DBVersionInfo();
				String[] parts = token.split(" ");
				if (parts.length == 3) {
					versionInfo.setVersion(parts[1]);
					versionInfo.setDate(DATE_FORMATTER.parse(parts[2].substring(1, parts[2].length() - 1)));					
				}
				inVersion = true;
			} else if (token.contains("Version") && inVersion) {
				break;
			} else {
				description.append(token.trim());
				description.append("\r\n");
			}
		}
		
		if (versionInfo != null) {
			versionInfo.setDescription(description.toString().substring(0, description.toString().length() - 2));
		}
		
		return versionInfo;
	}
}
