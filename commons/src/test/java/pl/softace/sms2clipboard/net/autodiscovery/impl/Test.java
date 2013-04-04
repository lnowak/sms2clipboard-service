package pl.softace.sms2clipboard.net.autodiscovery.impl;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		UDPAutoDiscoveryServer server = new UDPAutoDiscoveryServer();
		server.startServer();
		
		Thread.sleep(1000);
		
		server.stopServer();
		System.out.println(server.getState());
		server.startServer();
		System.out.println(server.getState());
		
		
		server.sendPingPacket();
	}
}
