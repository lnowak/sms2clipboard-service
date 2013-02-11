package pl.softace.passwordless;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import pl.softace.passwordless.net.autodiscovery.IAutoDiscoveryServer;
import pl.softace.passwordless.net.autodiscovery.impl.UDPAutoDiscoveryServer;

public class Main {
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException, InvalidKeySpecException {
		IAutoDiscoveryServer server = new UDPAutoDiscoveryServer();
		server.startServer();				
	}
}
