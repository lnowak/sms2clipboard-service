package pl.softace.sms2clipboard.net.autodiscovery;

public enum Command {

	/**
	 * Command send by client to search for servers.
	 */
	SEARCH_COMMAND		("SMS2CLIPBOARD-SERVER-SEARCH"),
	
	/**
	 * Command send by server in response to search command.
	 */
	RESPONSE_COMMAND	("SMS2CLIPBOARD-SERVER-INSTANCE");
	
	
	/**
	 * Command data.
	 */
	private String data;
	
	
	/**
	 * Constructor.
	 * 
	 * @param data		command data
	 */
	private Command(String data) {
		this.data = data;
	}

	public final String getData() {
		return data;
	}

	public final void setData(String data) {
		this.data = data;
	}
}
