package pl.softace.sms2clipboard.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * 
 * Panel displaying image.
 * 
 * @author lkawon@gmail.com
 *
 */
public class ImagePanel extends JPanel {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 4215007343753483426L;

	/**
	 * Image to display.
	 */
	private Image image;
	
	
	/**
	 * Constructor.
	 * 
	 * @param image		image
	 */
	public ImagePanel(Image image) {
		this.image = image;
		Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
	}
	
	 /* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		 g.drawImage(image, 0, 0, null);
	 }
}
