package pl.softace.passwordless.net.autodiscovery;

import java.util.List;

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
	 * @return		list of found servers
	 */
	List<ServerInstance> findServer();
}
