package actuatorHandler;

import org.json.JSONStringer;

import controlGUI.ControlPanel;

public class Actuator {
	String name;
	String device;
	String type;
	Integer seq;
	
	public Actuator(String name, String device, String type) {
		this.name = name;
		this.device = device;
		this.type = type;
		this.seq = 0;
	}
	
	public void move(char heading) {
        JSONStringer js = new JSONStringer();
        
        System.out.println("\n heading: " + heading + "\n");
        
        js.object().key("MACTQuery").object().key("type").value("cmd").key("label").value("move")
        	.key("device").value(device).key("cmds").array().object().key("seq").value(seq++).key("cmd")
        	.value("move").key("args").object().key("heading").value(heading).endObject().endObject().endArray().endObject().endObject();
        System.out.println(js.toString());
        ControlPanel.CP.ctxnetClient.sendCommand(js.toString());
	}
	
	public void sendRGB(String R, String G, String B) {
		JSONStringer js = new JSONStringer();
		
        js.object().key("MACTQuery").object().key("type").value("cmd").key("label").value("setRGBColor")
    		.key("device").value(device).key("cmds").array().object().key("seq").value(seq++).key("cmd")
    		.value("setRGB").key("args").object().key("R").value(R).key("G").value(G).key("B").value(B).key("setDefault")
    		.value("0").endObject().endObject().endArray().endObject().endObject();
        System.out.println(js.toString());
        ControlPanel.CP.ctxnetClient.sendCommand(js.toString());
	}
	
	public void sendGeneriComand(String jsonComand)
	{
		System.out.println(jsonComand);
		ControlPanel.CP.ctxnetClient.sendCommand(jsonComand);
	}

	@Override
	public String toString() {
		return name + " (" + device + ")";
	}
}