package pl.softace.passwordless.net.api.packet;

import java.nio.ByteBuffer;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pl.softace.passwordless.net.api.factory.IPacketFactory;
import pl.softace.passwordless.net.api.factory.impl.PacketFactory;

/**
 * 
 * Used to test packets.
 * 
 * @author lkawon@gmail.com
 *
 */
public class PacketTest {

	/**
	 * Packet factory.
	 */
	private IPacketFactory packetFactory;
	
	
	/**
	 * Sets up the test.
	 */
	@BeforeClass
	public final void setUp() {
		packetFactory = new PacketFactory();
	}
	
	/**
	 * Check {@link PingRequest} packet.
	 */
	@Test
	public final void checkPingRequestPacket() {
		// given
		PingRequest packet = new PingRequest();
		packet.setId(100);
		packet.setText("To jest testowy tekst.");
		
		// when
		ByteBuffer packetBuffer = packet.encodePacket();		
		
		// then
		Packet decodedPacket = packetFactory.decodeHeader(packetBuffer);	
		Assert.assertTrue(decodedPacket.isPacketAvailable(packetBuffer.remaining()));
		decodedPacket.decodeBody(packetBuffer);		
		Assert.assertEquals(decodedPacket, packet);
	}
}
