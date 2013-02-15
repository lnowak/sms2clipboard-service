package pl.softace.passwordless;

import pl.softace.passwordless.net.api.client.ApiClient;
import pl.softace.passwordless.net.api.packet.Packet;
import pl.softace.passwordless.net.api.packet.PingRequest;
import pl.softace.passwordless.net.api.packet.PingResponse;
import pl.softace.passwordless.net.api.packet.SMSPacket;
import pl.softace.passwordless.net.api.packet.enums.Status;
import pl.softace.passwordless.net.autodiscovery.IAutoDiscoveryClient;
import pl.softace.passwordless.net.autodiscovery.ServerInstance;
import pl.softace.passwordless.net.autodiscovery.impl.UDPAutoDiscoveryClient;

public class Client {

	public static void main(String[] args) {		
		IAutoDiscoveryClient autoDiscoveryClient = new UDPAutoDiscoveryClient(0);
		for (ServerInstance serverInstance : autoDiscoveryClient.findServer()) {
			ApiClient apiClient = new ApiClient(serverInstance.getIp(), 8080);
			apiClient.connect();
			
			PingRequest ping = new PingRequest();
			ping.setText("ping");
			Packet pingResponse = apiClient.send(ping);
			System.out.println(pingResponse);						
			
			if (pingResponse != null && ((PingResponse) pingResponse).getStatus().equals(Status.OK)) {
				SMSPacket smsPacket = new SMSPacket();
				smsPacket.setId(10);
				smsPacket.setText("sms text");
				Packet smsConfirmation = apiClient.send(smsPacket);
				System.out.println(smsConfirmation);
			}
			
			apiClient.disconnect();
		}
	}
}
