package pl.softace.sms2clipboard;

import pl.softace.sms2clipboard.gui.SMS2ClipboardPacketHandler;
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
	 * @param args
	 */
	public static void main(String[] args) {
		IAutoDiscoveryServer server = new UDPAutoDiscoveryServer();
		server.startServer();		
		
		ApiServer apiServer = new ApiServer();
		apiServer.setPacketHandler(new SMS2ClipboardPacketHandler());
		apiServer.startServer();
	}
}
