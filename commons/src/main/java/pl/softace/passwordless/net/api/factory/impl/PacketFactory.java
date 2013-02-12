package pl.softace.passwordless.net.api.factory.impl;

import java.nio.ByteBuffer;

import pl.softace.passwordless.net.api.factory.IPacketFactory;
import pl.softace.passwordless.net.api.packet.Packet;
import pl.softace.passwordless.net.api.packet.PacketType;
import pl.softace.passwordless.net.api.packet.PingRequest;
import pl.softace.passwordless.net.api.packet.PingResponse;

/**
 * 
 * Implementation of the packet factory.
 * 
 * @author lkawon@gmail.com
 *
 */
public class PacketFactory implements IPacketFactory {
	
	/* (non-Javadoc)
	 * @see pl.softace.passwordless.net.api.packet.IPacketFactory#createPacket(java.nio.ByteBuffer)
	 */
	@Override
	public final Packet decodeHeader(ByteBuffer buffer) {
		Packet packet = null;
		if (buffer.remaining() >= Packet.HEADER_LENGTH) {			
			PacketType packetType = PacketType.getById(buffer.get());
			switch (packetType) {
			case PING_REQUEST_PACKET:
				packet = new PingRequest();
				break;
			case PING_RESPONSE_PACKET:
				packet = new PingResponse();
				break;
			default:
				break;
			}
			
			if (packet != null) {			
				packet.setId(buffer.getInt());
				packet.setBodyLength(buffer.getInt());
			}
		}
		
		return packet;
	}
}
