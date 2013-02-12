package pl.softace.passwordless.net.api.factory;

import java.nio.ByteBuffer;

import pl.softace.passwordless.net.api.packet.Packet;

/**
 * 
 * Creates packet instance.
 * 
 * @author lkawon@gmail.com
 *
 */
public interface IPacketFactory {

	/**
	 * Creates packet based on the buffer.
	 * 
	 * @param buffer		buffer		
	 * @return				created packet
	 */
	Packet decodeHeader(ByteBuffer buffer);
}
