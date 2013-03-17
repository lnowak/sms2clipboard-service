package pl.softace.sms2clipboard.net.autodiscovery.impl;

import java.util.StringTokenizer;

import pl.softace.sms2clipboard.net.autodiscovery.Action;

/**
 * 
 * Object represents packet sends by network using UDP protocol.
 * 
 * @author lkawon@gmail.com
 *
 */
public class AutoDiscoveryPacket {

	/**
	 * Action.
	 */
	private Action action;
	
	/**
	 * Host name.
	 */
	private String host;
	
	/**
	 * Port number.
	 */
	private int port;
	
	
	/**
	 * Default constructor.
	 */
	public AutoDiscoveryPacket() {
		
	}
	
	/**
	 * Constructor.
	 * 
	 * @param action		packet action
	 * @param host			host
	 * @param port			port
	 */
	public AutoDiscoveryPacket(Action action, String host, int port) {
		this.action = action;
		this.host = host;
		this.port = port;
	}

	public final Action getAction() {
		return action;
	}

	public final void setAction(Action action) {
		this.action = action;
	}

	public final String getHost() {
		return host;
	}

	public final void setHost(String host) {
		this.host = host;
	}

	public final int getPort() {
		return port;
	}

	public final void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * Build packet.
	 * 
	 * @return	packet as string
	 */
	public final String buildPacket() {
		StringBuilder builder = new StringBuilder();
		builder.append("NOTIFY * HTTP/1.1\r\n");
		builder.append("APPLICATION: SMS2CLIPBOARD\r\n");
		builder.append("ACTION: " + action.getName() + "\r\n");
		if (action.equals(Action.SERVER_INSTANCE)) {
			builder.append("HOST: " + host + "\r\n");
			builder.append("PORT: " + port + "\r\n");
		}
		
		return builder.toString();
	}
	
	/**
	 * Tries to parse packet from string.
	 * 
	 * @param packetString	packet as string
	 * @return				packet or null
	 */
	public final static AutoDiscoveryPacket parsePacket(String packetString) {
		AutoDiscoveryPacket packet = null;		
		if (packetString.contains("APPLICATION: SMS2CLIPBOARD")) {
			packet = new AutoDiscoveryPacket();
			StringTokenizer tokenizer = new StringTokenizer(packetString, "\r\n");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token.contains("ACTION")) {
					packet.setAction(Action.findByName(token.split(": ")[1]));
				} else if (token.contains("HOST")) {
					packet.setHost(token.split(": ")[1]);
				} else if (token.contains("PORT")) {
					packet.setPort(Integer.parseInt(token.split(": ")[1]));
				}
			}
		}
		
		return packet;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
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
		AutoDiscoveryPacket other = (AutoDiscoveryPacket) obj;
		if (action != other.action)
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
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
		builder.append("AutoDiscoveryPacket [action=");
		builder.append(action);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append("]");
		return builder.toString();
	}
}
