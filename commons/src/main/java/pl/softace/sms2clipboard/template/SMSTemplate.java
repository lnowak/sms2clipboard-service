package pl.softace.sms2clipboard.template;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Object representing SMS template used to recognize SMS and parse password.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMSTemplate {

	/**
	 * Tag used for password.
	 */
	private static final String PASSWORD_TAG = "\\$\\{PASSWORD\\}";
	
	/**
	 * Source number of the SMS.
	 */
	private String source;
	
	/**
	 * SMS regex.
	 */
	private String smsRegex;
	
	/**
	 * Password regex.
	 */
	private String passwordRegex;
	
	/**
	 * Default constructor.
	 */
	public SMSTemplate() {
		
	}
	
	public final String getSource() {
		return source;
	}

	public final void setSource(String source) {
		this.source = source;
	}

	public final String getSmsRegex() {
		return smsRegex;
	}

	public final void setSmsRegex(String smsRegex) {
		this.smsRegex = smsRegex;
	}

	public final String getPasswordRegex() {
		return passwordRegex;
	}

	public final void setPasswordRegex(String passwordRegex) {
		this.passwordRegex = passwordRegex;
	}

	/**
	 * Checks the patter with text.
	 * 
	 * @param text		text to check
	 * @return			true if matches
	 */
	public final boolean matchText(String text) {
		String tempRegex = smsRegex.replaceAll(PASSWORD_TAG, passwordRegex);
		Pattern pattern = Pattern.compile(tempRegex);
		return pattern.matcher(text).matches();
	}
	
	/**
	 * Gets SMS prefix from SMS text (all text before password).
	 * 
	 * @param text		SMS text
	 * @return			prefix
	 */
	public final String getSMSPrefix(String text) {
		String prefix = null;		
		Matcher passwordMatcher = Pattern.compile(PASSWORD_TAG).matcher(smsRegex);
		if (passwordMatcher.find()) {
			String prefixRegex = smsRegex.substring(0, passwordMatcher.start());			
			Matcher prefixMatcher = Pattern.compile(prefixRegex).matcher(text);					
			if (prefixMatcher.find()) {
				prefix = text.substring(0, prefixMatcher.end());
			}
		}
		
		return prefix;
	}
	
	/**
	 * Gets password from text.
	 * 
	 * @param text		SMS text
	 * @return			password
	 */
	public final String getSMSPassword(String text) {
		String password = null;		
		Matcher passwordMatcher = Pattern.compile(PASSWORD_TAG).matcher(smsRegex);
		if (passwordMatcher.find()) {
			String prefixRegex = smsRegex.substring(0, passwordMatcher.start());
			String suffixRegex = smsRegex.substring(passwordMatcher.end(), smsRegex.length());
			
			Matcher prefixMatcher = Pattern.compile(prefixRegex).matcher(text);
			Matcher suffixMatcher = Pattern.compile(suffixRegex).matcher(text);		
			
			if (prefixMatcher.find() && suffixMatcher.find()) {
				password = text.substring(prefixMatcher.end(), suffixMatcher.start());
			}
		}
		
		return password;
	}
	
	/**
	 * Gets SMS prefix from SMS text (all text before password).
	 * 
	 * @param text		SMS text
	 * @return			prefix
	 */
	public final String getSMSSuffix(String text) {
		String suffix = null;		
		Matcher passwordMatcher = Pattern.compile(PASSWORD_TAG).matcher(smsRegex);
		if (passwordMatcher.find()) {
			String suffixRegex = smsRegex.substring(passwordMatcher.end(), smsRegex.length());			
			Matcher suffixMatcher = Pattern.compile(suffixRegex).matcher(text);					
			if (suffixMatcher.find()) {
				suffix = text.substring(suffixMatcher.start(), text.length());
			}
		}
		
		return suffix;
	}
}
