package pl.softace.passwordless.net.api.server;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import pl.softace.passwordless.net.api.client.ApiClient;
import pl.softace.passwordless.net.api.packet.Packet;
import pl.softace.passwordless.net.api.packet.PingRequest;
import pl.softace.passwordless.net.api.packet.PingResponse;
import pl.softace.passwordless.net.api.packet.SMSConfirmation;
import pl.softace.passwordless.net.api.packet.SMSPacket;
import pl.softace.passwordless.net.api.packet.enums.Status;

/**
 * 
 * Used for {@link ApiServer} test.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ApiServerTest {

	/**
	 * API server.
	 */
	private ApiServer apiServer;
	
	
	/**
	 * Starts the server.
	 */
	@Test
	public final void startServer() {
		// given
		apiServer = new ApiServer();
		apiServer.setPacketHandler(new TestPacketHandler());
		
		// when
		apiServer.startServer();
	}		
	
	/**
	 * Tries to send ping request message to server.
	 */
	@Test(dependsOnMethods = "startServer")
	public final void sendCorrectPingRequest() {
		// given
		ApiClient client = new ApiClient("127.0.0.1", 8080);
		client.connect();
		Assert.assertTrue(client.isConnected());
		
		// when		
		PingRequest request = new PingRequest();
		request.setId(1);
		request.setText("text");
		
		Packet response = client.send(request);
		
		// then
		Assert.assertNotNull(response);
		Assert.assertTrue(response instanceof PingResponse);
		Assert.assertEquals(request.getId(), ((PingResponse) response).getId());
		Assert.assertEquals(Status.OK, ((PingResponse) response).getStatus());
		Assert.assertEquals(request.getText(), ((PingResponse) response).getText());
		client.disconnect();
	}
	
	/**
	 * Tries to send ping request message to server.
	 * @throws InterruptedException 
	 */
	@Test(dependsOnMethods = "startServer")
	public final void sendCorrectPingRequests() throws InterruptedException {
		// given
		ApiClient client = new ApiClient("127.0.0.1", 8080);
		client.connect();
		Assert.assertTrue(client.isConnected());
		
		// when / then
		for (int i = 0; i < 10; i++) {
			PingRequest request = new PingRequest();
			request.setId(i);
			request.setText("test " + i);
			
			Packet response = client.send(request);
			Assert.assertNotNull(response);
			Assert.assertTrue(response instanceof PingResponse);
			Assert.assertEquals(request.getId(), ((PingResponse) response).getId());
			Assert.assertEquals(Status.OK, ((PingResponse) response).getStatus());
			Assert.assertEquals(request.getText(), ((PingResponse) response).getText());		
		}				
			
		client.disconnect();
	}
	
	/**
	 * Tries to send ping request message to server using incorrect AES password.
	 */
	@Test
	public final void sendCorrectMessageWithIncorrectAesPassword() {
		// given
		ApiClient client = new ApiClient("127.0.0.1", 8080);
		client.setAesPassword("password");
		client.connect();
		Assert.assertTrue(client.isConnected());
		
		// when		
		PingRequest request = new PingRequest();
		request.setId(1);
		request.setText("text");
		
		Packet response = client.send(request);
		
		// then
		Assert.assertNull(response);
		client.disconnect();
	}
	
	/**
	 * Tries to send ping request message to server using incorrect AES password.
	 */
	@Test(dependsOnMethods = "startServer")
	public final void sendSMSPacketAfterPingRequest() {
		// given
		ApiClient client = new ApiClient("127.0.0.1", 8080);
		client.connect();
		Assert.assertTrue(client.isConnected());
		
		PingRequest pingRequest = new PingRequest();
		pingRequest.setId(1);
		pingRequest.setText("text");
		
		Packet response = client.send(pingRequest);
		
		Assert.assertNotNull(response);
		Assert.assertTrue(response instanceof PingResponse);
		Assert.assertEquals(pingRequest.getId(), ((PingResponse) response).getId());
		Assert.assertEquals(Status.OK, ((PingResponse) response).getStatus());
		Assert.assertEquals(pingRequest.getText(), ((PingResponse) response).getText());
		
		SMSPacket smsPacket = new SMSPacket();
		smsPacket.setId(1);
		smsPacket.setTimestamp(System.currentTimeMillis());
		smsPacket.setSource("123123123");
		smsPacket.setText("text");
		
		response = client.send(smsPacket);
		
		// then
		Assert.assertNotNull(smsPacket);
		Assert.assertTrue(response instanceof SMSConfirmation);
		Assert.assertEquals(smsPacket.getId(), ((SMSConfirmation) response).getId());
		Assert.assertEquals(Status.OK, ((SMSConfirmation) response).getStatus());
		client.disconnect();
	}
	
	/**
	 * Stops the server.
	 */
	@AfterClass
	public final void stopServer() {
		// when
		apiServer.stopServer();
	}
}
