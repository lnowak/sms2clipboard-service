package pl.softace.sms2clipboard.utils;

import java.awt.Image;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * 
 * Contains all icons.
 * 
 * @author lkawon@gmail.com
 *
 */
public enum Icon {

	/**
	 * Front standard icon.
	 */
	FRONT_STANDARD_32		("images/front_32.png"),
	
	/**
	 * Front with information icon.
	 */
	FRONT_INFO_32			("images/front_info_32.png"),
	
	/**
	 * Side icon.
	 */
	SIDE_32					("images/side_32.png"),
	
	/**
	 * Side icon.
	 */
	SIDE_64					("images/side_64.png"),
	
	/**
	 * Front standard icon.
	 */
	FRONT_STANDARD_16		("images/front_16.png"),
	
	/**
	 * Front with information icon.
	 */
	FRONT_INFO_16			("images/front_info_16.png");
	
	
	/**
	 * Icon filename;
	 */
	private String fileName;
	
	
	/**
	 * Constructor.
	 * 
	 * @param fileName		icon filename
	 */
	private Icon(String fileName) {
		this.fileName = fileName;
	}

	public final String getFileName() {
		return fileName;
	}

	public final void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Gets list of icons for frame.
	 * 
	 * @return		list of icons
	 */
	public final static List<Image> getFrameIcons() {
		return Arrays.asList(
				new ImageIcon(ClassLoader.getSystemResource(Icon.FRONT_STANDARD_16.getFileName())).getImage(),
				new ImageIcon(ClassLoader.getSystemResource(Icon.SIDE_32.getFileName())).getImage()
				);
	}
	
	/**
	 * Gets icon.
	 * 
	 * @param icon		icon
	 * @return			image
	 */
	public final static Image getImage(Icon icon) {
		return new ImageIcon(ClassLoader.getSystemResource(icon.getFileName())).getImage();
	}
}
