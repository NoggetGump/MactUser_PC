package actuatorHandler;

import java.util.List;

public class Actuator {
	String type;
	String label;
	String device;
	List<String> cmds;
	
	public Actuator(String type, String label, String device, String cmd) {
		this.type = type;
		this.label = label;
		this.device = device;
		if(cmd != "")
			this.cmds.add(cmd);
	}
	
	public String toString() {
		return this.label;
	}
}