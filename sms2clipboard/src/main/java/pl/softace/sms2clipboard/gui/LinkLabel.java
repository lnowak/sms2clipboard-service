package pl.softace.sms2clipboard.gui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

/**
 * 
 * Label with link.
 * 
 * @author lkawon@gmail.com
 *
 */
public class LinkLabel extends JLabel {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 6162229725788996227L;

	/**
	 * Text for the label.
	 */
	private String text;
	
	
	/**
	 * Constructor.
	 * 
	 * @param text		label text
	 */
	public LinkLabel(String text) {		
		super(text);
		this.text = text;
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		enableEvents(MouseEvent.MOUSE_EVENT_MASK);
	}
	
	/**
	 * Returns the text set by the user.
	 */
	public String getNormalText(){
		return text;
	}
	
	/**
	 * Processes mouse events and responds to clicks.
	 */
	protected void processMouseEvent(MouseEvent evt) {
		super.processMouseEvent(evt);
		if (evt.getID() == MouseEvent.MOUSE_CLICKED)
			fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getNormalText()));
	}

	/**
	 * Adds an ActionListener to the list of listeners receiving notifications
	 * when the label is clicked.
	 */
	public void addActionListener(ActionListener listener) {
		listenerList.add(ActionListener.class, listener);
	}

	/**
	 * Removes the given ActionListener from the list of listeners receiving
	 * notifications when the label is clicked.
	 */
	public void removeActionListener(ActionListener listener) {
		listenerList.remove(ActionListener.class, listener);
	}

	/**
	 * Fires an ActionEvent to all interested listeners.
	 */
	protected void fireActionPerformed(ActionEvent evt) {
		Object [] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == ActionListener.class) {
				ActionListener listener = (ActionListener)listeners[i+1];
				listener.actionPerformed(evt);
			}
		}
	}
}
