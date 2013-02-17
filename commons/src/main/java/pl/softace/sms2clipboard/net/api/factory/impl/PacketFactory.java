package pl.softace.sms2clipboard.net.api.factory.impl;

import java.nio.ByteBuffer;

import pl.softace.sms2clipboard.net.api.factory.IPacketFactory;
import pl.softace.sms2clipboard.net.api.packet.Packet;
import pl.softace.sms2clipboard.net.api.packet.PingRequest;
import pl.softace.sms2clipboard.net.api.packet.PingResponse;
import pl.softace.sms2clipboard.net.api.packet.SMSConfirmation;
import pl.softace.sms2clipboard.net.api.packet.SMSPacket;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketType;

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
			case SMS_PACKET:
				packet = new SMSPacket();
				break;
			case SMS_CONFIRMATION:
				packet = new SMSConfirmation();
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
