package pl.softace.sms2clipboard;

import pl.softace.sms2clipboard.net.api.client.ApiClient;
import pl.softace.sms2clipboard.net.api.packet.Packet;
import pl.softace.sms2clipboard.net.api.packet.PingRequest;
import pl.softace.sms2clipboard.net.api.packet.PingResponse;
import pl.softace.sms2clipboard.net.api.packet.SMSPacket;
import pl.softace.sms2clipboard.net.api.packet.enums.Status;
import pl.softace.sms2clipboard.net.autodiscovery.IAutoDiscoveryClient;
import pl.softace.sms2clipboard.net.autodiscovery.ServerInstance;
import pl.softace.sms2clipboard.net.autodiscovery.impl.UDPAutoDiscoveryClient;

public class TestClient {

	public static void main(String[] args) {	
		//ApiClient apiClient = new ApiClient("192.168.55.2", 8080);
		//apiClient.connect();
		
		IAutoDiscoveryClient autoDiscoveryClient = new UDPAutoDiscoveryClient(0);
		for (ServerInstance serverInstance : autoDiscoveryClient.findServer()) {
			System.out.println(serverInstance);
			ApiClient apiClient = new ApiClient(serverInstance.getIp(), 8080);
			apiClient.connect();
			
			PingRequest ping = new PingRequest();
			ping.setText("ping");
			Packet pingResponse = apiClient.send(ping);
			System.out.println(pingResponse);						
			
			if (pingResponse != null && ((PingResponse) pingResponse).getStatus().equals(Status.OK)) {
				SMSPacket smsPacket = new SMSPacket();
				smsPacket.setId(10);
				smsPacket.setTimestamp(System.currentTimeMillis());
				smsPacket.setSource("Sms2Clipboard");
				smsPacket.setText("Greetings from sms2clipboard creators! Sample password: 123456");
				Packet smsConfirmation = apiClient.send(smsPacket);
				System.out.println(smsConfirmation);
			}
			
			apiClient.disconnect();
		}
	}
}
