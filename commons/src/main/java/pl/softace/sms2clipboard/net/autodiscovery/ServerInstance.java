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
