package pl.softace.passwordless.net.api.packet.enums;


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
	STATUS				((byte) 20, ParameterType.INTEGER),
	
	/**
	 * String text.
	 */
	TEXT				((byte) 21, ParameterType.SECURED_STRING),
	
	/**
	 * Source of the SMS.
	 */
	SMS_SOURCE			((byte) 22, ParameterType.SECURED_STRING),
	
	/**
	 * Timestamp of the SMS.
	 */
	TIMESTAMP			((byte) 23, ParameterType.LONG);
	
	
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
