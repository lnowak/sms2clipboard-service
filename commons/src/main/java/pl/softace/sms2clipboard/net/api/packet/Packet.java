package pl.softace.sms2clipboard.net.api.packet;

import java.io.Serializable;
import java.nio.ByteBuffer;

import pl.softace.sms2clipboard.net.api.packet.enums.PacketType;

/**
 * 
 * Super class for all of the packets sent from between client and server.
 * 
 * @author lkawon@gmail.com
 *
 */
public abstract class Packet implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -1169292313793693851L;

	/**
	 * Type of the packet.
	 */
	private PacketType type;
	
	/**
	 * Packet identifier (used to identify request and response).
	 */
	private int id;
	
	/**
	 * Body length;
	 */
	private int bodyLength;
	
	/**
	 * Length of the header.
	 */
	public static final int HEADER_LENGTH = Byte.SIZE/8 + Integer.SIZE/8 + Integer.SIZE/8;
	
	
	public final PacketType getType() {
		return type;
	}

	public final void setType(PacketType type) {
		this.type = type;
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}
	
	public final void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}
	
	public final int getBodyLength() {
		return bodyLength;
	}

	/**
	 * Returns the header as a byte array.
	 * 
	 * @return byte array
	 */
	protected final byte[] getHeader() {
		ByteBuffer buffer = ByteBuffer.allocate(HEADER_LENGTH);
		buffer.put(type.getId());
		buffer.putInt(id);
		buffer.putInt(bodyLength);
		
		return buffer.array();		
	}
	
	/**
	 * Returns the packet as a byte array (body with header).
	 * 
	 * @return byte array
	 */
	public abstract ByteBuffer encodePacket(String password);
	
	/**
	 * Gets and sets the packet parameters from the buffer. 
	 * 
	 * @param in	byte buffer
	 */
	public abstract void decodeBody(ByteBuffer buffer, String password);
	
	/**
	 * Count body length.
	 */
	protected abstract void countBodyLength(String password);
	
	/**
	 * Checks if the packet could be decoded from the incoming number of bytes.
	 * 
	 * @param numberOfBytes		number of received bytes 
	 * @return					true if packet can be decoded
	 * 							false if packet can't be decoded
	 */
	public final boolean isPacketAvailable(int numberOfBytes) {
		return (numberOfBytes >= getBodyLength());
	}
}
