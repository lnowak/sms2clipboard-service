package pl.softace.sms2clipboard.net.api.packet;

import java.nio.ByteBuffer;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pl.softace.sms2clipboard.net.api.factory.IPacketFactory;
import pl.softace.sms2clipboard.net.api.factory.impl.PacketFactory;
import pl.softace.sms2clipboard.net.api.packet.Packet;
import pl.softace.sms2clipboard.net.api.packet.PingRequest;
import pl.softace.sms2clipboard.net.api.packet.PingResponse;
import pl.softace.sms2clipboard.net.api.packet.SMSConfirmation;
import pl.softace.sms2clipboard.net.api.packet.SMSPacket;
import pl.softace.sms2clipboard.net.api.packet.enums.Status;

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
		ByteBuffer packetBuffer = packet.encodePacket("password");		
		
		// then
		Packet decodedPacket = packetFactory.decodeHeader(packetBuffer);	
		Assert.assertTrue(decodedPacket.isPacketAvailable(packetBuffer.remaining()));
		decodedPacket.decodeBody(packetBuffer, "password");		
		Assert.assertEquals(decodedPacket, packet);
	}
	
	/**
	 * Check {@link PingResponse} packet.
	 */
	@Test
	public final void checkPingResponsePacket() {
		// given
		PingResponse packet = new PingResponse();
		packet.setId(100);
		packet.setStatus(Status.OK);
		packet.setText("test 1");
		
		// when
		ByteBuffer packetBuffer = packet.encodePacket("password");		
		
		// then
		Packet decodedPacket = packetFactory.decodeHeader(packetBuffer);	
		Assert.assertTrue(decodedPacket.isPacketAvailable(packetBuffer.remaining()));
		decodedPacket.decodeBody(packetBuffer, "password");		
		Assert.assertEquals(decodedPacket, packet);
	}
	
	/**
	 * Check {@link SMSPacket} packet.
	 */
	@Test
	public final void checkSMSPacket() {
		// given
		SMSPacket packet = new SMSPacket();
		packet.setId(100);
		packet.setTimestamp(System.currentTimeMillis());
		packet.setSource("123123123");
		packet.setText("test 1");
		
		// when
		ByteBuffer packetBuffer = packet.encodePacket("password");		
		
		// then
		Packet decodedPacket = packetFactory.decodeHeader(packetBuffer);	
		Assert.assertTrue(decodedPacket.isPacketAvailable(packetBuffer.remaining()));
		decodedPacket.decodeBody(packetBuffer, "password");		
		Assert.assertEquals(decodedPacket, packet);
	}
	
	/**
	 * Check {@link SMSConfirmation} packet.
	 */
	@Test
	public final void checkSMSConfirmation() {
		// given
		SMSConfirmation packet = new SMSConfirmation();
		packet.setId(100);
		packet.setStatus(Status.OK);
		
		// when
		ByteBuffer packetBuffer = packet.encodePacket("password");		
		
		// then
		Packet decodedPacket = packetFactory.decodeHeader(packetBuffer);	
		Assert.assertTrue(decodedPacket.isPacketAvailable(packetBuffer.remaining()));
		decodedPacket.decodeBody(packetBuffer, "password");		
		Assert.assertEquals(decodedPacket, packet);
	}
}
