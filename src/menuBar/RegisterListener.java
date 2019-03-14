package menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import actuatorHandler.Actuator;
import controlGUI.ControlPanel;

public class RegisterListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		Actuator act = RegisterOptionsPane.RegisterActuator();
		
		if(act != null)
			ControlPanel.CP.addActuator(act);
		
	}
}
