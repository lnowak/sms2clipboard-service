package pl.softace.sms2clipboard.utils;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 
 * Common utility class.
 * 
 * @author lkawon@gmail.com
 *
 */
public class Utilities {

	/**
	 * Hidden constructor.
	 */
	private Utilities() {
		
	}
	
	/**
	 * Checks if the given port is free. 
	 * 
	 * @param port		port to check
	 * @return			true or false
	 */
	public static final boolean checkPort(int port) {
		boolean ok = false;
		try {
			ServerSocket socket = new ServerSocket(port);			
			socket.close();
			ok = true;
		} catch (IOException ex) {
			;
		}	
		
		return ok;
	}
}
