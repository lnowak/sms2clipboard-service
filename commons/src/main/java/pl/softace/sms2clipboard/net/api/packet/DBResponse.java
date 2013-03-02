package pl.softace.sms2clipboard.net.api.packet;

import java.util.ArrayList;
import java.util.List;

import pl.softace.sms2clipboard.net.api.packet.annotations.PropertyParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketType;
import pl.softace.sms2clipboard.net.api.packet.enums.Status;
import pl.softace.sms2clipboard.template.SMSTemplate;

/**
 * 
 * Response packet generated to template DB version request.
 * 
 * @author lkawon@gmail.com
 *
 */
public class DBResponse extends ReflectedPacket {

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
	 * All SMS templates from DB.
	 */
	@PropertyParameter(parameter = PacketParameter.TEMPLATE_LIST)
	private List<SMSTemplate> templates = new ArrayList<SMSTemplate>();
	
	
	/**
	 * Default constructor.
	 */
	public DBResponse() {
		setType(PacketType.DB_RESPONSE);
	}

	public final Status getStatus() {
		return Status.getById(status);
	}

	public final void setStatus(Status status) {
		this.status = status.getId();
	}

	public final List<SMSTemplate> getTemplates() {
		return templates;
	}

	public final void setTemplates(List<SMSTemplate> templates) {
		this.templates = templates;
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
		DBResponse other = (DBResponse) obj;
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
		builder.append("DBResponse [type=");
		builder.append(getType());
		builder.append(", id=");
		builder.append(getId());
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
}
