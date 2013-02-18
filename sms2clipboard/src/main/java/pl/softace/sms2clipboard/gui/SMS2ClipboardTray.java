package pl.softace.sms2clipboard.gui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final String TRAY_ICON = "images/icon.png";
	
	
	/**
	 * Constructor.
	 */
	public SMS2ClipboardTray() {
		
	}
	
	/**
	 * Creates and initializes the tray.
	 */
	public final void initalize() {
		if (SystemTray.isSupported()) {         
			try {			
				PopupMenu popup = new PopupMenu();
		        TrayIcon trayIcon = new TrayIcon(ImageIO.read(new File(TRAY_ICON)), "SMS2Clipboard Service", popup);
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
			} catch (IOException e) {
				LOG.error("Error with tray icon.", e);
			} catch (AWTException e) {
				LOG.error("Error with tray icon.", e);
			}
        }
	}
}
