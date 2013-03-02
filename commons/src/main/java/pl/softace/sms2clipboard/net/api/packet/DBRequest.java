package pl.softace.sms2clipboard.net.api.packet;

import pl.softace.sms2clipboard.net.api.packet.enums.PacketType;

/**
 * 
 * Request send to get the template DB.
 * 
 * @author lkawon@gmail.com
 *
 */
public class DBRequest extends ReflectedPacket {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -82748732658932L;

	/**
	 * Default constructor.
	 */
	public DBRequest() {
		setType(PacketType.DB_REQUEST);
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
		DBRequest other = (DBRequest) obj;
		if (getId() != other.getId())
			return false;
		if (getType() != other.getType())
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DBRequest [type=");
		builder.append(getType());		
		builder.append(", id=");
		builder.append(getId());
		builder.append("]");
		return builder.toString();
	}
}
