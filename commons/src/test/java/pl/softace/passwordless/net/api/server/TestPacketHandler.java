package pl.softace.passwordless.net.api.server;

import pl.softace.passwordless.net.api.packet.Packet;
import pl.softace.passwordless.net.api.packet.PingRequest;
import pl.softace.passwordless.net.api.packet.PingResponse;

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
			response.setStatus(0);
			response.setText(request.getText());
			
			responsePacket = response;
		}
		
		return responsePacket;
	}

}
