package pl.softace.sms2clipboard.net.api.server;

import pl.softace.sms2clipboard.net.api.packet.DBRequest;
import pl.softace.sms2clipboard.net.api.packet.DBResponse;
import pl.softace.sms2clipboard.net.api.packet.DBVersionRequest;
import pl.softace.sms2clipboard.net.api.packet.DBVersionResponse;
import pl.softace.sms2clipboard.net.api.packet.Packet;
import pl.softace.sms2clipboard.net.api.packet.PingRequest;
import pl.softace.sms2clipboard.net.api.packet.PingResponse;
import pl.softace.sms2clipboard.net.api.packet.SMSConfirmation;
import pl.softace.sms2clipboard.net.api.packet.SMSPacket;
import pl.softace.sms2clipboard.net.api.packet.enums.Status;
import pl.softace.sms2clipboard.net.api.server.IPacketHandler;
import pl.softace.sms2clipboard.template.SMSTemplate;

/**
 * 
 * Test packet handler.
 * 
 * @author lkawon@gmail.com
 *
 */
public class TestPacketHandler implements IPacketHandler {

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
		} else if (packet instanceof DBVersionRequest) {
			DBVersionRequest request = (DBVersionRequest) packet;			
			DBVersionResponse response = new DBVersionResponse();
			response.setId(request.getId());
			response.setStatus(Status.OK);		
			response.setVersion("version");
			responsePacket = response;
		} else if (packet instanceof DBRequest) {
			DBRequest request = (DBRequest) packet;
			DBResponse response = new DBResponse();
			response.setId(request.getId());
			response.setStatus(Status.OK);
			response.getTemplates().add(new SMSTemplate("source 1", "sms regex 1", "password regex 1"));
			response.getTemplates().add(new SMSTemplate("source 2", "sms regex 2", "password regex 2"));
			responsePacket = response;
		}
		
		return responsePacket;
	}
}
