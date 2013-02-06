package pl.softace.passwordless.net.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Assert;
import org.testng.annotations.Test;

import pl.softace.passwordless.net.IAutoDiscoveryClient;
import pl.softace.passwordless.net.IAutoDiscoveryServer;

/**
 * 
 * Used for {@link UDPAutoDiscoveryServer} test.
 * 
 * @author lkawon@gmail.com
 *
 */
public class UDPAutoDiscoveryServerTest {

	/**
	 * Tries to find server by multicast packet.
	 * @throws UnknownHostException 
	 */
	@Test
	public final void findServer() throws UnknownHostException {
		// given
		IAutoDiscoveryServer server = new UDPAutoDiscoveryServer();
		server.startServer();
		
		// when
		IAutoDiscoveryClient client = new UDPAutoDiscoveryClient();
		String ip = client.findServer();
		
		server.stopServer();
		
		// then		
		Assert.assertNotNull(ip);
		Assert.assertEquals(ip, InetAddress.getLocalHost().getHostAddress());				
	}
}
