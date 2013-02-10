import pl.softace.passwordless.net.IAutoDiscoveryServer;
import pl.softace.passwordless.net.impl.UDPAutoDiscoveryServer;

public class Main {
	public static void main(String[] args) {
		IAutoDiscoveryServer server = new UDPAutoDiscoveryServer();
		server.startServer();
	}
}
