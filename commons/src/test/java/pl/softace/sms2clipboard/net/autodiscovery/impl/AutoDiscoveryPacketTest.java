package pl.softace.sms2clipboard.net.autodiscovery.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import pl.softace.sms2clipboard.net.autodiscovery.Action;

/**
 * 
 * Used for testing the {@link AutoDiscoveryPacket} class.
 * 
 * @author lkawon@gmail.com
 *
 */
public class AutoDiscoveryPacketTest {

	/**
	 * Creates search packet and parses it.
	 */
	@Test
	public final void checkSearchPacket() {
		// given
		AutoDiscoveryPacket packet = new AutoDiscoveryPacket();
		packet.setAction(Action.SEARCH);
		
		// when
		String packetString = packet.buildPacket();
		
		// then
		Assert.assertEquals(AutoDiscoveryPacket.parsePacket(packetString), packet);
	}
	
	/**
	 * Creates instance packet and parses it.
	 */
	@Test
	public final void checkInstancePacket() {
		// given
		AutoDiscoveryPacket packet = new AutoDiscoveryPacket(Action.SERVER_INSTANCE, "host", 12345);
		
		// when
		String packetString = packet.buildPacket();
		
		// then
		Assert.assertEquals(AutoDiscoveryPacket.parsePacket(packetString), packet);
	}
	
	/**
	 * Parses incorrect packet.
	 */
	@Test
	public final void checkIncorrectPacket() {		
		// when
		String packetString = "UNKNOWN";
		
		// then
		Assert.assertEquals(AutoDiscoveryPacket.parsePacket(packetString), null);
	}
}
