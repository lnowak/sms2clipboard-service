package pl.softace.sms2clipboard.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Action listener used for opening browser.
 * 
 * @author lkawon@gmail.com
 *
 */
public class LinkActionListener implements ActionListener {
		
	/**
	 * SLF4j logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AboutFrame.class);
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public final void actionPerformed(ActionEvent event) {		
		Desktop desktop = Desktop.getDesktop();
		if (desktop.isSupported(Desktop.Action.BROWSE)) {
			URI uri = null;
			try {
				uri = new URI(event.getActionCommand());
				desktop.browse(uri);
			} catch (URISyntaxException e) {
				LOG.error("Exception occurred.", e);
			} catch (IOException e) {
				LOG.error("Exception occurred.", e);
			}
		}
		
	}

}
