package pl.softace.passwordless.net.api;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

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
	private static final Logger LOG = Logger.getLogger(ApiServer.class);
	
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
	 * Default constructor.
	 */
	public ApiServer() {
		this.port = DEFAULT_PORT;
	}
	
	/**
	 * Starts the server.
	 */
	public final void startServer() {
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ApiProtocolCodecFactory()));
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		
		acceptor.setHandler(new ApiServerIOHandler());
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
