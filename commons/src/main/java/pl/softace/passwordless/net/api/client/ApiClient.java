package pl.softace.passwordless.net.api.client;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import pl.softace.passwordless.net.api.codec.ApiProtocolCodecFactory;
import pl.softace.passwordless.net.api.packet.Packet;

/**
 * 
 * TCP client used to connect to the API.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ApiClient extends IoHandlerAdapter {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOG = Logger.getLogger(ApiClient.class);
	
	/**
	 * Connect timeout.
	 */
	private static final long CONNECT_TIMEOUT = 10 * 1000L;
	
	/**
	 * Receive timeout.
	 */
	private static final long RECEIVE_TIMEOUT = 5 * 1000L;
	
	/**
	 * Server host.
	 */
	private String host;
	
	/**
	 * Server port;
	 */
	private int port;
	
	/**
	 * IO session.
	 */
	private IoSession ioSession;
	
	/**
	 * NIO socket connector.
	 */
	private NioSocketConnector connector;
	
	/**
	 * Queue with received messages.
	 */
	private Queue<Packet> queue = new LinkedList<Packet>();
	
	/**
	 * Codec factory.
	 */
	private ApiProtocolCodecFactory codecFactory = new ApiProtocolCodecFactory();
	
	
	/**
	 * Constructor.
	 * 
	 * @param host		server host
	 * @param port		server port
	 */
	public ApiClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	/**
	 * Sets new AES password.
	 * 
	 * @param password	AES password
	 */
	public final void setAesPassword(String password) {
		codecFactory.setAesPassword(password);
	}
	
	/**
	 * Connects to the server.
	 */
	public final void connect() {
		LOG.debug("Trying to connect to " + host + " on port " + port + ".");
		
		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));
		//connector.getFilterChain().addLast("logger", new LoggingFilter());
		
		connector.setHandler(this);
		
		ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
		future.awaitUninterruptibly();
		ioSession = future.getSession();
	}
	
	/**
	 * Disconnects from the server.
	 */
	public final void disconnect() {
		if (ioSession != null && ioSession.isConnected()) {
			LOG.debug("Disconnecting from " + host + " on port " + port + ".");
			
			CloseFuture future = ioSession.close(true);
			future.awaitUninterruptibly();
			connector.dispose();
		}
	}
	
	/**
	 * Check if the client is connected.
	 * 
	 * @return		true if connected
	 */
	public final boolean isConnected() {
		return ioSession != null ? ioSession.isConnected() : false;
	}
	
	/**
	 * Sends packet.
	 * 
	 * @param packet	packet to send
	 * @return			received response
	 */
	public final Packet send(Packet packet) {
		Packet response = null;		
		
		ioSession.write(packet);	
		long startTime = System.currentTimeMillis();
		do {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				LOG.error("Exception occured.", e);
			}
			response = queue.poll();
		} while (response == null  && ioSession.isConnected() && (System.currentTimeMillis() - startTime < RECEIVE_TIMEOUT));
		
		return response;
	}
	
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
	 * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(
	 * org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	public final void messageReceived(IoSession session, Object message) throws Exception {
		LOG.debug("Message " + message + " received from" + session + ".");
		if (message instanceof Packet) {
			queue.add((Packet) message);
		}
    }
	
	/* (non-Javadoc)
     * @see org.apache.mina.core.service.IoHandlerAdapter#messageSent(
     * org.apache.mina.core.session.IoSession, java.lang.Object)
     */
    public final void messageSent(IoSession session, Object message) throws Exception {
    	LOG.debug("Message " + message + " sent to " + session + ".");
    }
}
