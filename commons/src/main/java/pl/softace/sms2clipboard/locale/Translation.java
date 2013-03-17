package pl.softace.sms2clipboard.locale;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.net.api.client.ApiClient;

/**
 * 
 * Utility class for localization.
 * 
 * 
 * @author lkawon@gmail.com
 *
 */
public class Translation {

	/**
	 * SLF4J logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ApiClient.class);

	/**
	 * Default language.
	 */
	private static final String DEFAULT_LANGUAGE = "en"; 
	
	/**
	 * Actual language.
	 */
	private String language;
	
	/**
	 * Properties.
	 */
	private Properties properties;
	
	
	/**
	 * Singleton instance.
	 */
	private static Translation INSTANCE;
	

	/**
	 * Gets instance of the singleton.
	 * 
	 * @return		singleton instance
	 */
	public final static Translation getInstance() {
		return getInstance(null);
	}
	
	/**
	 * Gets instance of the singleton.
	 * 
	 * @return		singleton instance
	 */
	public final static Translation getInstance(String language) {
		if (INSTANCE == null || INSTANCE.language != language) {
			INSTANCE = new Translation();
			if (language != null) {
				INSTANCE.setLanguage(language);
			} else {
				INSTANCE.setSystemLanguage();
			}
		}				
		
		return INSTANCE;
	}
	
	/**
	 * Sets new language.
	 * 
	 * @param language		new language
	 */
	public final synchronized void setLanguage(String language) {
		this.language = language;
		reload();
	}
	
	/**
	 * Sets system language.
	 */
	public final void setSystemLanguage() {
		setLanguage(Locale.getDefault().getLanguage());
	}
	
	/**
	 * Gets property from locale.
	 * 
	 * @param key		key of the property
	 * @return			value of the property
	 */
	public final synchronized String getProperty(Category category) {
		String finalValue = null;
		
		String value = properties.getProperty(category.getKey());		
		try {
			finalValue = new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error("Exception occured.", e);
		} 
		
		return finalValue;
	}
	
	/**
	 * Gets specific language or default (null) 
	 * 
	 * @return		string representing language
	 */
	public final String getLanguage() {
		InputStream is = ClassLoader.getSystemResourceAsStream("locale_" + language + ".properties");
		if (is == null) {			
			return DEFAULT_LANGUAGE;
		}
		
		return language;
	}
	
	/**
	 * Reloads properties if needed.
	 */
	private final void reload() {		
		properties = new Properties();
		try {
			properties.load(getLocaleAsInputStream(language));
		} catch (IOException e) {
			LOG.debug("Exception occurred during locale loading.", e);
		}
	}
	
	/**
	 * Gets locale file as input stream.
	 * 
	 * @param language		language
	 * @return				input stream
	 */
	private final InputStream getLocaleAsInputStream(String language) {
		InputStream is = ClassLoader.getSystemResourceAsStream("locale_" + language + ".properties");
		if (is == null) {
			is = ClassLoader.getSystemResourceAsStream("locale_" + DEFAULT_LANGUAGE + ".properties");
		} 
		
		return is;
	}
}
