package pl.softace.passwordless.net.api.packet;

import java.nio.ByteBuffer;

/**
 * 
 * Packet send to check the connection and password.
 * 
 * @author lkawon@gmail.com
 *
 */
public class PingRequest extends Packet {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 8196090941386293865L;

	/**
	 * Ping text.
	 */
	private String text;
	

	/**
	 * Default constructor.
	 */
	public PingRequest() {
		setType(PacketType.PING_REQUEST_PACKET);
	}
	
	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}
	
	/* (non-Javadoc)
	 * @see pl.softace.passwordless.net.api.packet.Packet#getPacket()
	 */
	@Override
	public final ByteBuffer encodePacket() {
		countBodyLength();
		ByteBuffer buffer = ByteBuffer.allocate(Packet.HEADER_LENGTH + getBodyLength());		
		buffer.put(getHeader());
		buffer.put((byte) PacketParameter.TEXT.getId());
		buffer.putInt(text.length());
		buffer.put(text.getBytes());		
		buffer.flip();
	
		return buffer;
	}
	
	/* (non-Javadoc)
	 * @see pl.softace.passwordless.net.api.packet.Packet#setPacket(java.nio.ByteBuffer)
	 */
	@Override
	public final void decodeBody(ByteBuffer in) {		
		if (in.get() == PacketParameter.TEXT.getId()) {
			byte[] bytes = new byte[in.getInt()];
			in.get(bytes);
			text = new String(bytes);
		}	
	}

	/* (non-Javadoc)
	 * @see pl.softace.passwordless.net.api.packet.Packet#countBodyLength()
	 */
	@Override
	protected final void countBodyLength() {
		int bodyLength = 0;		
		bodyLength += 1 + Integer.SIZE/8 + text.length();
		setBodyLength(bodyLength);				
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PingRequest other = (PingRequest) obj;
		if (getId() != other.getId())
			return false;
		if (getType() != other.getType())
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PingRequest [id=");
		builder.append(getId());
		builder.append(", type=");
		builder.append(getType());
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
}
