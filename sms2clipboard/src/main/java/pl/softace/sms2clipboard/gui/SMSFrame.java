package pl.softace.sms2clipboard.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pl.softace.sms2clipboard.template.SMSTemplate;

/**
 * 
 * JFrame that is displayed when SMS was received.
 * 
 * @author lkawon@gmail.com
 *
 */
public class SMSFrame extends JFrame {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -808936437971092539L;

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
	 * Title of the label.
	 */
	private static final String LABEL_TITLE = "Received SMS:";	

	/**
	 * SMS template.
	 */
	private SMSTemplate smsTemplate;
	
	/**
	 * SMS text.
	 */
	private String smsText;
	
	/**
	 * Constructor.
	 */
	public SMSFrame(SMSTemplate smsTemplate, String smsText) {
		this.smsTemplate = smsTemplate;
		this.smsText = smsText;
		
		setTitle("SMS2Clipboard");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// label and text
		JPanel smsPanel = new JPanel();
		smsPanel.setLayout(new BoxLayout(smsPanel, BoxLayout.PAGE_AXIS));		
		JLabel titleLabel = new JLabel(LABEL_TITLE);		
		smsPanel.add(titleLabel);		

		smsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		StringBuilder text = new StringBuilder();
		text.append(smsTemplate.getSMSPrefix(smsText));
		text.append(linkIfy(smsTemplate.getSMSPassword(smsText)));
		text.append(smsTemplate.getSMSSuffix(smsText));
		
		JLabel smsLabel = new JLabel(htmlIfy(text.toString()));		
		smsPanel.add(smsLabel);
		smsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		JButton copyButton = new JButton("Copy password");
		copyButton.addActionListener(new ActionListener() {
			
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				SMSFrame smsJFrame = (SMSFrame) SwingUtilities.getRoot(button);
				
				String password = smsJFrame.getSmsTemplate().getSMSPassword(smsJFrame.getSmsText());
				StringSelection stringSelection = new StringSelection(password);
			    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents(stringSelection, null);		    		    
			    		    
			    smsJFrame.setVisible(false);
			    smsJFrame.dispose();	
			}
		});
    
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(copyButton);	
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		Container contentPane = getContentPane();
		contentPane.add(smsPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.PAGE_END);		
		
		pack();
	}
	
	public final SMSTemplate getSmsTemplate() {
		return smsTemplate;
	}

	public final void setSmsTemplate(SMSTemplate smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public final String getSmsText() {
		return smsText;
	}

	public final void setSmsText(String smsText) {
		this.smsText = smsText;
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
	 * 
	 * @param smsTemplate		SMS template
	 * @param smsText			SMS text
	 */
	public static final void createAndShow(final SMSTemplate smsTemplate, final String smsText) {			
		SwingUtilities.invokeLater(new Runnable() {

			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {								
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				double width = screenSize.getWidth();
				double height = screenSize.getHeight();
				
				SMSFrame smsJFrame = new SMSFrame(smsTemplate, smsText);
				smsJFrame.setIconImage(new ImageIcon(ICON).getImage());				
				smsJFrame.setResizable(false);
				smsJFrame.setSize(400, 145);
				smsJFrame.setLocation((int) width / 2 - smsJFrame.getWidth() / 2, (int) height / 2 - smsJFrame.getHeight() / 2);				
				smsJFrame.setVisible(true);
			}
		});
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SMSTemplate smsTemplate = new SMSTemplate();
		smsTemplate.setSource("3388");
		smsTemplate.setSmsRegex("Operacja nr \\d+ z dn. [\\d-]+ mTransfer z rach.: ...\\d+ na rach.: \\d+...\\d+ kwota: [\\d,]+ PLN haslo: ${PASSWORD} mBank.");
		smsTemplate.setPasswordRegex("\\\\d+");
		
		String smsText = "Operacja nr 1 z dn. 17-02-2013 mTransfer z rach.: ...55060959 na rach.: 8111...746759 kwota: 60,00 PLN haslo: 98066528 mBank.";
		
		SMSFrame.createAndShow(smsTemplate, smsText);
	}
}