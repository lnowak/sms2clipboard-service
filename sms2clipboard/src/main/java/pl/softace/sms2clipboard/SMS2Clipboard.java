package pl.softace.sms2clipboard;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.config.ConfigurationManager;
import pl.softace.sms2clipboard.gui.SMS2ClipboardTray;
import pl.softace.sms2clipboard.net.SMS2ClipboardPacketHandler;
import pl.softace.sms2clipboard.net.api.server.ApiServer;
import pl.softace.sms2clipboard.net.autodiscovery.IAutoDiscoveryServer;
import pl.softace.sms2clipboard.net.autodiscovery.impl.UDPAutoDiscoveryServer;
import pl.softace.sms2clipboard.template.SMSTemplateManager;
import pl.softace.sms2clipboard.update.UpdateCheckingThread;
import pl.softace.sms2clipboard.utils.Utilities;

/**
 * 
 * Main class of the application.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMS2Clipboard {

	/**
	 * SLF4J logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SMS2Clipboard.class);
	
	/**
	 * Tray icon.
	 */
	public static SMS2ClipboardTray TRAY;
	
	/**
	 * Auto discovery server.
	 */
	private IAutoDiscoveryServer autoDiscoveryServer;
	
	/**
	 * API server.
	 */
	public static ApiServer API_SERVER;
	
	/**
	 * Update checking thread.
	 */
	private UpdateCheckingThread updateCheckingThread;
	
	
	/**
	 * Default constructor. Loads configuration and templates database.
	 */
	public SMS2Clipboard() {
		ConfigurationManager.getInstance();
		SMSTemplateManager.getInstance();		
	}
	
	/**
	 * Starts the auto discovery server.
	 */
	public final void startAutoDiscovery() {
		autoDiscoveryServer = new UDPAutoDiscoveryServer();
		autoDiscoveryServer.startServer();	
	}
	
	/**
	 * Starts API server.
	 * @throws IOException 
	 */
	public final void startApiServer() throws IOException {				
		API_SERVER = new ApiServer();
		API_SERVER.setPacketHandler(new SMS2ClipboardPacketHandler());
		
		
		int actualPort = ConfigurationManager.getInstance().getConfiguration().getServerPort();
		if (actualPort > 0 && Utilities.checkPort(actualPort)) {
			API_SERVER.setPort(actualPort);
		} else {		
			int port = findFreePort();
			if (port == 0) {
				throw new RuntimeException("No free port found.");
			}
			ConfigurationManager.getInstance().getConfiguration().setServerPort(port);
			ConfigurationManager.getInstance().saveToFile();
			API_SERVER.setPort(port);
		}
		
		API_SERVER.startServer();
	}
	
	/**
	 * Finds free port.
	 * 
	 * @return	free port or 0
	 */
	private final int findFreePort() {
		int port = 0;
		for (int i = 1000; i < 10000; i++) {
			if (Utilities.checkPort(i)) {
				port = i;
				break;
			}
		}
		
		return port;
	}
	
	/**
	 * Starts checking thread.
	 */
	public final void startUpdateCheckingThread() {
		updateCheckingThread = new UpdateCheckingThread();
		updateCheckingThread.start();
	}
	
	/**
	 * Shows the tray icon.
	 */
	public final void showTrayIcon() {
		TRAY = new SMS2ClipboardTray();
		TRAY.initalize();
	}
	
	/**
	 * Sets the UI look and feel.
	 */
	public static final void setUILookAndFeel() {
		// set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Main method of the class.
	 * 
	 * @param args		arguments
	 */
	public static void main(String[] args) {
		LOG.info("Encoding: " + System.getProperty("file.encoding") + ".");
		setUILookAndFeel();
		
		try {
			// main object
			final SMS2Clipboard sms2Clipboard = new SMS2Clipboard();		
			sms2Clipboard.startAutoDiscovery();
			sms2Clipboard.startApiServer();	
			sms2Clipboard.showTrayIcon();
			sms2Clipboard.startUpdateCheckingThread();
			
			
			// shutdown handler
			Runtime.getRuntime().addShutdownHook(new Thread(){
				
	            /* (non-Javadoc)
	             * @see java.lang.Thread#run()
	             */
	            @Override
	            public final void run(){
	            	LOG.debug("Shutdown application.");
	            	
	                sms2Clipboard.autoDiscoveryServer.stopServer();
	                SMS2Clipboard.API_SERVER.stopServer();
	                sms2Clipboard.updateCheckingThread.setRunning(false);
	                
	                LOG.debug("Shutdown finished.");
	                
	                //System.exit(0);
	            }
	        });
			
		} catch (IOException e) {
			LOG.error("Exception while starting application.", e);
		}				
	}
}
