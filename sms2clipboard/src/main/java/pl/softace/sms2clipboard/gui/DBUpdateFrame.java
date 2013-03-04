package pl.softace.sms2clipboard.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pl.softace.sms2clipboard.SMS2Clipboard;
import pl.softace.sms2clipboard.config.ConfigurationManager;
import pl.softace.sms2clipboard.net.http.DBVersionInfo;
import pl.softace.sms2clipboard.net.http.ErrorCode;
import pl.softace.sms2clipboard.net.http.IDBClient;
import pl.softace.sms2clipboard.net.http.IDownloadListener;
import pl.softace.sms2clipboard.net.http.impl.S3DBClient;
import pl.softace.sms2clipboard.template.SMSTemplateManager;
import pl.softace.sms2clipboard.utils.Icon;

/**
 * 
 * Allows to update the template database.
 * 
 * @author lkawon@gmail.com
 *
 */
public class DBUpdateFrame extends JFrame implements IDownloadListener {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 2158347433231559828L;
	
	/**
	 * Regex used to set title label for new version.
	 */
	private static final String NEW_VERSION_TITLE_REGEX = "New version ${VERSION} published at ${DATE} is available.";
	
	/**
	 * Regex used to set title label for latest version.
	 */
	private static final String LATEST_VERSION_TITLE_REGEX = "You have the latest version ${VERSION} published at ${DATE}.";
	
	/**
	 * Version tag.
	 */
	private static final String VERSION_TAG = "${VERSION}";
	
	/**
	 * Date tag.
	 */
	private static final String DATE_TAG = "${DATE}";
	
	/**
	 * DB version to update.
	 */
	private DBVersionInfo versionInfo;
	
	/**
	 * Main content panel.
	 */
	private JPanel contentPane;
	
	/**
	 * Progress bar.
	 */
	private JProgressBar progressBar; 
	
	/**
	 * Update button.
	 */
	private JButton btnUpdate; 
	
	/**
	 * Title label.
	 */
	private JLabel titleLabel; 
	
	/**
	 * Text area for changes.
	 */
	private JTextArea changesTextArea;
	
	/**
	 * Flag for download indication.
	 */
	private boolean download;
	
	/**
	 * Used to access from anonymous classes;
	 */
	private DBUpdateFrame updateFrame = this;
	
	
	/**
	 * Checks new version at S3 and sets the window components.
	 */
	private final void getVersionInfo() {
		IDBClient client = new S3DBClient();
		versionInfo = client.getAvailableVersion();				
	}
	
	/**
	 * Create the frame.
	 */
	public DBUpdateFrame() {				
		setResizable(false);
		setTitle("Database update");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setBounds(100, 100, 450, 290);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		JPanel versionPanel = new JPanel();
		versionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(versionPanel);
		GridBagLayout gbl_versionPanel = new GridBagLayout();
		gbl_versionPanel.columnWidths = new int[]{0, 0};
		gbl_versionPanel.rowHeights = new int[]{14, 14, 150, 0, 0};
		gbl_versionPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_versionPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		versionPanel.setLayout(gbl_versionPanel);				
		
		titleLabel = new JLabel("Update service is temporairly unavailabel. Please try again later.");	
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.insets = new Insets(0, 0, 5, 0);
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.gridy = 0;
		versionPanel.add(titleLabel, gbc_titleLabel);
		
		JLabel lblChanges = new JLabel("Changes:");
		lblChanges.setHorizontalAlignment(SwingConstants.LEFT);
		lblChanges.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblChanges = new GridBagConstraints();
		gbc_lblChanges.anchor = GridBagConstraints.WEST;
		gbc_lblChanges.insets = new Insets(0, 0, 2, 0);
		gbc_lblChanges.gridx = 0;
		gbc_lblChanges.gridy = 1;
		versionPanel.add(lblChanges, gbc_lblChanges);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 0);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		versionPanel.add(scrollPane, gbc_scrollPane);
		
