package pl.softace.passwordless.net;

/**
 * 
 * Object containing IP address and hostname.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ServerInstance {

	private String ip;
	
	private String hostName;
	
	
	/**
	 * Default constructor.
	 */
	public ServerInstance() {
		
	}

	public final String getIp() {
		return ip;
	}

	public final void setIp(String ip) {
		this.ip = ip;
	}

	public final String getHostName() {
		return hostName;
	}

	public final void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ServerInstance [ip=");
		builder.append(ip);
		builder.append(", hostName=");
		builder.append(hostName);
		builder.append("]");
		return builder.toString();
	}
}
