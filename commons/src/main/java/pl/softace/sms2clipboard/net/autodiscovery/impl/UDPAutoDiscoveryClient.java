package pl.softace.sms2clipboard.net.autodiscovery.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.net.autodiscovery.Action;
import pl.softace.sms2clipboard.net.autodiscovery.IAutoDiscoveryClient;
import pl.softace.sms2clipboard.net.autodiscovery.ServerInstance;

/**
 * 
 * Implementation of the auto discovery client based on UDP.
 * 
 * @author lkawon@gmail.com
 *
 */
public class UDPAutoDiscoveryClient implements IAutoDiscoveryClient {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UDPAutoDiscoveryClient.class);
	
	/**
	 * Searching timeout.
	 */
	private long timout = 5000;
	
	/**
	 * Listening port.
	 */
	private int port = 1900;
	
	/**
	 * Multicast socket.
	 */
	private MulticastSocket socket;
	
	/**
	 * Multicast group.
	 */
	private String multicastGroup = "239.255.255.250";
		

	/**
	 * Default constructor.
	 */
	public UDPAutoDiscoveryClient() {
		
	}
	
	public UDPAutoDiscoveryClient(long timeout) {
		if (timeout < 1000) {
			this.timout = 1000;
		} else {
			this.timout = timeout;
		}
	}
	
	/* (non-Javadoc)
	 * @see pl.softace.passwordless.net.IAutoDiscoveryClient#findServer()
	 */
	@Override
	public final List<ServerInstance> findServer() {	
		List<ServerInstance> servers = new ArrayList<ServerInstance>();
		
		try {			
			socket = new MulticastSocket(port);
			socket.joinGroup(InetAddress.getByName(multicastGroup));
									
			AutoDiscoveryPacket searchPacket = new AutoDiscoveryPacket();
			searchPacket.setAction(Action.SEARCH);
			byte[] requestBuffer = searchPacket.buildPacket().getBytes();			
			
			DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length, 
					InetAddress.getByName(multicastGroup), port);
			socket.send(requestPacket);
	
			long startTime = System.currentTimeMillis();
			while (true) {
				try {
					byte receivingBuffer[] = new byte[1024];
					DatagramPacket receivedPacket = new DatagramPacket(receivingBuffer, receivingBuffer.length);
					socket.setSoTimeout(1);
					socket.receive(receivedPacket);											
					AutoDiscoveryPacket responsePacket = AutoDiscoveryPacket.parsePacket(
							new String(receivedPacket.getData()));
					
					if (responsePacket != null && responsePacket.getAction().equals(Action.SERVER_INSTANCE)) {
						ServerInstance server = new ServerInstance();
						server.setIp(receivedPacket.getAddress().getHostAddress());
						server.setHostName(responsePacket.getHost());
						server.setPort(responsePacket.getPort());
						if (!servers.contains(server)) {
							servers.add(server);
						}
					}								
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						LOG.error("Exception occured.", e);
					}
				} catch (SocketTimeoutException e) {
					if (System.currentTimeMillis() - startTime > timout) {
						break;
					}
				}
			}
			
			socket.leaveGroup(InetAddress.getByName(multicastGroup));
			socket.close();
		} catch (IOException e) {
			LOG.error("Exception occured.", e);
		}
		
		return servers;
	}
}
