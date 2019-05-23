package menuBar;

import java.io.*;

public class Script {
	
	private boolean isEmpty;
	private File script;
	
	public Script(String name) throws IOException {
		isEmpty = true;
		script = new File(System.getProperty("user.dir") + File.separator + "scripts" + File.separator + name);
		
		if (script.createNewFile())	{
		    System.out.println("File is created!");
		} else {
		    System.out.println("File already exists.");
		}
	}
	
	public void editScript() throws IOException {
		DesktopApi.edit(script);
		
		isEmpty = false;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
}