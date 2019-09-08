package actuatorHandler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import controlGUI.ControlPanel;

public class Actuator {
	String informalName;
	String deviceName;
	String type = "cmd";
	Integer seq = 0;
	
	public Actuator(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public void addInformalName(String informalName) {
		this.informalName = informalName;
	}
	
	@SuppressWarnings("unchecked")
	public void move(char heading) {
		JSONObject jsonObject = new JSONObject();
		JSONObject query = new JSONObject();
		JSONArray commands = new JSONArray();
		JSONObject command = new JSONObject();
		JSONObject args = new JSONObject();
		
		jsonObject.put("type", type);
		jsonObject.put("label", "move");
		jsonObject.put("device", deviceName);
		
		command.put("seq", seq++);
		command.put("cmd", "move");
		
		args.put("heading", heading);
		
		command.put("args", args.toJSONString());
		
		commands.add(command);
		
		jsonObject.put("cmds", commands);
		
		query.put("MACTQuery", jsonObject);
		
        ControlPanel.getContextnetClient().sendCommand(query.toJSONString(),
				(String) ControlPanel.getControlPanel().getSelectedMhub());
	}
	
	@SuppressWarnings("unchecked")
	public void sendRGB(String R, String G, String B){
		JSONObject jsonObject = new JSONObject();
		JSONObject query = new JSONObject();
		JSONArray commands = new JSONArray();
		JSONObject command = new JSONObject();
		JSONObject args = new JSONObject();
		
		jsonObject.put("type", type);
		jsonObject.put("label", "move");
		jsonObject.put("device", deviceName);
		
		command.put("seq", seq++);
		command.put("cmd", "move");
		
		args.put("R", R);
		args.put("G", G);
		args.put("B", B);
		
		command.put("args", args.toJSONString());
		
		commands.add(command);
		
		jsonObject.put("cmds", commands);
		
		query.put("MACTQuery", jsonObject);
		
        ControlPanel.getContextnetClient().sendCommand(query.toJSONString(),
				(String) ControlPanel.getControlPanel().getSelectedMhub());
	}
	
	public void sendCustoMactQuery(String query) {
		System.out.println(query);
		ControlPanel.getContextnetClient().sendCommand(query, 
				(String) ControlPanel.getControlPanel().getSelectedMhub());
	}

	@Override
	public String toString() {
		return deviceName;
	}
}