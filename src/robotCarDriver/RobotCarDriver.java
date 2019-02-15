package robotCarDriver;

import java.util.List;
import com.google.gson.Gson;

public class RobotCarDriver {
	String type;
	String label;
	String device;
	
	public RobotCarDriver(String type, String label, String device) {
		this.type = type;
		this.label = label;
		this.device = device;
	}
	
	public String toString() {
		return this.label;
	}
}