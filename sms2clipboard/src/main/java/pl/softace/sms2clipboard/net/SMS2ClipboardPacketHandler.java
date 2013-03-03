package pl.softace.sms2clipboard.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.config.Configuration;
import pl.softace.sms2clipboard.config.ConfigurationManager;
import pl.softace.sms2clipboard.gui.SMSFrame;
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
		Packet response = null;		
		if (packet instanceof PingRequest) {														
			response = handlePing((PingRequest) packet);			
		} else if (packet instanceof SMSPacket) {
			response = handleSMS((SMSPacket) packet);			
		} else if (packet instanceof DBVersionRequest) {
			response = handleDBVersion((DBVersionRequest) packet);
		} else if (packet instanceof DBResponse) {
			response = handleDB((DBRequest) packet);
		}
		
		return response;
	}
	
	/**
	 * handles ping request packet.
	 * 
	 * @param request	ping request
	 * @return			response
	 */
	private final PingResponse handlePing(PingRequest request) {			
		PingResponse response = new PingResponse();
		response.setId(request.getId());
		response.setStatus(Status.OK);
		response.setText(request.getText());
		
		return response;
	}
	
	/**
	 * Handles SMS packet.
	 * 
	 * @param sms		SMS packet
	 * @return			response
	 */
	private final SMSConfirmation handleSMS(SMSPacket sms) {
		SMSConfirmation smsConfirmation = new SMSConfirmation();
		smsConfirmation.setId(sms.getId());
		smsConfirmation.setStatus(Status.UNKNOWN_ERROR);
		
		String smsText = sms.getText();	
		SMSTemplate smsTemplate = SMSTemplateManager.getInstance().findSMSTemplate(smsText);				
		
		if (smsTemplate != null) {	
			SMSFrame.createAndShow(smsTemplate, smsText);
			smsConfirmation.setStatus(Status.OK);
		} else {
			LOG.debug("Template not found for " + sms + ".");
		}
		
		return smsConfirmation;
	}
	
	/**
	 * handle DB version packet.
	 * 
	 * @param request		packet request
	 * @return				response
	 */
	private final DBVersionResponse handleDBVersion(DBVersionRequest request) {									
		DBVersionResponse response = new DBVersionResponse();
		response.setId(request.getId());
		response.setStatus(Status.OK);		
		
		Configuration configuration = ConfigurationManager.getInstance().getConfiguration();
		if (configuration != null && configuration.getTemplatesDBVersion() != null) {
			response.setVersion(configuration.getTemplatesDBVersion());
		} else {
			response.setVersion("unknown");
		}
		
		return response;
	}
	
	/**
	 * Handles DB request.
	 * 
	 * @param request		packet request
	 * @return				response
	 */
	private final DBResponse handleDB(DBRequest request) {
		DBResponse response = new DBResponse();
		response.setId(request.getId());
		response.setStatus(Status.OK);
		
		for (SMSTemplate template : SMSTemplateManager.getInstance().getTemplates()) {
			response.getTemplates().add(template);
		}
		
		return response;
	}
}
