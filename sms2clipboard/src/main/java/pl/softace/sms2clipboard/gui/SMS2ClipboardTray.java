package pl.softace.sms2clipboard.gui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.softace.sms2clipboard.utils.Icon;

/**
 * 
 * Class that sets up the system tray.
 * 
 * @author Lukasz
 *
 */
public class SMS2ClipboardTray {

	/**
	 * SLF4J logger/
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SMS2ClipboardTray.class);
	
	/**
	 * Tray icon.
	 */
	private TrayIcon trayIcon;
	
	
	/**
	 * Constructor.
	 */
	public SMS2ClipboardTray() {
		
	}
	
	/**
	 * Sets info icon and tooltip.
	 */
	public final void setInfoIcon() {
		trayIcon.setImage(Icon.getImage(Icon.FRONT_INFO_16));
		trayIcon.setToolTip("New version of database is available.");
	}
	
	/**
	 * Sets standard icon and tooltip.
	 */
	public final void setStandardIcon() {
		trayIcon.setImage(Icon.getImage(Icon.FRONT_STANDARD_16));
		trayIcon.setToolTip("SMS2Clipboard.");
	}
	
	/**
	 * Creates and initializes the tray.
	 */
	public final void initalize() {
		if (SystemTray.isSupported()) {         
			try {			
				PopupMenu popup = new PopupMenu();
		        trayIcon = new TrayIcon(Icon.getImage(Icon.FRONT_STANDARD_16), "SMS2Clipboard", popup);
		        SystemTray tray = SystemTray.getSystemTray();
		        
		        // settings menu
		        MenuItem settingsItem = new MenuItem("Settings");
		        settingsItem.addActionListener(new ActionListener() {
					
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public final void actionPerformed(ActionEvent arg0) {
						SettingsFrame.createAndShow();
						
					}
				});
		        popup.add(settingsItem);
		        
		        // update menu
		        MenuItem updateItem = new MenuItem("Check for update");
		        updateItem.addActionListener(new ActionListener() {
					
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public final void actionPerformed(ActionEvent arg0) {
						DBUpdateFrame.createAndShow();
						
					}
				});
		        popup.add(updateItem);
		        
		        // about menu
		        MenuItem aboutItem = new MenuItem("About");
		        aboutItem.addActionListener(new ActionListener() {
					
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public final void actionPerformed(ActionEvent arg0) {
						AboutFrame.createAndShow();
						
					}
				});
		        popup.add(aboutItem);		        
		        popup.addSeparator();
		        
		        // exit menu
		        MenuItem exitItem = new MenuItem("Exit");
		        exitItem.addActionListener(new ActionListener() {
					
					/* (non-Javadoc)
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
					 */
					@Override
					public final void actionPerformed(ActionEvent arg0) {
						System.exit(0);						
					}
				});		        
		        popup.add(exitItem);
		        
		        trayIcon.setPopupMenu(popup);		        
				tray.add(trayIcon);			
			} catch (AWTException e) {
				LOG.error("Error with tray icon.", e);
			}
        }
	}
}
