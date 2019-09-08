/**
 *	Code delivered by: Felipe Nogueira de Souza (Nog)
 *	Student of Control and Automation Engineering at
 *	Pontifícia Universidade Católica (PUC) - Rio de Janeiro.
 */

package main;

import java.io.IOException;

import javax.swing.JOptionPane;
import controlGUI.ControlPanel;


public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException{
		int result = JOptionPane.OK_OPTION;
/*		JPanel startingPanel = new JPanel();
		JTextField mHubUUID = new JTextField(10);		
		
		startingPanel.setLayout(new BoxLayout(startingPanel, BoxLayout.PAGE_AXIS));
		startingPanel.add(new JLabel("UUID:"));
		startingPanel.add(mHubUUID);
		result = JOptionPane.showConfirmDialog(null, startingPanel,
				"Please Enter UUID From Mobile Hub", JOptionPane.OK_CANCEL_OPTION);*/
		if (result == JOptionPane.OK_OPTION) {
			ControlPanel.getControlPanel();
			ControlPanel.getContextnetClient();
		}
	}
}