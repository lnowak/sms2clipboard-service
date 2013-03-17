package pl.softace.sms2clipboard.locale;

/**
 * 
 * Enumeration with all translation categories.
 * 
 * @author lkawon@gmail.com
 *
 */
public enum Category {

	NEW_DB_VERSION_AVAILABLE		("new.db.version.available"),
	
	TRAY_MENU_SETTINGS				("tray.menu.settings"),
	
	TRAY_MENU_UPDATE				("tray.menu.update"),
	
	TRAY_MENU_ABOUT					("tray.menu.about"),
	
	TRAY_MENU_EXIT					("tray.menu.exit"),
	
	TRAY_APPLICATION_NAME			("tray.application.name"),
	
	
	FRAME_SMS_WINDOW_TILTE			("frame.sms.window.title"),
	
	FRAME_SMS_MAIN_LABEL			("frame.sms.main.label"),
	
	FRAME_SMS_BUTTON_LABEL			("frame.sms.button.label"),
	
	
	FRAME_ABOUT_WINDOW_TITLE				("frame.about.window.title"),
	
	FRAME_ABOUT_MAIN_VERSION_TEXT_LABEL		("frame.about.main.version.label"),
	
	FRAME_ABOUT_DB_VERSION_TEXT_LABEL		("frame.about.db.version.label"),
	
	FRAME_ABOUT_MORE_LABEL					("frame.about.more.label"),
	
	FRAME_ABOUT_LINK_1						("frame.about.link1"),
	
	FRAME_ABOUT_LINK_2						("frame.about.link2"),
	
	
	FRAME_UPDATE_WINDOW_TITLE					("frame.update.window.title"),	

	FRAME_UPDATE_SERVICE_NOT_AVAILABLE_LABEL	("frame.update.service.not.available.label"),
	
	FRAME_UPDATE_NEW_VERSION_IS_AVAILABLE_LABEL	("frame.update.new.version.available.label"),
	
	FRAME_UPDATE_LATEST_VERSION_LABEL			("frame.update.latest.version.label"),
	
	FRAME_UPDATE_CHANGES_LABEL					("frame.update.changes.label"),
	
	FRAME_UPDATE_BUTTON_CLOSE_LABEL				("frame.update.button.close.label"),
	
	FRAME_UPDATE_BUTTON_UPDATE_LABEL			("frame.update.button.update.label"),
	
	FRAME_UPDATE_BUTTON_CANCEL_LABEL			("frame.update.button.cancel.label"),
	
	
	FRAME_SETTINGS_WINDOW_TITLE					("frame.settings.window.title"),
	
	FRAME_SETTINGS_PASSWORD_LABEL				("frame.settings.password.label"),
	
	FRAME_SETTINGS_PORT_LABEL				("frame.settings.port.label"),
	
	FRAME_SETTINGS_BUTTON_CANCEL_LABEL			("frame.settings.button.cancel.label"),
	
	FRAME_SETTINGS_BUTTON_SAVE_LABEL			("frame.settings.button.save.label"),
	
	FRAME_SETTINGS_PORT_SELECTED_ERROR			("frame.settings.port.selected.error");
		
	
	/**
	 * Translation key.
	 */
	private String key;
	
	
	/**
	 * Constructor.
	 * 
	 * @param key		key
	 */
	private Category(String key) {
		this.key = key;
	}

	public final String getKey() {
		return key;
	}
}
