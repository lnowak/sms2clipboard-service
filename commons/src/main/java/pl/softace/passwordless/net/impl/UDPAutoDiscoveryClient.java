package pl.softace.passwordless.net.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;

import pl.softace.passwordless.net.Command;
import pl.softace.passwordless.net.IAutoDiscoveryClient;

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
	private static final Logger LOG = Logger.getLogger(UDPAutoDiscoveryClient.class);
	
	/**
	 * Searching timeout.
	 */
	private static final long TIMEOUT = 10000;
	
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
	private String multicastGroup = "225.1.1.1";
	
	
	/* (non-Javadoc)
	 * @see pl.softace.passwordless.net.IAutoDiscoveryClient#findServer()
	 */
	@Override
	public final String findServer() {	
		String ip = null;
		
		try {
			socket = new MulticastSocket(port);
			socket.joinGroup(InetAddress.getByName(multicastGroup));
											
			byte[] requestBuffer = Command.SEARCH_COMMAND.getData().getBytes();
			DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length, 
					InetAddress.getByName(multicastGroup), port);
			socket.send(requestPacket);
	
			long startTime = System.currentTimeMillis();
			while (true) {
				try {
					byte receivingBuffer[] = new byte[1024];
					DatagramPacket receivedPacket = new DatagramPacket(receivingBuffer, receivingBuffer.length);
					socket.setSoTimeout(5);
					socket.receive(receivedPacket);
					
					
					if (new String(receivedPacket.getData()).contains(Command.RESPONSE_COMMAND.getData())) {
						ip = receivedPacket.getAddress().getHostAddress();
						break;
					}								
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						LOG.error("Exception occured.", e);
					}
				} catch (SocketTimeoutException e) {
					if (System.currentTimeMillis() - startTime > TIMEOUT) {
						break;
					}
				}
			}
			
			socket.leaveGroup(InetAddress.getByName(multicastGroup));
			socket.close();
		} catch (IOException e) {
			LOG.error("Exception occured.", e);
		}
		
		return ip;
	}
}
