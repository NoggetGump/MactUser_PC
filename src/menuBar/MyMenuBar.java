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
	private JMenu drivers_menu;
	private JMenu run_script;
	private JMenu edit_script;
	private JMenuItem register_driver;
	private JMenuItem add_actuator;
	private JMenuItem config_actuator;
	static ArrayList<Script> scripts;
	
	public MyMenuBar() {
		
		super();
		
		//ACTUATORS MENU
		actuators_menu = new JMenu("Actuators");
		actuators_menu.setMnemonic(KeyEvent.VK_A);
		this.add(actuators_menu);
		
		//DRIVERS MENU
		drivers_menu = new JMenu("Drivers");
		drivers_menu.setMnemonic(KeyEvent.VK_D);
		this.add(drivers_menu);
		
		//SCRIPTS MENU
		scripts_menu = new JMenu("Scripts");
		scripts_menu.setMnemonic(KeyEvent.VK_S);
		this.add(scripts_menu);		
		
		//ACTUATORS MENU ITENS
		
			//REGISTER ACTUATOR ITEM
		add_actuator = new JMenuItem("Register", KeyEvent.VK_R);
		add_actuator.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_R, ActionEvent.ALT_MASK));
		add_actuator.addActionListener(new RegisterActuatorListener());
		actuators_menu.add(add_actuator);
		
			//CONFIG ACTUATOR ITEM
		config_actuator = new JMenuItem("Config");
		config_actuator.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_C, ActionEvent.ALT_MASK));
		actuators_menu.add(config_actuator);
		
		//DRIVERS MENU
		register_driver = new JMenuItem("Upload");
		register_driver.addActionListener(new RegisterDriverListener());
		drivers_menu.add(register_driver);
		
		//SCRIPTS MENU

		//RUN SCRIPTS SUBMENU
		run_script = new JMenu("Run");
		scripts_menu.add(run_script);
		
		//EDIT SCRIPTS SUBMENU
		edit_script = new JMenu("Edit");
		scripts_menu.add(edit_script);
		
		scripts = new ArrayList<Script>(); //DECLARING LIST OF SCRIPTS
		
		int key = 112; //key value (from F1 to F5)
		
			//RUN SCRIPTS ADDING ITENS
		for(Integer i = 1; i<6 ; i++, key++) {
			JMenuItem script = new JMenuItem("Script " + i.toString(),
					key);
			try {
				scripts.add(new Script("script_" + i.toString() + ".txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			script.addActionListener(new RunScriptListener());
			run_script.add(script);
		}
		
			//EDIT SCRIPTS ADDING ITENS
		for(Integer i = 1; i<6 ; i++) {
			JMenuItem script = new JMenuItem("Script " + i.toString());
			script.addActionListener(new EditScriptListener());
			edit_script.add(script);
		}
	}	
}