package menuBar;

import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import actuatorHandler.Actuator;
import controlGUI.ControlPanel;
import menuBar.Consts;

class CmdPair{
	  String cmd;
	  short nArgs;
	}

public class Script {
	
	private File script;
	private boolean isEmpty = true;;
	private boolean cmd_flag = false;
	private boolean arg_flag = false;
	private boolean value_flag = false;
	private ArrayList<CmdPair> cmds = new ArrayList<CmdPair>();
	private ArrayList<String> args = new ArrayList<String>();
	private ArrayList<String> values = new ArrayList<String>();
	private CmdPair currentCmd = null;
	private final static int FIRST = 0;
	
	public Script(String name) throws IOException{
		File dir = new File(System.getProperty("user.dir") + File.separator +"scripts");
		if(!dir.exists())
			dir.mkdir();
		script = new File(System.getProperty("user.dir") + File.separator + "scripts" + File.separator + name);
		
		if (script.createNewFile())	{
		    System.out.println("File is created!");
		} else {
		    System.out.println("File already exists.");
		}
	}
	
	public void editScript() throws IOException {
		DesktopApi.edit(script);
		//TODO subject to change
		isEmpty = false;
	}
	
	public void runScript() throws IOException {
		FileReader fis = new FileReader(script);
        StringBuilder word = new StringBuilder(new String());
        String query;
        int  character = 0;
        int line = 1;
        
		Actuator actuator = ControlPanel.getControlPanel().getSelectedSmarThing();
		
		if(actuator != null) {
	        while(character != Consts.END && ((character = fis.read()) != -1)) {	        	        	
	        	switch(character) {
	        		case Consts.LINEBREAK:
	            		judgeWord(word.toString());
	        			line++;
	        			word.setLength(0);
	        			break;
	        			
	        		case Consts.SPACE:
	            		judgeWord(word.toString());
	            		word.setLength(0);
	        			break;
	        			
	        		case Consts.TAB:
	            		judgeWord(word.toString());
	            		word.setLength(0);
	        			break;
	        			
	        		case Consts.ARGS_END:
	        			judgeWord(word.toString());
	        			cmd_flag = false;
	        			arg_flag = false;
	        			currentCmd = null;
	        			word.setLength(0);
	        			break;
	        			
	        		case Consts.END:
	        			judgeWord(word.toString());
	        			break;
	        		
	        		case Consts.ERROR:
	        			//TODO popup message ERROR - Script mistake at line @line
	        			System.out.println("Mistake at line: " + line);
	        			break; 
	        			
	        		default:
	        			word.append((char)character);
	        			break;
	        	}
	        }
			arg_flag = false;
			cmd_flag = false;
			word.setLength(0);
			currentCmd = null;
			fis.close();
			
			query = generateMactQuery (actuator.toString(), "some label");
			
			actuator.sendCustoMactQuery(query);
		}
		else { 
			JOptionPane.showMessageDialog(ControlPanel.getControlPanel().getMainContainer()
					, "First select an actuator...");
		}
	}

	private void judgeWord(String word) {
		if(!word.isEmpty()) {
			if(word.equals(Consts.CMD))
				cmd_flag = true;
			else if(cmd_flag == true && word.equals(Consts.ARGS)) {
				arg_flag = true;
			}
			else if( cmd_flag == true && arg_flag == false) {
				CmdPair cmd = new CmdPair();
				
				cmd.cmd = word;
				cmd.nArgs = 0;
				cmds.add(cmd);
				currentCmd = cmd;
			}
			else if(cmd_flag == true && arg_flag == true && value_flag == false) {
				args.add(word);
				currentCmd.nArgs++;
				value_flag = true;
			}
			else if(value_flag == true){
				values.add(word);
				value_flag = false;
			}
		}
	}
	
	private String generateMactQuery (String deviceName, String deviceLabel) { 
		StringBuilder mactQuery = new StringBuilder(new String());
		
		mactQuery.append("[ {\n"//open Json curly brackets
				+ "\t\"MACTQuery\" :\n"
				+ "\t{\n" //open MACTQuery curly brackets
				+ "\t\t\"type\" : \"cmd\",\n"
				+ "\t\t\"label\" : \"someLabel\",\n"
				+ "\t\t\"device\" : \"" + deviceName + "\"" + ",\n"
				+ "\t\t\"id\" : " + ControlPanel.getContextnetClient().myUUID + ",\n"
				+ "\t\t\"cmds\" : [\n"); //open brackets to cmds
		while(!cmds.isEmpty()) {
			CmdPair cmd = cmds.remove(FIRST);
			
			mactQuery.append("\t\t{\n" //opens curly brackets to command
					+ "\t\t\t\"cmd\" : \"" + cmd.cmd + "\",\n"
					+ "\t\t\t\"args\" : { "); //open curly brackets to args
			while(cmd.nArgs != 0) {
				cmd.nArgs--;
				mactQuery.append("\"" + args.remove(FIRST) + "\" : "
						+ values.remove(FIRST));
				if(cmd.nArgs != 0)
					mactQuery.append(", ");
			}
			mactQuery.append(" }\n" //close curly brackets to args
					+ "\t\t}"); //close curly brackets to command
			if(!cmds.isEmpty())
				mactQuery.append(", ");
		}
		
		mactQuery.append("]\n" //close brackets to cmds
				+ "\t}\n" //close MACTQuery curly brackets
				+ "} ]"); //close Json curly brackets
		
		return mactQuery.toString();
	}

	public boolean isEmpty() {
		return isEmpty;
	}
}