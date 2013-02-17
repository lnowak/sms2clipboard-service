package pl.softace.sms2clipboard.net.api.server;

import pl.softace.sms2clipboard.net.api.packet.Packet;
import pl.softace.sms2clipboard.net.api.packet.PingRequest;
import pl.softace.sms2clipboard.net.api.packet.PingResponse;
import pl.softace.sms2clipboard.net.api.packet.SMSConfirmation;
import pl.softace.sms2clipboard.net.api.packet.SMSPacket;
import pl.softace.sms2clipboard.net.api.packet.enums.Status;

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
