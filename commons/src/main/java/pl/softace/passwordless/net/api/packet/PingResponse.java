package pl.softace.passwordless.net.api.packet;

import pl.softace.passwordless.net.api.packet.annotations.PropertyParameter;
import pl.softace.passwordless.net.api.packet.enums.PacketParameter;
import pl.softace.passwordless.net.api.packet.enums.PacketType;
import pl.softace.passwordless.net.api.packet.enums.Status;

/**
 * 
 * Response packet generated to ping request.
 * 
 * @author lkawon@gmail.com
 *
 */
public class PingResponse extends ReflectedPacket {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -364782100050883380L;

	/**
	 * Status of the response.
	 */
	@PropertyParameter(parameter = PacketParameter.STATUS)
	private int status;
	
	/**
	 * Token used in request.
	 */
	@PropertyParameter(parameter = PacketParameter.TEXT)
	private String text;
	
	
	/**
	 * Default constructor.
	 */
	public PingResponse() {
		setType(PacketType.PING_RESPONSE_PACKET);
	}

	public final Status getStatus() {
		return Status.getById(status);
	}

	public final void setStatus(Status status) {
		this.status = status.getId();
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
		result = prime * result + (int) (status ^ (status >>> 32));
		result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
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
		PingResponse other = (PingResponse) obj;
		if (getId() != other.getId())
			return false;
		if (getType() != other.getType())
			return false;
		if (status != other.status)
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
		builder.append("PingResponse [type=");
		builder.append(getType());
		builder.append(", id=");
		builder.append(getId());				
		builder.append(", text=");
		builder.append(text);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
