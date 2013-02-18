package pl.softace.sms2clipboard.config;

import java.io.Serializable;

/**
 * 
 * Configuration object.
 * 
 * @author lkawon@gmail.com
 *
 */
public class Configuration implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 7526243804834985307L;

	/**
	 * Templates DB version.
	 */
	private String templatesDBVersion;
	
	/**
	 * Password.
	 */
	private String password;
	
	/**
	 * Update delay.
	 */
	private long updateDelay;
	
	
	/**
	 * Default constructor.
	 */
	public Configuration() {
		
	}

	public final String getTemplatesDBVersion() {
		return templatesDBVersion;
	}

	public final void setTemplatesDBVersion(String templatesDBVersion) {
		this.templatesDBVersion = templatesDBVersion;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final long getUpdateDelay() {
		return updateDelay;
	}

	public final void setUpdateDelay(long updateDelay) {
		this.updateDelay = updateDelay;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime
				* result
				+ ((templatesDBVersion == null) ? 0 : templatesDBVersion
						.hashCode());
		result = prime * result + (int) (updateDelay ^ (updateDelay >>> 32));
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
		Configuration other = (Configuration) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (templatesDBVersion == null) {
			if (other.templatesDBVersion != null)
				return false;
		} else if (!templatesDBVersion.equals(other.templatesDBVersion))
			return false;
		if (updateDelay != other.updateDelay)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Configuration [templatesDBVersion=");
		builder.append(templatesDBVersion);
		builder.append(", password=");
		builder.append(password);
		builder.append(", updateDelay=");
		builder.append(updateDelay);
		builder.append("]");
		return builder.toString();
	}
}
