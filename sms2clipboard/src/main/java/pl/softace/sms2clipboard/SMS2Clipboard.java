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
	private ApiServer apiServer;
	
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
		// TODO: search for free socket
		
		apiServer = new ApiServer();
		apiServer.setPacketHandler(new SMS2ClipboardPacketHandler());
		apiServer.startServer();
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
	                sms2Clipboard.apiServer.stopServer();
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
