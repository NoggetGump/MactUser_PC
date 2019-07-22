package menuBar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import actuatorHandler.Actuator;

public class RegisterOptionsPane {
	public static Actuator RegisterActuator() {
		JTextField Name = new JTextField(5);
		JTextField Device= new JTextField(5);
		String Type = "cmd";
		int result;
		
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
		myPanel.add(new JLabel("Name:"));
		myPanel.add(Name);
		myPanel.add(Box.createVerticalStrut(15)); // a spacer
		myPanel.add(new JLabel("Device:"));
		myPanel.add(Device);
		myPanel.add(Box.createVerticalStrut(15)); // a spacer
		
		
		result = JOptionPane.showConfirmDialog(null, myPanel,
				"Please Enter Name and Type", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION)
			return new Actuator(Name.getText(), Device.getText(), Type);
		
		return null;
   }
}