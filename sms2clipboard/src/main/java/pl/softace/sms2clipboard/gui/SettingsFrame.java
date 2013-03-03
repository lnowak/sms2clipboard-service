package pl.softace.sms2clipboard.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import pl.softace.sms2clipboard.config.Configuration;
import pl.softace.sms2clipboard.config.ConfigurationManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	 * Icon.
	 */
	private static final String ICON = "images/icon.png";
	
	private JPanel contentPane;
	private JTextField textFieldPassword;
	private JTextField textFieldUpdateDelay;

	/**
	 * Create the frame.
	 */
	public SettingsFrame() {
		setResizable(false);
		setTitle("Settings");
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
		
		JLabel lblPassword = new JLabel("Password:");
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
		
		JLabel lblUpdateDelay = new JLabel("Update delay:");
		GridBagConstraints gbc_lblUpdateDelay = new GridBagConstraints();
		gbc_lblUpdateDelay.anchor = GridBagConstraints.EAST;
		gbc_lblUpdateDelay.insets = new Insets(0, 0, 0, 5);
		gbc_lblUpdateDelay.gridx = 0;
		gbc_lblUpdateDelay.gridy = 1;
		settingsPanel.add(lblUpdateDelay, gbc_lblUpdateDelay);
		
		textFieldUpdateDelay = new JTextField();
		GridBagConstraints gbc_textFieldUpdateDelay = new GridBagConstraints();
		gbc_textFieldUpdateDelay.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUpdateDelay.gridx = 1;
		gbc_textFieldUpdateDelay.gridy = 1;
		settingsPanel.add(textFieldUpdateDelay, gbc_textFieldUpdateDelay);
		textFieldUpdateDelay.setColumns(10);
		
		JPanel buttonsPanel = new JPanel();
		contentPane.add(buttonsPanel);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			    dispose();
			}
		});
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
				setVisible(false);
			    dispose();
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
					frame.setIconImage(new ImageIcon(ICON).getImage());
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
			textFieldUpdateDelay.setText(String.valueOf(configuration.getUpdateDelay()));
		}
	}
	
	/**
	 * Save configuration to file.
	 */
	private final void save() {
		Configuration configuration = ConfigurationManager.getInstance().getConfiguration();
		if (configuration != null) {
			configuration.setPassword(textFieldPassword.getText());
			configuration.setUpdateDelay(Long.parseLong(textFieldUpdateDelay.getText()));
		}
		ConfigurationManager.getInstance().saveToFile();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SettingsFrame.createAndShow();
	}
}
