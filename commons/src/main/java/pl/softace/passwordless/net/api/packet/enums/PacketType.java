package pl.softace.passwordless.net.api.packet.enums;

/**
 * 
 * Type of the packet.
 * 
 * @author lkawon@gmail.com
 *
 */
public enum PacketType {

	/**
	 * Ping request packet.
	 */
	PING_REQUEST_PACKET				((byte) 10),
	
	/**
	 * Ping response packet.
	 */
	PING_RESPONSE_PACKET			((byte) 11),
	
	/**
	 * SMS packet.
	 */
	SMS_PACKET						((byte) 12),
	
	/**
	 * SMS confirmation.
	 */
	SMS_CONFIRMATION				((byte) 13);
	
	
	/**
	 * Packet identifier.
	 */
	private byte id;
	
	
	/**
	 * Constructor.
	 * 
	 * @param id		packet identifier
	 */
	PacketType(byte id) {
		this.id = id;
	}
	
	public byte getId() {
		return id;
	}
	
	/**
	 * Get PacketType enumeration type by integer value.
	 * 
	 * @param id 		packet id as integer
	 * @return 			enumeration type
	 */
	public static PacketType getById(byte id) {
		for (PacketType packetType : PacketType.values()) {
			if (packetType.getId() == id) {
				return packetType;
			}
		}
	
		return null;
	}
}
