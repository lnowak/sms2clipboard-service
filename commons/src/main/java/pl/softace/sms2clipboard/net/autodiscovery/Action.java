package pl.softace.sms2clipboard.net.autodiscovery;

/**
 * 
 * Available actions.
 * 
 * @author lkawon@gmail.com
 *
 */
public enum Action {
	
	/**
	 * Searches for instances.
	 */
	SEARCH				("SEARCH"),
	
	/**
	 * Instance was found.
	 */
	SERVER_INSTANCE		("SERVER-INSTANCE");
	
	/**
	 * Action text.
	 */
	private String name;
	
	
	/**
	 * Default constructor.
	 * 
	 * @param action
	 */
	private Action(String action) {
		this.name = action;
	}

	public final String getName() {
		return name;
	}
	
	/**
	 * Finds action by name.
	 * 
	 * @param name		action name
	 * @return			found action
	 */
	public static final Action findByName(String name) {
		Action action = null;
		for (Action temp : values()) {
			if (temp.getName().equals(name)) {
				action = temp;
				break;
			}
		}
		
		return action;
	}
}