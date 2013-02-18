package pl.softace.sms2clipboard.template;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.security.AESCrypter;

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
	public final synchronized SMSTemplate findSMSTemplate(String text) {
		SMSTemplate template = null;
		for (SMSTemplate temp : templates) {
			if (temp.matchText(text)) {
				template = temp;
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
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
			for (SMSTemplate template : templates) {
				String line = template.getSource() + ";" + template.getSmsRegex() + ";" + template.getPasswordRegex();				
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
	 * Reloads templates from file.
	 */
	public final synchronized void reload() {		
		LOG.debug("Start reloading templates.");
		
		templates.clear();
		try {
			FileInputStream fis = new FileInputStream(new File(DB_FILENAME));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			crypter.decrypt(fis, baos);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
			
			String line = null;
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split(";");
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
	
	public static void main(String[] args) throws InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException {		
		SMSTemplateManager manager = new SMSTemplateManager();			
				
		SMSTemplate template = new SMSTemplate();
		template.setSource("3388");
		template.setSmsRegex("Operacja nr \\d+ z dn. [\\d-]+ mTransfer z rach.: ...\\d+ na rach.: \\d+...\\d+ kwota: [\\d,]+ PLN haslo: ${PASSWORD} mBank.");
		template.setPasswordRegex("\\\\d+");
		manager.getTemplates().add(template);
		
		template = new SMSTemplate();
		template.setSource("3388");
		template.setSmsRegex("Operacja nr \\d+ z dn. [\\d-]+ Przelew z rach.: ...\\d+ na rach.: \\d+...\\d+ kwota: [\\d,]+ PLN haslo: ${PASSWORD} mBank.");
		template.setPasswordRegex("\\\\d+");
		manager.getTemplates().add(template);
		
		manager.saveToFile();
				
		manager.reload();
		System.out.println(manager.getTemplates());
	}
}
