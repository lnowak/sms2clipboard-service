package pl.softace.passwordless.net.api.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.passwordless.net.api.codec.ApiProtocolCodecFactory;

/**
 * 
 * TCP server that handles API requests.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ApiServer {

	/**
	 * Log4j logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ApiServer.class);
	
	
	
	/**
	 * Default port.
	 */
	private static final int DEFAULT_PORT = 8080;
	
	/**
	 * Server port;
	 */
	private int port;
	
	/**
	 * NIO socket acceptor.
	 */
	private NioSocketAcceptor acceptor;
	
	/**
	 * Packet handler.
	 */
	private IPacketHandler packetHandler;
	
	/**
	 * Codec factory.
	 */
	private ApiProtocolCodecFactory codecFactory = new ApiProtocolCodecFactory();
	
	
	/**
	 * Default constructor.
	 */
	public ApiServer() {
		this.port = DEFAULT_PORT;
	}
		
	public final IPacketHandler getPacketHandler() {
		return packetHandler;
	}

	public final void setPacketHandler(IPacketHandler packetHandler) {
		this.packetHandler = packetHandler;
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
	 * Starts the server.
	 */
	public final void startServer() {
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));
		//acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		
		acceptor.setHandler(new ApiServerIOHandler(packetHandler));
		try {
			acceptor.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			LOG.error("Exception in server.", e);
		}
		
		LOG.debug("Server started on port " + port + ".");
	}
	
	/**
	 * Stops the server.
	 */
	public final void stopServer() {
		acceptor.unbind();
	
		LOG.debug("Server on port " + port + " was stopped.");
	}
}
