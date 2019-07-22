package menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class MyMenuBar extends JMenuBar {
	private JMenu actuators_menu;
	private JMenu scripts_menu;
	private JMenu run_script;
	private JMenu edit_script;
	private JMenuItem add_actuator;
	private JMenuItem config_actuator;
	static ArrayList<Script> scripts;
	
	public MyMenuBar() throws IOException {
		super();
		//ACTUATORS MENU
		actuators_menu = new JMenu("Actuators");
		actuators_menu.setMnemonic(KeyEvent.VK_A);
		this.add(actuators_menu);
		//SCRIPTS MENU
		scripts_menu = new JMenu("Scripts");
		scripts_menu.setMnemonic(KeyEvent.VK_S);
		this.add(scripts_menu);
		//ACTUATORS MENU ITENS
		add_actuator = new JMenuItem("Register", KeyEvent.VK_R);
		add_actuator.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_R, ActionEvent.ALT_MASK));
		add_actuator.addActionListener(new RegisterListener());
		actuators_menu.add(add_actuator);
		
		config_actuator = new JMenuItem("Config");
		config_actuator.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_C, ActionEvent.ALT_MASK));
		actuators_menu.add(config_actuator);
		
		//SCRIPTS MENU ITENS
		run_script = new JMenu("Run");
		scripts_menu.add(run_script);
		
		edit_script = new JMenu("Edit");
		scripts_menu.add(edit_script);
		
		//INITIALIZING LIST OF SCRIPTS
		scripts = new ArrayList<Script>();
		
		//RUN SCRIPTS SUBMENU
		int key = 112; //key value (from F1 to F5)
		for(Integer i = 1; i<6 ; i++, key++) {
			JMenuItem script = new JMenuItem("Script " + i.toString(),
					key);
			scripts.add(new Script("/script_" + i.toString() + ".txt"));
			script.addActionListener(new EditScriptListener());
			run_script.add(script);
		}
		
		//EDIT SCRIPTS SUBMENU
		for(Integer i = 1; i<6 ; i++) {
			JMenuItem script = new JMenuItem("Script " + i.toString());
			script.addActionListener(new EditScriptListener());
			edit_script.add(script);
		}
	}	
}