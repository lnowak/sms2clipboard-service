package pl.softace.passwordless;

import pl.softace.passwordless.net.api.server.ApiServer;
import pl.softace.passwordless.net.autodiscovery.IAutoDiscoveryServer;
import pl.softace.passwordless.net.autodiscovery.impl.UDPAutoDiscoveryServer;

public class Main {
	
	public static void main(String[] args) {
		IAutoDiscoveryServer server = new UDPAutoDiscoveryServer();
		server.startServer();		
		
		ApiServer apiServer = new ApiServer();
		apiServer.startServer();
	}
}
