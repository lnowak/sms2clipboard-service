package pl.softace.sms2clipboard.net.api.packet.enums;


/**
 * 
 * Contains all possible parameters.
 * 
 * @author lkawon@gmail.com
 *
 */
public enum PacketParameter {
	
	/**
	 * Status of the response.
	 */
	STATUS						((byte) 20, ParameterType.INTEGER),
	
	/**
	 * String text.
	 */
	TEXT						((byte) 21, ParameterType.STRING),
	
	/**
	 * String text.
	 */
	SECURED_TEXT				((byte) 22, ParameterType.SECURED_STRING),
	
	/**
	 * Source of the SMS.
	 */
	SMS_SOURCE					((byte) 23, ParameterType.SECURED_STRING),
	
	/**
	 * Timestamp of the SMS.
	 */
	TIMESTAMP					((byte) 24, ParameterType.LONG),
	
	/**
	 * Secured with AES list of object.
	 */
	TEMPLATE_LIST				((byte) 25, ParameterType.SECURED_SMS_TEMPLATES_LIST),
	
	/**
	 * Template source.
	 */
	TEMPLATE_SOURCE				((byte) 26, ParameterType.STRING),
	
	/**
	 * Template SMS regex.
	 */
	TEMPLATE_SMS_REGEX			((byte) 27, ParameterType.STRING),
	
	/**
	 * Template password regex.
	 */
	TEMPLATE_PASSWORD_REGEX		((byte) 28, ParameterType.STRING);
	
	
	/**
	 * Parameter identifier.
	 */
	private byte id;
	
	/**
	 * Parameter length.
	 */
	private ParameterType type;
	
	
	/**
	 * Constructor.
	 * 
	 * @param id			identifier
	 * @param type			type
	 */
	private PacketParameter(byte id, ParameterType type) {
		this.id = id;
		this.type = type;
	}
	
	public final byte getId() {
		return id;
	}

	public final void setId(byte id) {
		this.id = id;
	}

	public final ParameterType getType() {
		return type;
	}

	public final void setType(ParameterType type) {
		this.type = type;
	}

	/**
	 * Get PacketType enumeration type by integer value.
	 * 
	 * @param id 		packet id as integer
	 * @return 			enumeration type
	 */
	public static PacketParameter getById(int id) {
		for (PacketParameter packetParameter : PacketParameter.values()) {
			if (packetParameter.getId() == id) {
				return packetParameter;
			}
		}
		
		return null;
	}
}
