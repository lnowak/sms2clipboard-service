package pl.softace.sms2clipboard.net.autodiscovery;

/**
 * 
 * Interface used to start the auto discovery server.
 * 
 * @author lkawon@gmail.com
 *
 */
public interface IAutoDiscoveryServer {

	/**
	 * Returns information if server is running.
	 * 
	 * @return true or false
	 */
	boolean isRunning();
	
	/**
	 * Starts the server.
	 */
	void startServer();
	
	/**
	 * Stops the server.
	 */
	void stopServer();
	
	/**
	 * Sends ping packet.
	 * 
	 * @return true if packet was send
	 */
	boolean sendPingPacket();
	
	/**
	 * Returns true if ping was received.
	 * 
	 * @return true if packet was received
	 */
	boolean wasPingReceived();
}
