package pl.softace.sms2clipboard.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import pl.softace.sms2clipboard.config.Configuration;
import pl.softace.sms2clipboard.config.ConfigurationManager;
import pl.softace.sms2clipboard.utils.Icon;

/**
 * 
 * Frame displaying information about application.
 * 
 * @author lkawon@gmail.com
 *
 */
public class AboutFrame extends JFrame {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -2847280013796120620L;
	
	
	/**
	 * Main panel.
	 */
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public AboutFrame() {
		setTitle("About");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		setMinimumSize(new Dimension(320, 0));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(15, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		ImagePanel imagePanel = new ImagePanel(Icon.getImage(Icon.SIDE_64));
		contentPane.add(imagePanel);
		
		JPanel versionPanel = new JPanel();
		versionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(versionPanel);
		versionPanel.setLayout(new BoxLayout(versionPanel, BoxLayout.PAGE_AXIS));
		
		JLabel lblSms2ClipboardVersion = new JLabel("SMS2Clipboard version " + getClass().getPackage().getImplementationVersion() + ".");
		versionPanel.add(lblSms2ClipboardVersion);
		lblSms2ClipboardVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Configuration configuration = ConfigurationManager.getInstance().getConfiguration();
		//if (configuration != null) {
			JLabel lblDatbaseVersion = new JLabel("Database version " + configuration.getTemplatesDBVersion() + ".");
			versionPanel.add(lblDatbaseVersion);
			lblDatbaseVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
		//}
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new EmptyBorder(20, 5, 5, 5));
		contentPane.add(infoPanel);
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		
		JLabel lblForMoreInformation = new JLabel("For more information please visit:");
		lblForMoreInformation.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPanel.add(lblForMoreInformation);
		
		JPanel links = new JPanel();
		contentPane.add(links);
		links.setLayout(new BoxLayout(links, BoxLayout.PAGE_AXIS));
		
		//For more information visit 
		LinkLabel linkLabelWeb = new LinkLabel("www.google.com");
		linkLabelWeb.setForeground(Color.BLUE);
		linkLabelWeb.setHorizontalAlignment(SwingConstants.CENTER);
		linkLabelWeb.setAlignmentX(Component.CENTER_ALIGNMENT);
		linkLabelWeb.addActionListener(new LinkActionListener());
		links.add(linkLabelWeb);
		
		LinkLabel linkLabelFb = new LinkLabel("www.facebook.com/rocksmithgame");
		linkLabelFb.setForeground(Color.BLUE);
		linkLabelFb.setHorizontalAlignment(SwingConstants.CENTER);
		linkLabelFb.setAlignmentX(Component.CENTER_ALIGNMENT);				
		linkLabelFb.addActionListener(new LinkActionListener());
		links.add(linkLabelFb);				
		
		pack();
	}
	
	/**
	 * Shows the SMS in JFrame window.
	 */
	public static final void createAndShow() {		
		SwingUtilities.invokeLater(new Runnable() {

			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {								
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				double width = screenSize.getWidth();
				double height = screenSize.getHeight();
				
				AboutFrame aboutJFrame = new AboutFrame();
				aboutJFrame.setIconImages(Icon.getFrameIcons());
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
		AboutFrame.createAndShow();
	}	
}
