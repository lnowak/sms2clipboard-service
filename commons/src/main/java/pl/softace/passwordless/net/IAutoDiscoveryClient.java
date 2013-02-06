package pl.softace.passwordless.net;

/**
 * 
 * Interface for auto discovery client.
 * 
 * @author lkawon@gmail.com
 *
 */
public interface IAutoDiscoveryClient {

	/**
	 * Tries to find the server.
	 * 
	 * @return
	 */
	String findServer();
}
