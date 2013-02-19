package pl.softace.sms2clipboard;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.gui.SMS2ClipboardPacketHandler;
import pl.softace.sms2clipboard.gui.SMS2ClipboardTray;
import pl.softace.sms2clipboard.net.api.server.ApiServer;
import pl.softace.sms2clipboard.net.autodiscovery.IAutoDiscoveryServer;
import pl.softace.sms2clipboard.net.autodiscovery.impl.UDPAutoDiscoveryServer;

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
	private SMS2ClipboardTray tray;
	
	/**
	 * Auto discovery server.
	 */
	private IAutoDiscoveryServer autoDiscoveryServer;
	
	/**
	 * API server.
	 */
	private ApiServer apiServer;
	
	
	/**
	 * Default constructor.
	 */
	public SMS2Clipboard() {
		
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
	 */
	public final void startApiServer() {
		apiServer = new ApiServer();
		apiServer.setPacketHandler(new SMS2ClipboardPacketHandler());
		apiServer.startServer();
	}
	
	/**
	 * Shows the tray icon.
	 */
	public final void showTrayIcon() {
		tray = new SMS2ClipboardTray();
		tray.initalize();
	}
	
	
	/**
	 * Main method of the class.
	 * 
	 * @param args		arguments
	 */
	public static void main(String[] args) {
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
		
		// main object
		final SMS2Clipboard sms2Clipboard = new SMS2Clipboard();
		sms2Clipboard.startAutoDiscovery();
		sms2Clipboard.startApiServer();	
		sms2Clipboard.showTrayIcon();
		
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
                
                LOG.debug("Shutdown finished.");
            }
        });
	}
}