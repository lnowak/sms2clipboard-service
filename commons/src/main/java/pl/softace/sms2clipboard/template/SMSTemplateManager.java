package pl.softace.sms2clipboard.template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class that is able to manage SMS templates.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMSTemplateManager {

	/**
	 * File containing all templates.
	 */
	private static final String DB_FILENAME = "templates.db";
	
	/**
	 * List of SMS templates.
	 */
	private List<SMSTemplate> templates = new ArrayList<SMSTemplate>();
	
	/**
	 * Singleton instance.
	 */
	private static SMSTemplateManager INSTANCE;
	
	
	/**
	 * Default constructor.
	 */
	public SMSTemplateManager() {
		
	}
	
	/**
	 * Returns singleton instance.
	 * 
	 * @return	singleton instance
	 */
	public static final SMSTemplateManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SMSTemplateManager();
		}
		
		return INSTANCE;
	}
	
	/**
	 * Reloads templates from file.
	 */
	public final void reload() {		
		templates.clear();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(new File(DB_FILENAME)));
			String line;
			while ((line = bf.readLine()) != null) {
				String[] tokens = line.split(";");
				SMSTemplate templete = new SMSTemplate();
				templete.setSource(tokens[0]);
				templete.setSmsRegex(tokens[1]);
				templete.setPasswordRegex(tokens[2]);
				templates.add(templete);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds SMS template.
	 * 
	 * @param text		SMS text
	 * @return			SMS template
	 */
	public final SMSTemplate findSMSTemplate(String text) {
		SMSTemplate template = null;
		for (SMSTemplate temp : templates) {
			if (temp.matchText(text)) {
				template = temp;
			}
		}
		
		return template;
	}
}
