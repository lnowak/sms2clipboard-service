package pl.softace.sms2clipboard.net.api.packet;

import pl.softace.sms2clipboard.net.api.packet.annotations.PropertyParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketType;
import pl.softace.sms2clipboard.net.api.packet.enums.Status;

/**
 * 
 * Response packet generated to template DB version request.
 * 
 * @author lkawon@gmail.com
 *
 */
public class DBVersionResponse extends ReflectedPacket {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -17263928731649874L;

	/**
	 * Status of the response.
	 */
	@PropertyParameter(parameter = PacketParameter.STATUS)
	private int status;
	
	/**
	 * Token used in request.
	 */
	@PropertyParameter(parameter = PacketParameter.SECURED_TEXT)
	private String version;
	
	
	/**
	 * Default constructor.
	 */
	public DBVersionResponse() {
		setType(PacketType.DB_VERSION_RESPONSE);
	}

	public final Status getStatus() {
		return Status.getById(status);
	}

	public final void setStatus(Status status) {
		this.status = status.getId();
	}

	public final String getVersion() {
		return version;
	}

	public final void setVersion(String version) {
		this.version = version;
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
		result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
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
		DBVersionResponse other = (DBVersionResponse) obj;
		if (getId() != other.getId())
			return false;
		if (getType() != other.getType())
			return false;
		if (status != other.status)
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DBVersionResponse [type=");
		builder.append(getType());
		builder.append(", id=");
		builder.append(getId());				
		builder.append(", version=");
		builder.append(version);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
