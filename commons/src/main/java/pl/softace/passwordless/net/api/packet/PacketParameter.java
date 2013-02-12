package pl.softace.passwordless.net.api.packet;

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
	STATUS				(20, 1),
	
	/**
	 * String text.
	 */
	TEXT				(21, 0);
	
	
	/**
	 * Parameter identifier.
	 */
	private int id;
	
	/**
	 * Parameter length.
	 */
	private int length;
	
	
	/**
	 * Constructor.
	 * 
	 * @param id			identifier
	 * @param length		length
	 */
	private PacketParameter(int id, int length) {
		this.id = id;
		this.length = length;
	}
	
	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final int getLength() {
		return length;
	}

	public final void setLength(int length) {
		this.length = length;
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
