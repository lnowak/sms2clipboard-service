package pl.softace.sms2clipboard.net.api.server;

import pl.softace.sms2clipboard.net.api.packet.Packet;

/**
 * 
 * Packet handler.
 * 
 * @author lkawon@gmail.com
 *
 */
public interface IPacketHandler {

	/**
	 * Handle incoming packet and return response packet.
	 * 
	 * @param packet	receive packet
	 * @return			returned packet
	 */
	Packet handlePacket(Packet packet);
}
