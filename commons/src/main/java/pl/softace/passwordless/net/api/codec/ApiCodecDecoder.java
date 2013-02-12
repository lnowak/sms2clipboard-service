package pl.softace.passwordless.net.api.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * 
 * Codec decoder for API server and client.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ApiCodecDecoder implements ProtocolDecoder {

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolDecoder#decode(
	 * org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, 
	 * org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	@Override
	public final void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		
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
