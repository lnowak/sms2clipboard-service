package pl.softace.sms2clipboard.gui;

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
 * Packet handler used to receive SMS from mobile devices.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMS2ClipboardPacketHandler implements IPacketHandler {

	/* (non-Javadoc)
	 * @see pl.softace.sms2clipboard.net.api.server.IPacketHandler#handlePacket(
	 * pl.softace.sms2clipboard.net.api.packet.Packet)
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
			
			handleSMS(request);
			
			SMSConfirmation response = new SMSConfirmation();
			response.setId(request.getId());
			response.setStatus(Status.OK);			
			responsePacket = response;
		}
		
		return responsePacket;
	}
	
	/**
	 * Handles SMS packet.
	 * 
	 * @param sms		SMS packet
	 */
	private final void handleSMS(SMSPacket sms) {
		SMSTemplate smsTemplate = new SMSTemplate();
		smsTemplate.setSource("3388");
		smsTemplate.setSmsRegex("Operacja nr \\d+ z dn. [\\d-]+ mTransfer z rach.: ...\\d+ na rach.: \\d+...\\d+ kwota: [\\d,]+ PLN haslo: ${PASSWORD} mBank.");
		smsTemplate.setPasswordRegex("\\\\d+");
		
		String smsText = sms.getText();
		
		SMSJFrame.showSMS(smsTemplate, smsText);
	}
}
