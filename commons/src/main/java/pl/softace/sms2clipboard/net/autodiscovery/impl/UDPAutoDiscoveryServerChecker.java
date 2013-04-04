package pl.softace.sms2clipboard.net.autodiscovery.impl;

import org.apache.log4j.Logger;

import pl.softace.sms2clipboard.net.autodiscovery.IAutoDiscoveryServer;

/**
 * 
 * Thread that takes care of the auto discovery server.
 * 
 * @author lukasz.nowak@homersoft.com
 *
 */
public class UDPAutoDiscoveryServerChecker extends Thread {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOG = Logger.getLogger(UDPAutoDiscoveryServerChecker.class);
	
	/**
	 * Delay between sending ping packet and checking response.
	 */
	private long pingDelay = 10 * 1000;
	
	/**
	 * Main thread delay.
	 */
	private long delay = 60 * 1000;
	
	/**
	 * Indicates that the thread is running.
	 */
	private boolean isRunning;
	
	/**
	 * Auto discovery server.
	 */
	private IAutoDiscoveryServer autoDiscoveryServer;
	
	
	/**
	 * Constructor.
	 * 
	 * @param autoDiscoveryServer	auto discovery server
	 */
	public UDPAutoDiscoveryServerChecker(IAutoDiscoveryServer autoDiscoveryServer) {
		this.autoDiscoveryServer = autoDiscoveryServer;
	}
	
	public final IAutoDiscoveryServer getAutoDiscoveryServer() {
		return autoDiscoveryServer;
	}

	public final void setAutoDiscoveryServer(
			IAutoDiscoveryServer autoDiscoveryServer) {
		this.autoDiscoveryServer = autoDiscoveryServer;
	}

	public final long getPingDelay() {
		return pingDelay;
	}

	public final void setPingDelay(long pingDelay) {
		this.pingDelay = pingDelay;
	}

	public final long getDelay() {
		return delay;
	}

	public final void setDelay(long delay) {
		this.delay = delay;
	}

	public final void stopChecker() {
		isRunning = false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public final void run() {
		isRunning = true;
		while (isRunning) {
			if (!autoDiscoveryServer.isRunning()) {
				autoDiscoveryServer.startServer();
			}
			
			boolean checkForResponse = autoDiscoveryServer.sendPingPacket();
			
			try {
				sleep(pingDelay);
			} catch (InterruptedException e) {
				LOG.error("Exception ocurred.", e);
			}
			
			if (checkForResponse) {
				if (!autoDiscoveryServer.wasPingReceived()) {
					autoDiscoveryServer.stopServer();
					autoDiscoveryServer.startServer();
				}
			}
			
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				LOG.error("Exception ocurred.", e);
			}
		}
	}
}
