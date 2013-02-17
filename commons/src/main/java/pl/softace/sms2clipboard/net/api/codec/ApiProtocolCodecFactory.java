package pl.softace.sms2clipboard.net.api.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 
 * Protocol codec factory for API server and client.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ApiProtocolCodecFactory implements ProtocolCodecFactory {

	/**
	 * Protocol encoder.
	 */
	private final ProtocolEncoder encoder;
	
	/**
	 * Protocol decoder.
	 */
	private final ProtocolDecoder decoder;
	
	
	/**
	 * Default constructor.
	 */
	public ApiProtocolCodecFactory() {
		encoder = new ApiCodecEncoder();
		decoder = new ApiCodecDecoder();
	}
	
	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getEncoder(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public final ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	/* (non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getDecoder(org.apache.mina.core.session.IoSession)
	 */
	@Override
	public final ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}
	
	/**
	 * Sets new AES password.
	 * 
	 * @param password		AES password
	 */
	public final void setAesPassword(String password) {
		((ApiCodecEncoder) encoder).setAesPassword(password);
		((ApiCodecDecoder) decoder).setAesPassword(password);
	}
}
