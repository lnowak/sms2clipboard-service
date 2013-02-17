package pl.softace.sms2clipboard.net.api.packet;

import pl.softace.sms2clipboard.net.api.packet.annotations.PropertyParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketParameter;
import pl.softace.sms2clipboard.net.api.packet.enums.PacketType;

/**
 * 
 * Implementation of the SMS packet.
 * 
 * @author lkaown@gmail.com
 *
 */
public class SMSPacket extends ReflectedPacket {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Timestamp.
	 */
	@PropertyParameter(parameter = PacketParameter.TIMESTAMP)
	private long timestamp;
	
	/**
	 * Source.
	 */
	@PropertyParameter(parameter = PacketParameter.SMS_SOURCE)
	private String source;
	
	/**
	 * SMS text.
	 */
	@PropertyParameter(parameter = PacketParameter.TEXT)
	private String text;
	
	
	/**
	 * Constructor.
	 */
	public SMSPacket() {
		setType(PacketType.SMS_PACKET);
	}

	public final String getText() {
		return text;
	}

	public final void setText(String text) {
		this.text = text;
	}
	
	public final long getTimestamp() {
		return timestamp;
	}

	public final void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public final String getSource() {
		return source;
	}

	public final void setSource(String source) {
		this.source = source;
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
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
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
		SMSPacket other = (SMSPacket) obj;
		if (getId() != other.getId())
			return false;
		if (getType() != other.getType())
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SMSPacket [type=");
		builder.append(getType());		
		builder.append(", id=");
		builder.append(getId());
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", source=");
		builder.append(source);
		builder.append(", text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
}