		changesTextArea = new JTextArea();
		changesTextArea.setEditable(false);
		scrollPane.setViewportView(changesTextArea);
		changesTextArea.setRows(1);
		changesTextArea.setLineWrap(true);
		changesTextArea.setFont(new Font("Arial", Font.PLAIN, 11));		
		
		JPanel updatePanel = new JPanel();
		updatePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(updatePanel);
		updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.PAGE_AXIS));
		
		progressBar = new JProgressBar();
		updatePanel.add(progressBar);
		updatePanel.add(Box.createRigidArea(new Dimension(10, 10)));
		
		btnUpdate = new JButton("Close");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnUpdate.getText().equals("Update")) {
					download = true;
					new Thread() {
						@Override
						public void run() {
							IDBClient client = new S3DBClient();
							client.downloadDatabase(versionInfo.getVersion(), updateFrame);
						}
					}.start();
					btnUpdate.setText("Cancel");
				} else if (btnUpdate.getText().equals("Cancel")) {
					download = false;
					btnUpdate.setText("Update");
				} else if (btnUpdate.getText().equals("Close")) {
					dispose();
				}
			}
		});
		btnUpdate.setAlignmentX(Component.CENTER_ALIGNMENT);
		updatePanel.add(btnUpdate);		
		
		
		setMinimumSize(new Dimension(450, 0));
		pack();
	}

	/**
	 * Checks the version at S3 and sets components texts.
	 */
	private final void checkVersion() {
		getVersionInfo();
		
		if (versionInfo != null) {
			if (!versionInfo.getVersion().equals(ConfigurationManager.getInstance().getConfiguration().getTemplatesDBVersion())) {			
				String titleLabelText = NEW_VERSION_TITLE_REGEX;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				titleLabelText = titleLabelText.replace(VERSION_TAG, versionInfo.getVersion());
				titleLabelText = titleLabelText.replace(DATE_TAG, sdf.format(versionInfo.getDate()));			
				titleLabel.setText(titleLabelText);
								
				changesTextArea.setText(versionInfo.getDescription());
				
				btnUpdate.setText("Update");
			} else {
				String titleLabelText = LATEST_VERSION_TITLE_REGEX;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				titleLabelText = titleLabelText.replace(VERSION_TAG, versionInfo.getVersion());
				titleLabelText = titleLabelText.replace(DATE_TAG, sdf.format(versionInfo.getDate()));			
				titleLabel.setText(titleLabelText);
				
				changesTextArea.setText(versionInfo.getDescription());
				
				btnUpdate.setText("Close");
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see pl.softace.sms2clipboard.net.http.IProgressListener#setProgress(int)
	 */
	@Override
	public final boolean updateProgress(int value) {
		if (progressBar != null) {
			progressBar.setValue(value);
		}
		
		return download;
	}
	
	/* (non-Javadoc)
	 * @see pl.softace.sms2clipboard.net.http.IDownloadListener#finish(pl.softace.sms2clipboard.net.http.ErrorCode)
	 */
	@Override
	public final void finished(ErrorCode errorCode) {
		if (errorCode.equals(ErrorCode.FINISHED)) {
			if (versionInfo != null) {
				if (SMSTemplateManager.getInstance().replaceVersion(versionInfo.getVersion())) {
					SMS2Clipboard.TRAY.setStandardIcon();
				}
			}
		}
		
		// check again to display status
		checkVersion();
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
					
					DBUpdateFrame frame = new DBUpdateFrame();					
					frame.setIconImages(Icon.getFrameIcons());
					frame.setResizable(false);				
					frame.setLocation((int) width / 2 - frame.getWidth() / 2, (int) height / 2 - frame.getHeight() / 2);
					
					frame.checkVersion();
					frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SMS2Clipboard.setUILookAndFeel();
		DBUpdateFrame.createAndShow();
	}
}
