package pl.softace.sms2clipboard;

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
	 * Default constructor. Loads configuration and templates database.
	 */
	public SMS2Clipboard() {
		ConfigurationManager.getInstance().loadFromFile();
		SMSTemplateManager.getInstance().loadFromFile();
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
		// TODO: search for free socket
		
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
