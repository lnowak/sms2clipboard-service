package pl.softace.sms2clipboard.net.autodiscovery.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.config.ConfigurationManager;
import pl.softace.sms2clipboard.net.autodiscovery.Action;
import pl.softace.sms2clipboard.net.autodiscovery.IAutoDiscoveryServer;

/**
 * 
 * {@link IAutoDiscoveryServer} implementation based on UDP multicasts.
 * 
 * @author lkawon@gmail.com
 *
 */
public class UDPAutoDiscoveryServer extends Thread implements IAutoDiscoveryServer {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(UDPAutoDiscoveryServer.class);
	
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
	 * Flag indicating that the thread is running.
	 */
	private boolean isRunning; 
	
	
	/**
	 * Default constructor.
	 */
	public UDPAutoDiscoveryServer() {
		
	}
	
	public final int getPort() {
		return port;
	}

	public final void setPort(int port) {
		this.port = port;
	}

	public final String getMulticastGroup() {
		return multicastGroup;
	}

	public final void setMulticastGroup(String multicastGroup) {
		this.multicastGroup = multicastGroup;
	}

	/* (non-Javadoc)
	 * @see pl.softace.passwordless.net.IAutoDiscoveryServer#startServer()
	 */
	@Override
	public final void startServer() {
		try {						
			socket = new MulticastSocket(port);			
			socket.joinGroup(InetAddress.getByName(multicastGroup));
		} catch (IOException e) {
			LOG.error("Exception occured.", e);
		}
		
		isRunning = true;
		start();		
	}

	/* (non-Javadoc)
	 * @see pl.softace.passwordless.net.IAutoDiscoveryServer#stopServer()
	 */
	@Override
	public final void stopServer() {
		try {
			socket.leaveGroup(InetAddress.getByName(multicastGroup));
			socket.close();
		} catch (IOException e) {
			LOG.error("Exception occured.", e);
		}
		
		
		try {
			join();
		} catch (InterruptedException e) {
			LOG.error("Exception occured.", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public final void run() {
		LOG.debug("UDP Auto Discovery server started.");
		
		try {					
			while (isRunning) {
				// listening for datagram
				byte buffer[] = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				
				AutoDiscoveryPacket receivedPacket = AutoDiscoveryPacket.parsePacket(new String(packet.getData()));			
				if (receivedPacket != null && receivedPacket.getAction().equals(Action.SEARCH)) {
					LOG.debug("Received search packet from " + packet.getAddress() + ".");
										
					AutoDiscoveryPacket responsePacket = new AutoDiscoveryPacket();
					responsePacket.setAction(Action.SERVER_INSTANCE);
					responsePacket.setHost(InetAddress.getLocalHost().getHostName());
					responsePacket.setPort(ConfigurationManager.getInstance().getConfiguration().getServerPort());
					
					byte[] responseBuffer = responsePacket.buildPacket().getBytes();
					DatagramPacket responseDatagram = new DatagramPacket(responseBuffer, 
							responseBuffer.length, InetAddress.getByName(multicastGroup), port);
					socket.send(responseDatagram);
					
					LOG.debug("Response packet sent.");
				}			
								
				sleep(1);				
			}									
		} catch (IOException e) {
			LOG.error("Exception occured.", e);
		} catch (InterruptedException e) {
			LOG.error("Exception occured.", e);
		}
		
		LOG.debug("UDP Auto Discovery server stopped.");
	}
}
