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
public class SMSConfirmation extends ReflectedPacket {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -8297409281479082374L;

	/**
	 * Status of the response.
	 */
	@PropertyParameter(parameter = PacketParameter.STATUS)
	private int status;
	
	
	/**
	 * Default constructor.
	 */
	public SMSConfirmation() {
		setType(PacketType.SMS_CONFIRMATION);
	}

	public final Status getStatus() {
		return Status.getById(status);
	}

	public final void setStatus(Status status) {
		this.status = status.getId();
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
		SMSConfirmation other = (SMSConfirmation) obj;
		if (getId() != other.getId())
			return false;
		if (getType() != other.getType())
			return false;
		if (status != other.status)
			return false;		
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SMSConfirmation [type=");
		builder.append(getType());
		builder.append(", id=");
		builder.append(getId());		
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
