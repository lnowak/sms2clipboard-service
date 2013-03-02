package pl.softace.sms2clipboard.net.api.server;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import pl.softace.sms2clipboard.net.api.client.ApiClient;
import pl.softace.sms2clipboard.net.api.packet.DBRequest;
import pl.softace.sms2clipboard.net.api.packet.DBResponse;
import pl.softace.sms2clipboard.net.api.packet.DBVersionRequest;
import pl.softace.sms2clipboard.net.api.packet.DBVersionResponse;
import pl.softace.sms2clipboard.net.api.packet.Packet;
import pl.softace.sms2clipboard.net.api.packet.PingRequest;
import pl.softace.sms2clipboard.net.api.packet.PingResponse;
import pl.softace.sms2clipboard.net.api.packet.SMSConfirmation;
import pl.softace.sms2clipboard.net.api.packet.SMSPacket;
import pl.softace.sms2clipboard.net.api.packet.enums.Status;
import pl.softace.sms2clipboard.net.api.server.ApiServer;

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
	 * Tries to send DB version request to server.
	 * @throws InterruptedException 
	 */
	@Test(dependsOnMethods = "startServer")
	public final void sendDBVersionRequest() throws InterruptedException {
		// given
		ApiClient client = new ApiClient("127.0.0.1", 8080);
		client.connect();
		Assert.assertTrue(client.isConnected());
		
		// when
		DBVersionRequest request = new DBVersionRequest();
		request.setId(1);
			
		Packet response = client.send(request);
		
		// then
		Assert.assertNotNull(response);
		Assert.assertTrue(response instanceof DBVersionResponse);
		Assert.assertEquals(request.getId(), ((DBVersionResponse) response).getId());
		Assert.assertEquals(Status.OK, ((DBVersionResponse) response).getStatus());
		Assert.assertEquals("version", ((DBVersionResponse) response).getVersion());						
			
		client.disconnect();
	}
	
	/**
	 * Tries to send DB  request to server.
	 * @throws InterruptedException 
	 */
	@Test(dependsOnMethods = "startServer")
	public final void sendDBRequest() throws InterruptedException {
		// given
		ApiClient client = new ApiClient("127.0.0.1", 8080);
		client.connect();
		Assert.assertTrue(client.isConnected());
		
		// when
		DBRequest request = new DBRequest();
		request.setId(1);
			
		Packet response = client.send(request);
		
		// then
		Assert.assertNotNull(response);
		Assert.assertTrue(response instanceof DBResponse);
		Assert.assertEquals(request.getId(), ((DBResponse) response).getId());
		Assert.assertEquals(Status.OK, ((DBResponse) response).getStatus());
		Assert.assertEquals(2, ((DBResponse) response).getTemplates().size());				
			
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
