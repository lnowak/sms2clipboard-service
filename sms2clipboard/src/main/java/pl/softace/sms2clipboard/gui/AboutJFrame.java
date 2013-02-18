package pl.softace.sms2clipboard.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * JFrame that is displayed when SMS was received.
 * 
 * @author lkawon@gmail.com
 *
 */
public class AboutJFrame extends JFrame {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -2412341325134534L;

	/**
	 * Icon.
	 */
	private static final String ICON = "images/icon.png";
	
	/**
	 * A HREF tag.
	 */
	private static final String A_HREF = "<a href=\"";
	
	/**
	 * Close HREF tag.
	 */
	private static final String HREF_CLOSED = "\">";
	
	
	/**
	 * HREF end tag.
	 */
	private static final String HREF_END = "</a>";
	
	/**
	 * HTML tag.
	 */
	private static final String HTML = "<html>";
	
	/**
	 * HTML end tag. 
	 */
	private static final String HTML_END = "</html>";
	
	
	/**
	 * Constructor.
	 */
	public AboutJFrame() {				
		setTitle("About");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// version
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.LINE_AXIS));		
		JLabel titleLabel = new JLabel(htmlIfy("<h4>SMS2Clipboard Service " + getClass().getPackage().getImplementationVersion() + "</h4>"));
		textPanel.add(titleLabel);		
		textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));		
		
		Container contentPane = getContentPane();
		contentPane.add(textPanel, BorderLayout.CENTER);		
		
		pack();
	}
	
	/**
	 * Wraps string with a href tag.
	 * 
	 * @param string		string to wrap
	 * @return				wrapped string
	 */
	private static String linkIfy(String string) {
		return A_HREF.concat(string).concat(HREF_CLOSED).concat(string).concat(HREF_END);
	}

	/**
	 * Wrap string with html tag.
	 * 
	 * @param string		string to wrap
	 * @return				wrapped string
	 */
	private static String htmlIfy(String string) {
		return HTML.concat(string).concat(HTML_END);
	}
	
	/**
	 * Shows the SMS in JFrame window.
	 */
	public static final void createAndShow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {

			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {								
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				double width = screenSize.getWidth();
				double height = screenSize.getHeight();
				
				AboutJFrame aboutJFrame = new AboutJFrame();
				aboutJFrame.setIconImage(new ImageIcon(ICON).getImage());
				aboutJFrame.setResizable(false);				
				aboutJFrame.setLocation((int) width / 2 - aboutJFrame.getWidth() / 2, (int) height / 2 - aboutJFrame.getHeight() / 2);				
				aboutJFrame.setVisible(true);
			}
		});
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		AboutJFrame.createAndShow();
	}
}
