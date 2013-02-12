package pl.softace.passwordless.net.api.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 
 * Protocol codec encoder for API server and client.
 * 
 * @author Lukasz
 *
 */
public class ApiCodecEncoder implements ProtocolEncoder {

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolEncoder#encode(
	 * org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
	 */
	@Override
	public final void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolEncoder#dispose(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public final void dispose(IoSession session) throws Exception {
			
	}
}
