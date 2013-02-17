package pl.softace.sms2clipboard.net.api.codec;

import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import pl.softace.sms2clipboard.net.api.packet.Packet;

/**
 * 
 * Protocol codec encoder for API server and client.
 * 
 * @author Lukasz
 *
 */
public class ApiCodecEncoder implements ProtocolEncoder {

	/**
	 * AES password.
	 */
	private String aesPassword = "sms2clipboard";
	
	
	public final String getAesPassword() {
		return aesPassword;
	}

	public final void setAesPassword(String aesPassword) {
		this.aesPassword = aesPassword;
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolEncoder#encode(
	 * org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
	 */
	@Override
	public final void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		ByteBuffer packetBuffer = ((Packet) message).encodePacket(aesPassword);
		IoBuffer buffer = IoBuffer.allocate(packetBuffer.remaining());
		buffer.put(packetBuffer);
		buffer.flip();
		
		out.write(buffer);
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolEncoder#dispose(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public final void dispose(IoSession session) throws Exception {
			
	}
}
