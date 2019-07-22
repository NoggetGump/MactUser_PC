package menuBar;

import java.io.*;
import java.util.ArrayList;

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
	private ArrayList<CmdPair> cmds = new ArrayList<CmdPair>();
	private ArrayList<String> args = new ArrayList<String>();
	private CmdPair currentCmd = null;
	
	public Script(String name) throws IOException {
		script = new File(System.getProperty("user.dir") + File.separator + "scripts" + File.separator + name);
		
		if (script.createNewFile())	{
		    System.out.println("File is created!");
		} else {
		    System.out.println("File already exists.");
		}
	}
	
	public void editScript() throws IOException {
		DesktopApi.edit(script);
		//TODO subject to change criteria of empty or not empty
		isEmpty = false;
	}
	
	public void runScript() throws IOException {
		FileReader fileReader = new FileReader(script);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String word = null;
        char character = '\0';
        int line = 1;
		
        do {
        	character = (char) bufferedReader.read();
        	        	
        	switch(addChar2word(word, character)) {
        		case Consts.SUCCESS:
        			//...
        			break;
        		
        		case Consts.LINEBREAK:
            		judgeWord(word);
        			line++;
        			word = null;
        			break;
        			
        		case Consts.SPACE:
            		judgeWord(word);
            		word = null;
        			break;
        			
        		case Consts.ARGS_END:
        			judgeWord(word);
        			arg_flag = false;
        			cmd_flag = false;
        			currentCmd = null;
        			word = null;
        			
        		case Consts.END:
        			//...
        			break;
        			
        		case Consts.ERROR:
        			//TODO popup message ERROR - Script mistake at line @line
        			System.out.println("Mistake at line: " + line + "probably character \"!\" missing at the end of the script or the script is empty.");
        			break;
        			
        		default:
        			//...
        			break;
        	}
        } while(character != Consts.END);
		arg_flag = false;
		cmd_flag = false;
	}
	
	private short addChar2word(String word, char character) {
		if(character != ' ' && character != '\t' && character != '\n' && character != '\0') {
			word = word + character;
			return Consts.SUCCESS;
		}
		else if(character == '\n')
			return Consts.LINEBREAK;
		else if(character == '\t' || character == ' ')
			return Consts.SPACE;
		else if(character == ';')
			return Consts.ARGS_END;
		else if(character == '!')
			return Consts.END;
		else if (character == '\0')
			return Consts.ERROR;
		
		return Consts.ERROR;
	}

	private void judgeWord(String word) {
		if(word != null) {
			if(word.equals(Consts.CMD))
				cmd_flag = true;
			else if(word.equals(Consts.ARGS)) {
				if(!cmd_flag)
					arg_flag = true;
			}
			else if( cmd_flag == true && arg_flag == false) {
				CmdPair cmd = new CmdPair();
				
				cmd.cmd = word;
				cmd.nArgs = 0;
				cmds.add(cmd);
				currentCmd = cmd;
			}
			else if(cmd_flag == true && arg_flag == true) {
				args.add(word);
				currentCmd.nArgs++;
			}
		}
	}

	public boolean isEmpty() {
		return isEmpty;
	}
}