package pl.softace.sms2clipboard.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.net.api.packet.Packet;
import pl.softace.sms2clipboard.net.api.packet.PingRequest;
import pl.softace.sms2clipboard.net.api.packet.PingResponse;
import pl.softace.sms2clipboard.net.api.packet.SMSConfirmation;
import pl.softace.sms2clipboard.net.api.packet.SMSPacket;
import pl.softace.sms2clipboard.net.api.packet.enums.Status;
import pl.softace.sms2clipboard.net.api.server.IPacketHandler;
import pl.softace.sms2clipboard.template.SMSTemplate;
import pl.softace.sms2clipboard.template.SMSTemplateManager;

/**
 * 
 * Packet handler used to receive SMS from mobile devices.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMS2ClipboardPacketHandler implements IPacketHandler {

	/**
	 * SLF4J logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SMS2ClipboardPacketHandler.class);
	
	
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
		SMSTemplateManager.getInstance().loadFromFile();
		
		String smsText = sms.getText();
		SMSTemplate smsTemplate = SMSTemplateManager.getInstance().findSMSTemplate(smsText);				
		
		if (smsTemplate != null) {	
			SMSFrame.createAndShow(smsTemplate, smsText);
		} else {
			LOG.debug("Template not found for " + sms + ".");
		}
	}
}
