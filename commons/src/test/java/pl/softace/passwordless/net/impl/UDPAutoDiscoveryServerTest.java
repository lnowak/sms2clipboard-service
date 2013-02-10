package pl.softace.passwordless.net.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.Test;

import pl.softace.passwordless.net.IAutoDiscoveryClient;
import pl.softace.passwordless.net.IAutoDiscoveryServer;
import pl.softace.passwordless.net.ServerInstance;

/**
 * 
 * Used for {@link UDPAutoDiscoveryServer} test.
 * 
 * @author lkawon@gmail.com
 *
 */
public class UDPAutoDiscoveryServerTest {

	/**
	 * Tries to find servers by multicast packet using default timeout.
	 * @throws UnknownHostException 
	 */
	@Test
	public final void findServerWithDefaultTimeout() throws UnknownHostException {
		// given
		IAutoDiscoveryServer server = new UDPAutoDiscoveryServer();
		server.startServer();
		
		// when
		IAutoDiscoveryClient client = new UDPAutoDiscoveryClient();
		List<ServerInstance> servers = client.findServer();
		
		server.stopServer();
		
		// then		
		Assert.assertNotNull(servers);
		Assert.assertEquals(1, servers.size());
		Assert.assertEquals(InetAddress.getLocalHost().getHostAddress(), servers.get(0).getIp());
		Assert.assertEquals(InetAddress.getLocalHost().getHostName(), servers.get(0).getHostName());
	}
	
	/**
	 * Tries to find servers by multicast packet using custom timeout.
	 * @throws UnknownHostException 
	 */
	@Test
	public final void findServerWithCustomTimeout() throws UnknownHostException {
		// given
		IAutoDiscoveryServer server = new UDPAutoDiscoveryServer();
		server.startServer();
		
		// when
		IAutoDiscoveryClient client = new UDPAutoDiscoveryClient(1000);
		List<ServerInstance> servers = client.findServer();
		
		server.stopServer();
		
		// then		
		Assert.assertNotNull(servers);
		Assert.assertEquals(1, servers.size());
		Assert.assertEquals(InetAddress.getLocalHost().getHostAddress(), servers.get(0).getIp());
		Assert.assertEquals(InetAddress.getLocalHost().getHostName(), servers.get(0).getHostName());
	}
}
