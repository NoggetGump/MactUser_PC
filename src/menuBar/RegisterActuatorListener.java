package menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlGUI.ControlPanel;

public class RegisterActuatorListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
			JTextField mac = new JTextField(5);
			JTextField type = new JTextField(5);
			JTextField model = new JTextField(5);
			JTextField vendor = new JTextField(5);
			int result;
			
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
			myPanel.add(new JLabel("MAC address:"));
			myPanel.add(mac);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Type:"));
			myPanel.add(type);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Model:"));
			myPanel.add(model);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Vendor:"));
			myPanel.add(vendor);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			
			
			result = JOptionPane.showConfirmDialog(null, myPanel,
					"Please Enter All Data ", JOptionPane.OK_CANCEL_OPTION);
			
			if (result == JOptionPane.OK_OPTION) {
				ControlPanel.getContextnetClient().sendDevice2SOM(mac.getText(),
						type.getText(), model.getText(), vendor.getText());
				
				return;
			}
			
			JOptionPane.showMessageDialog(ControlPanel.getControlPanel().getMainContainer()
					, "User canceled actuator registration");
	}
}