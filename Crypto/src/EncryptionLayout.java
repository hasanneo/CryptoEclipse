import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class EncryptionLayout implements ActionListener  {

	// JFrame
	static JFrame f;

	// JButton
	static JButton b;

	// label to display text
	static JLabel l;

	// text area
	static JTextArea jt;

	// default constructor
	EncryptionLayout() {

	}

	// if the button is pressed
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("submit")) {
			// set the text of the label to the text of the field
			l.setText(jt.getText());
		}
	}
}
