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
	 * Starts the server.
	 */
	void startServer();
	
	/**
	 * Stops the server.
	 */
	void stopServer();
}
