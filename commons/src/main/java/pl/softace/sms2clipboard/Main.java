package pl.softace.sms2clipboard;

import pl.softace.sms2clipboard.net.api.server.ApiServer;
import pl.softace.sms2clipboard.net.api.server.SimplePacketHandler;
import pl.softace.sms2clipboard.net.autodiscovery.IAutoDiscoveryServer;
import pl.softace.sms2clipboard.net.autodiscovery.impl.UDPAutoDiscoveryServer;

public class Main {
	
	public static void main(String[] args) {
		IAutoDiscoveryServer server = new UDPAutoDiscoveryServer();
		server.startServer();		
		
		ApiServer apiServer = new ApiServer();
		apiServer.setPacketHandler(new SimplePacketHandler());
		apiServer.startServer();
	}
}
