package pl.softace.sms2clipboard.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pl.softace.sms2clipboard.SMS2Clipboard;
import pl.softace.sms2clipboard.config.Configuration;
import pl.softace.sms2clipboard.config.ConfigurationManager;
import pl.softace.sms2clipboard.locale.Category;
import pl.softace.sms2clipboard.locale.Translation;
import pl.softace.sms2clipboard.utils.Icon;
import pl.softace.sms2clipboard.utils.Utilities;

/**
 * 
 * Settings frame.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SettingsFrame extends JFrame {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -7546802544820039121L;
	
	/**
	 * Main panel.
	 */
	private JPanel contentPane;
	
	/**
	 * Text field with password.
	 */
	private JTextField textFieldPassword;
	
	/**
	 * Text field with update delay.
	 */
	private JTextField textFieldPort;

	
	/**
	 * Create the frame.
	 */
	public SettingsFrame() {
		setResizable(false);
		setTitle(Translation.getInstance().getProperty(Category.FRAME_SETTINGS_WINDOW_TITLE));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 354, 131);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JPanel settingsPanel = new JPanel();
		contentPane.add(settingsPanel);
		GridBagLayout gbl_settingsPanel = new GridBagLayout();
		gbl_settingsPanel.columnWidths = new int[]{0, 0, 0};
		gbl_settingsPanel.rowHeights = new int[]{0, 0, 0};
		gbl_settingsPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_settingsPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		settingsPanel.setLayout(gbl_settingsPanel);
		
		JLabel lblPassword = new JLabel(Translation.getInstance().getProperty(Category.FRAME_SETTINGS_PASSWORD_LABEL));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 0;
		settingsPanel.add(lblPassword, gbc_lblPassword);
		
		textFieldPassword = new JTextField();
		GridBagConstraints gbc_textFieldPassword = new GridBagConstraints();
		gbc_textFieldPassword.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPassword.gridx = 1;
		gbc_textFieldPassword.gridy = 0;
		settingsPanel.add(textFieldPassword, gbc_textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JLabel lblUpdateDelay = new JLabel(Translation.getInstance().getProperty(Category.FRAME_SETTINGS_PORT_LABEL));
		GridBagConstraints gbc_lblUpdateDelay = new GridBagConstraints();
		gbc_lblUpdateDelay.anchor = GridBagConstraints.EAST;
		gbc_lblUpdateDelay.insets = new Insets(0, 0, 0, 5);
		gbc_lblUpdateDelay.gridx = 0;
		gbc_lblUpdateDelay.gridy = 1;
		settingsPanel.add(lblUpdateDelay, gbc_lblUpdateDelay);
		
		textFieldPort = new JTextField();
		GridBagConstraints gbc_textFieldPort = new GridBagConstraints();
		gbc_textFieldPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPort.gridx = 1;
		gbc_textFieldPort.gridy = 1;
		settingsPanel.add(textFieldPort, gbc_textFieldPort);
		textFieldPort.setColumns(10);
		
		JPanel buttonsPanel = new JPanel();
		contentPane.add(buttonsPanel);
		
		JButton btnCancel = new JButton(
				Translation.getInstance().getProperty(Category.FRAME_SETTINGS_BUTTON_CANCEL_LABEL));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			    dispose();
			}
		});
		
		JButton btnSave = new JButton(
				Translation.getInstance().getProperty(Category.FRAME_SETTINGS_BUTTON_SAVE_LABEL));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (save()) {
					setVisible(false);
			    	dispose();
				}
			}
		});
		buttonsPanel.add(btnSave);
		buttonsPanel.add(btnCancel);
		
		load();
	}

	/**
	 * Launch the frame.
	 */
	public static void createAndShow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					double width = screenSize.getWidth();
					double height = screenSize.getHeight();
					
					SettingsFrame frame = new SettingsFrame();
					frame.setIconImages(Icon.getFrameIcons());
					frame.setResizable(false);				
					frame.setLocation((int) width / 2 - frame.getWidth() / 2, (int) height / 2 - frame.getHeight() / 2);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Loads configuration from file.
	 */
	public final void load() {
		Configuration configuration = ConfigurationManager.getInstance().getConfiguration();
		if (configuration != null) {
			textFieldPassword.setText(configuration.getPassword());
			textFieldPort.setText(String.valueOf(configuration.getServerPort()));
		}
	}
	
	/**
	 * Save configuration to file.
	 */
	private final boolean save() {
		boolean saved = true;		
		Configuration configuration = ConfigurationManager.getInstance().getConfiguration();
		if (configuration != null) {
			configuration.setPassword(textFieldPassword.getText());
			int port = Integer.parseInt(textFieldPort.getText());
			if (!Utilities.checkPort(port)) {
				JOptionPane.showMessageDialog(this, 
						Translation.getInstance().getProperty(Category.FRAME_SETTINGS_PORT_SELECTED_ERROR));
				saved = false;
			}			
			
			configuration.setServerPort(port);
			if (SMS2Clipboard.API_SERVER != null && SMS2Clipboard.API_SERVER.getPort() != port) {
				try {
					SMS2Clipboard.API_SERVER.stopServer();
					SMS2Clipboard.API_SERVER.setPort(port);
					SMS2Clipboard.API_SERVER.startServer();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, 
							Translation.getInstance().getProperty(Category.FRAME_SETTINGS_PORT_SELECTED_ERROR));
				}
			}
		}
		ConfigurationManager.getInstance().saveToFile();

		return saved;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SettingsFrame.createAndShow();
	}
}
