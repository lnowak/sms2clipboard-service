package pl.softace.sms2clipboard.template;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.config.ConfigurationManager;
import pl.softace.sms2clipboard.security.AESCrypter;
import pl.softace.sms2clipboard.security.CryptException;

/**
 * 
 * Class that is able to manage SMS templates.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMSTemplateManager {

	/**
	 * SLF4J logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SMSTemplateManager.class);
	
	/**
	 * Template delimiter.
	 */
	private static final String DELIMITER = "\\|";
	
	/**
	 * Temporary file for database.
	 */
	public static final String DB_TEMP_FILE_NAME = "./config/temp.db";
	
	/**
	 * File containing all templates.
	 */
	private static final String DB_FILENAME = "./config/templates.db";
	
	/**
	 * AES crypter.
	 */
	private AESCrypter crypter;
	
	/**
	 * List of SMS templates.
	 */
	private List<SMSTemplate> templates = new ArrayList<SMSTemplate>();
	
	/**
	 * Singleton instance.
	 */
	private static SMSTemplateManager instance;
	
	
	/**
	 * Default constructor.
	 */
	public SMSTemplateManager() {
		crypter = new AESCrypter();
		loadFromFile();
	}
	
	public final List<SMSTemplate> getTemplates() {
		return templates;
	}

	public final void setTemplates(List<SMSTemplate> templates) {
		this.templates = templates;
	}

	/**
	 * Returns singleton instance.
	 * 
	 * @return	singleton instance
	 */
	public static final SMSTemplateManager getInstance() {
		if (instance == null) {
			instance = new SMSTemplateManager();
		}
		
		return instance;
	}
	
	/**
	 * Adds new SMS template to the collection.
	 * 
	 * @param smsTemplate		SMS template
	 */
	public final synchronized void addSMSTemplate(SMSTemplate smsTemplate) {
		templates.add(smsTemplate);
	}
	
	/**
	 * Finds SMS template.
	 * 
	 * @param text		SMS text
	 * @return			SMS template
	 */
	public final synchronized SMSTemplate findSMSTemplate(String source, String text) {
		SMSTemplate template = null;
		for (SMSTemplate temp : templates) {
			if (temp.getSource().equals(source) && temp.matchText(text)) {				
				template = temp;
				break;
			}
		}
		
		return template;
	}		
	
	/**
	 * Saves all templates to file.
	 */
	public final synchronized void saveToFile() {
		LOG.debug("Start saving templates to file (" + templates.size() + ".");
		
		try {			
			String delimiter = convertDelimiterForSaving(DELIMITER);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
			for (SMSTemplate template : templates) {
				String line = template.getSource() + delimiter + template.getSmsRegex() + delimiter	+ template.getPasswordRegex();				
				writer.write(line);				
				writer.write("\r\n");
			}
			writer.close();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			FileOutputStream fos = new FileOutputStream(new File(DB_FILENAME));
			crypter.encrypt(bais, fos);
			
		} catch (IOException e) {
			LOG.error("Exception during templates saving to file.", e);
		}
		
		LOG.debug("Saving templates to file finished.");
	}
	
	/**
	 * Load templates from decrypted file.
	 */
	public final synchronized void loadFromFile() {
		loadFromFile(true, DB_FILENAME);
	}
	
	/**
	 * Load templates from encrypted file.
	 */
	public final synchronized void loadFromFile(boolean encrypted, String fileName) {		
		LOG.debug("Start reloading templates from fileName: " + fileName + " (" + encrypted + ").");
		
		templates.clear();
		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (encrypted) {
				crypter.decrypt(fis, baos);
			} else {
				copy(fis, baos);
			}
			fis.close();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split(DELIMITER);
				SMSTemplate template = new SMSTemplate();
				template.setSource(tokens[0]);
				template.setSmsRegex(tokens[1]);
				template.setPasswordRegex(tokens[2]);
				templates.add(template);
			}
			reader.close();
			
		} catch (Exception e) {
			LOG.error("Exception during templates reloading.", e);
		}
		
		LOG.debug("Reloading templates finished (" + templates.size() + ").");
	}
	
	/**
	 * Copy input stream to output.
	 * 
	 * @param in			input stream
	 * @param out			output stream
	 */
	public void copy(InputStream in, OutputStream out) {
		byte[] buf = new byte[1024];
		try {
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			out.close();
		}  catch (Exception e) {
			LOG.error("Exception during AES decrypting.", e);			
			throw new CryptException("Exception during AES decrypting.", e);
		}
	}
	
	/**
	 * Replace database file.
	 * 
	 * @param fileName		new database file
	 */
	public synchronized boolean replaceVersion(String version) {
		boolean replaced = false;
		
		File oldDatabase = new File(DB_FILENAME);
		File oldDatabaseBackup = new File(DB_FILENAME + ".bak");
		File newDatbase = new File(DB_TEMP_FILE_NAME);
		
		// backup
		if (oldDatabaseBackup.exists()) {
			oldDatabaseBackup.delete();
		}
		oldDatabase.renameTo(oldDatabaseBackup);
		replaced = newDatbase.renameTo(oldDatabase);
		if (replaced) {
			loadFromFile();
			ConfigurationManager.getInstance().getConfiguration().setTemplatesDBVersion(version);
			ConfigurationManager.getInstance().saveToFile();
		}
		
		return replaced; 
	}
	
	/**
	 * Converts delimiter for saving to file.
	 * 
	 * @param delimiter		delimiter to covert
	 * @return				converted delimiter
	 */
	private String convertDelimiterForSaving(String delimiter) {
		String converterdDelimiter = delimiter;
		if (delimiter.contains("\\")) {
			converterdDelimiter = delimiter.replaceAll("\\\\", "");
		} 
		
		return converterdDelimiter;
	}
}
