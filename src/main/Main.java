/**
 *	Code delivered by: Felipe Nogueira de Souza
 *	Student of Control and Automation Engineering at
 *	Pontifícia Universidade Católica (PUC) - Rio de Janeiro.
 */

package main;

//import contextNET.ControllerClient;
import controlGUI.ControlPanel;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args){
//		ControllerClient client = new ControllerClient();
		ControlPanel cp = ControlPanel.getControlPanel(/*client*/);
	}
}