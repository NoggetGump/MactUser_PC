package menuBar;

import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import actuatorHandler.Actuator;

public class RegisterOptionsPane {
	public static Actuator RegisterActuator() {
		JTextField NameField = new JTextField(5);
		JTextField MACField = new JTextField(5);
		JTextField Type= new JTextField(5);
		int result;
		
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
		myPanel.add(new JLabel("Name:"));
		myPanel.add(NameField);
		myPanel.add(Box.createVerticalStrut(15)); // a spacer
		myPanel.add(new JLabel("Type:"));
		myPanel.add(MACField);
		myPanel.add(Box.createVerticalStrut(15)); // a spacer
		
		result = JOptionPane.showConfirmDialog(null, myPanel,
				"Please Enter Name and Type", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION)
			return new Actuator(NameField.getText(), MACField.getText());
		
		return null;
   }
}