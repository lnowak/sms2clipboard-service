package pl.softace.sms2clipboard.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class NetworkInterfaceUtils {

	public static final InetAddress getInetAddress() throws SocketException {
		InetAddress address = null;
		
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			//System.out.println(intf);
			if (intf.getDisplayName() != null && !intf.getDisplayName().contains("Loopback")  && !intf.getDisplayName().contains("WiFi")) {
			    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
			      InetAddress temp = enumIpAddr.nextElement();
			      Pattern patter = Pattern.compile("\\d+.\\d+.\\d+.\\d+");
			      if (patter.matcher(temp.getHostAddress()).matches()) { 			      
			    	  address = temp;
			    	  break;
			      }
			    }
			}
		}
		
		if (address == null) {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				//System.out.println(intf);
				if (intf.getDisplayName() != null && !intf.getDisplayName().contains("Loopback")) {
				    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
				      InetAddress temp = enumIpAddr.nextElement();
				      Pattern patter = Pattern.compile("\\d+.\\d+.\\d+.\\d+");
				      if (patter.matcher(temp.getHostAddress()).matches()) { 			      
				    	  address = temp;
				    	  break;
				      }
				    }
				}
			}
		}
		
		
		return address;
	}
}
