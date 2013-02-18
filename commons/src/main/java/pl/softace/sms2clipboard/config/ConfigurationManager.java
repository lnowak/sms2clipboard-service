package pl.softace.sms2clipboard.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.security.AESCrypter;

/**
 * 
 * Class that is used to manage confiuration.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ConfigurationManager {

	/**
	 * SLF4J logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationManager.class);
	
	/**
	 * File containing all templates.
	 */
	private static final String CONFIG_FILENAME = "./config/config.data";
	
	/**
	 * Singleton instance.
	 */
	private static ConfigurationManager instance;
	
	/**
	 * AES crypter.
	 */
	private AESCrypter crypter;
	
	/**
	 * Configuration object.
	 */
	private Configuration configuration;
	
	
	/**
	 * Default constructor.
	 */
	public ConfigurationManager() {
		crypter = new AESCrypter();	
		loadFromFile();
	}

	/**
	 * Returns singleton instance.
	 * 
	 * @return	singleton instance
	 */
	public static final ConfigurationManager getInstance() {
		if (instance == null) {
			instance = new ConfigurationManager();
		}
		
		return instance;
	}
	
	public final Configuration getConfiguration() {
		return configuration;
	}

	public final void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Saves all templates to file.
	 */
	public final synchronized void saveToFile() {
		LOG.debug("Saving " + configuration + " to file.");
		
		try {			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(configuration);
			oos.close();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			FileOutputStream fos = new FileOutputStream(new File(CONFIG_FILENAME));
			crypter.encrypt(bais, fos);
			
		} catch (IOException e) {
			LOG.error("Exception during configuration saving.", e);
		}
		
		LOG.debug("Saving " + configuration + " to file finished.");
	}
	
	/**
	 * Reloads templates from file.
	 */
	public final synchronized void loadFromFile() {		
		LOG.debug("Loading configuration from file.");
		
		try {
			FileInputStream fis = new FileInputStream(new File(CONFIG_FILENAME));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			crypter.decrypt(fis, baos);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			configuration = (Configuration) ois.readObject();
			ois.close();
			
		} catch (Exception e) {
			LOG.error("Exception during configuration loading.", e);
		}
		
		LOG.debug(configuration + " loaded from file.");
	}
}
