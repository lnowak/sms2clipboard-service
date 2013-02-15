package pl.softace.passwordless.net.api.server;

import pl.softace.passwordless.net.api.packet.Packet;
import pl.softace.passwordless.net.api.packet.PingRequest;
import pl.softace.passwordless.net.api.packet.PingResponse;
import pl.softace.passwordless.net.api.packet.SMSConfirmation;
import pl.softace.passwordless.net.api.packet.SMSPacket;
import pl.softace.passwordless.net.api.packet.enums.Status;

/**
 * 
 * Simple packet handler used for testing.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SimplePacketHandler implements IPacketHandler {

	/* (non-Javadoc)
	 * @see pl.softace.passwordless.net.api.server.IPacketHandler#handlePacket(pl.softace.passwordless.net.api.packet.Packet)
	 */
	@Override
	public final Packet handlePacket(Packet packet) {
		Packet responsePacket = null;
		
		if (packet instanceof PingRequest) {
			PingRequest request = (PingRequest) packet;			
			PingResponse response = new PingResponse();
			response.setId(request.getId());
			response.setStatus(Status.OK);
			response.setText(request.getText());			
			responsePacket = response;			
		} else if (packet instanceof SMSPacket) {
			SMSPacket request = (SMSPacket) packet;			
			SMSConfirmation response = new SMSConfirmation();
			response.setId(request.getId());
			response.setStatus(Status.OK);			
			responsePacket = response;
		}
		
		return responsePacket;
	}
}
