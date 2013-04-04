package pl.softace.sms2clipboard.net.autodiscovery;

/**
 * 
 * Object containing IP address and hostname.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ServerInstance {

	/**
	 * IP address.
	 */
	private String ip;
	
	/**
	 * Name of the host.
	 */
	private String hostName;
	
	/**
	 * Port of the server.
	 */
	private int port;
	
	
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

	public final int getPort() {
		return port;
	}

	public final void setPort(int port) {
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerInstance other = (ServerInstance) obj;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (port != other.port)
			return false;
		return true;
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
		builder.append(", port=");
		builder.append(port);
		builder.append("]");
		return builder.toString();
	}
}
