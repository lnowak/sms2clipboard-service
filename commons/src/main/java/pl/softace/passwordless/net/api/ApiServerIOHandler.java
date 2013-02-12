package pl.softace.passwordless.net.api;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

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
	
	
	/* (non-Javadoc)
	 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionOpened(org.apache.mina.core.session.IoSession)
	 */
	public final void sessionOpened(IoSession session) throws Exception {
		LOG.debug("Session " + session + " opened.");
    }

    /* (non-Javadoc)
     * @see org.apache.mina.core.service.IoHandlerAdapter#sessionClosed(org.apache.mina.core.session.IoSession)
     */
    public final void sessionClosed(IoSession session) throws Exception {
    	LOG.debug("Session " + session + " closed.");
    }
    
    /* (non-Javadoc)
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    public final void messageReceived(IoSession session, Object message) throws Exception {
        LOG.debug("Message " + message + " received from" + session + ".");
    }

    /* (non-Javadoc)
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageSent(org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    public final void messageSent(IoSession session, Object message) throws Exception {
    	LOG.debug("Message " + message + " sent to " + session + ".");
    }
}
