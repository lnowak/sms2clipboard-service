package pl.softace.passwordless.net.api.server;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderException;

import pl.softace.passwordless.net.api.packet.Packet;

/**
 * 
 * Extends the IO handler that is receiving the messages from clients.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ApiServerIOHandler extends IoHandlerAdapter {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOG = Logger.getLogger(ApiServerIOHandler.class);
	
	/**
	 * Packet handler.
	 */
	private IPacketHandler packetHandler;
	
	
	/**
	 * Constructor.
	 * 
	 * @param packetHandler		packet handler
	 */
	public ApiServerIOHandler(IPacketHandler packetHandler) {
		this.packetHandler = packetHandler;
	}
	
	
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionOpened(
	 * org.apache.mina.core.session.IoSession)
	 */
	public final void sessionOpened(IoSession session) throws Exception {
		LOG.debug("Session " + session + " opened.");
    }

    /* (non-Javadoc)
     * @see org.apache.mina.core.service.IoHandlerAdapter#sessionClosed(
     * org.apache.mina.core.session.IoSession)
     */
    public final void sessionClosed(IoSession session) throws Exception {
    	LOG.debug("Session " + session + " closed.");
    }
    
    /* (non-Javadoc)
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(
     * org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    public final void messageReceived(IoSession session, Object message) throws Exception {
        LOG.debug("Message " + message + " received from " + session + ".");
        
        Packet response = packetHandler.handlePacket((Packet) message);
        if (response != null) {
        	session.write(response);
        }
    }

    /* (non-Javadoc)
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageSent(
     * org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    public final void messageSent(IoSession session, Object message) throws Exception {
    	LOG.debug("Message " + message + " sent to " + session + ".");
    }
    
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
       if (cause instanceof ProtocolDecoderException) {
    	   LOG.debug("Closing " + session + " because of decoding exception.");
    	   session.close(true);    	   
       }
    }
}
