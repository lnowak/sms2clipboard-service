package pl.softace.sms2clipboard.net.api.packet;

import pl.softace.sms2clipboard.net.api.packet.annotations.PropertyParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketType;

/**
 * 
 * Packet send to check the connection and password.
 * 
 * @author lkawon@gmail.com
 *
 */
public class PingRequest extends ReflectedPacket {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 8196090941386293865L;

	/**
	 * Ping text.
	 */
	@PropertyParameter(parameter = PacketParameter.SECURED_TEXT)
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
		builder.append("PingRequest [type=");
		builder.append(getType());		
		builder.append(", id=");
		builder.append(getId());
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
}
