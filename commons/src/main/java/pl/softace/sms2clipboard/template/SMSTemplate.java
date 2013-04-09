package pl.softace.sms2clipboard.template;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.softace.sms2clipboard.net.api.packet.ReflectedPacket;
import pl.softace.sms2clipboard.net.api.packet.annotations.PropertyParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketType;

/**
 * 
 * Object representing SMS template used to recognize SMS and parse password.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMSTemplate extends ReflectedPacket implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -480781114016723085L;

	/**
	 * Tag used for password.
	 */
	private static final String PASSWORD_TAG = "\\$\\{PASSWORD\\}";
	
	/**
	 * Source number of the SMS.
	 */
	@PropertyParameter(parameter = PacketParameter.TEMPLATE_SOURCE)
	private String source;
	
	/**
	 * SMS regex.
	 */
	@PropertyParameter(parameter = PacketParameter.TEMPLATE_SMS_REGEX)
	private String smsRegex;
	
	/**
	 * Password regex.
	 */
	@PropertyParameter(parameter = PacketParameter.TEMPLATE_PASSWORD_REGEX)
	private String passwordRegex;
	
	
	/**
	 * Default constructor.
	 */
	public SMSTemplate() {
		setType(PacketType.SMS_TEMPLATE);
	}
	
	public SMSTemplate(String source, String smsRegex, String passwordRegex) {
		setType(PacketType.SMS_TEMPLATE);
		this.source = source;
		this.smsRegex = smsRegex;
		this.passwordRegex = passwordRegex;
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
		text = normalizeText(text);
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
		text = normalizeText(text);
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
		text = normalizeText(text);
		String password = null;		
		Matcher passwordMatcher = Pattern.compile(PASSWORD_TAG).matcher(smsRegex);
		if (passwordMatcher.find()) {
			String prefixRegex = smsRegex.substring(0, passwordMatcher.start());
			String suffixRegex = smsRegex.substring(passwordMatcher.end(), smsRegex.length());
			
			Matcher prefixMatcher = Pattern.compile(prefixRegex).matcher(text);
			Matcher suffixMatcher = Pattern.compile(suffixRegex).matcher(text);		
			
			if (prefixMatcher.find() && suffixMatcher.find()) {
				int end = suffixMatcher.start();
				if (suffixRegex.length() == 0) {
					end = text.length();
				}
				password = text.substring(prefixMatcher.end(), end);
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
		text = normalizeText(text);
		String suffix = "";		
		Matcher passwordMatcher = Pattern.compile(PASSWORD_TAG).matcher(smsRegex);
		if (passwordMatcher.find()) {
			String suffixRegex = smsRegex.substring(passwordMatcher.end(), smsRegex.length());
			if (suffixRegex.length() > 0) {
				Matcher suffixMatcher = Pattern.compile(suffixRegex).matcher(text);					
				if (suffixMatcher.find()) {
					suffix = text.substring(suffixMatcher.start(), text.length());
				}
			}
		}
		
		return suffix;
	}

	/**
	 * Normalizes whitespace characters.
	 * 
	 * @param text		text to normalize
	 * @return normalized text
	 */
	private final String normalizeText(String text) {
		return text.trim().replaceAll(" +", " ");
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((passwordRegex == null) ? 0 : passwordRegex.hashCode());
		result = prime * result
				+ ((smsRegex == null) ? 0 : smsRegex.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SMSTemplate other = (SMSTemplate) obj;
		if (passwordRegex == null) {
			if (other.passwordRegex != null)
				return false;
		} else if (!passwordRegex.equals(other.passwordRegex))
			return false;
		if (smsRegex == null) {
			if (other.smsRegex != null)
				return false;
		} else if (!smsRegex.equals(other.smsRegex))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SMSTemplate [source=");
		builder.append(source);
		builder.append(", smsRegex=");
		builder.append(smsRegex);
		builder.append(", passwordRegex=");
		builder.append(passwordRegex);
		builder.append("]");
		return builder.toString();
	}
}
