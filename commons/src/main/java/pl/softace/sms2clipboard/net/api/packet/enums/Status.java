package pl.softace.sms2clipboard.net.api.packet.enums;

/**
 * 
 * Status sent in packets.
 * 
 * @author lkawon@gmail.com
 *
 */
public enum Status {

	/**
	 * Everything is OK.
	 */
	OK				(0),
	
	/**
	 * Unknown error.
	 */
	UNKNOWN_ERROR	(999);
	
	
	/**
	 * Error identifier.
	 */
	private int id;
	
	
	/**
	 * Private constructor.
	 * 
	 * @param id	error identifier
	 */
	private Status(int id) {
		this.id = id;
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Finds status by identifier.
	 * 
	 * @param id	status identifier
	 * @return
	 */
	public static final Status getById(int id) {
		Status status = null;
		for (Status temp : values()) {
			if (temp.getId() == id) {
				status = temp;
				break;
			}
		}
		return status;
	}
}
