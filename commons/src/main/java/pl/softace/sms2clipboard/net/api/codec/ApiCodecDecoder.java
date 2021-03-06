package pl.softace.sms2clipboard.net.api.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import pl.softace.sms2clipboard.net.api.factory.impl.PacketFactory;
import pl.softace.sms2clipboard.net.api.packet.Packet;
import pl.softace.sms2clipboard.security.AESCrypter;

/**
 * 
 * Codec decoder for API server and client.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ApiCodecDecoder implements ProtocolDecoder {

	/**
	 * Key used to store actual packet to decode.
	 */
	private static final String PACKET_STATE_KEY = ApiCodecDecoder.class.getName() + ".STATE";

	/**
	 * AES crypter.
	 */
	private AESCrypter crypter;
	
	
	/**
	 * Default constructor.
	 */
	public ApiCodecDecoder() {
		crypter = new AESCrypter();
	}

	public final void setAesPassword(String password) {
		crypter = new AESCrypter(password);
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolDecoder#decode(
	 * org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, 
	 * org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	@Override
	public final void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {			
		Packet packet = (Packet) session.getAttribute(PACKET_STATE_KEY);		
		if (packet == null) {
			packet = new PacketFactory().decodeHeader(in.buf());
			if (packet != null) {
				session.setAttribute(PACKET_STATE_KEY, packet);
			} else {
				return;
			}
		}
		
		if (packet.isPacketAvailable(in.buf().remaining())) {	
			packet.decodeBody(in.buf(), crypter);
			out.write(packet);			
			
			session.removeAttribute(PACKET_STATE_KEY);
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolDecoder#finishDecode(
	 * org.apache.mina.core.session.IoSession, org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	@Override
	public final void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolDecoder#dispose(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public final void dispose(IoSession session) throws Exception {
		
	}
}
