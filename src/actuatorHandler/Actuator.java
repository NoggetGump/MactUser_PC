package actuatorHandler;

import org.json.JSONStringer;

public class Actuator {
	String name;
	String type;
	String cfg;
	Integer seq;
	
	public Actuator(String name, String type) {
		this.name = name;
		this.type = type;
		this.seq = 0;
	}
	
	public void forwardMove() {
        JSONStringer js = new JSONStringer();
        
        js.object().key("MACTQuery").object().key("type").value("cmd").key("label").value("moveForward")
        	.key("device").value(name).key("cmds").array().object().key("seq").value(++seq).key("cmd")
        	.value("forward").endObject().endArray().endObject().endObject();
        System.out.println(js.toString());
	}
	
	public void rightMove() {
        JSONStringer js = new JSONStringer();
        
        js.object().key("MACTQuery").object().key("type").value("cmd").key("label").value("moveRight")
        	.key("device").value(name).key("cmds").array().object().key("seq").value(++seq).key("cmd")
        	.value("right").endObject().endArray().endObject().endObject();
        System.out.println(js.toString());
	}
	
	public void leftMove() {
        JSONStringer js = new JSONStringer();
        
        js.object().key("MACTQuery").object().key("type").value("cmd").key("label").value("moveLeft")
        	.key("device").value(name).key("cmds").array().object().key("seq").value(++seq).key("cmd")
        	.value("left").endObject().endArray().endObject().endObject();
        System.out.println(js.toString());
	}
	
	public void backwardMove() {
        JSONStringer js = new JSONStringer();
        
        js.object().key("MACTQuery").object().key("type").value("cmd").key("label").value("moveBackward")
        	.key("device").value(name).key("cmds").array().object().key("seq").value(++seq).key("cmd")
        	.value("backward").endObject().endArray().endObject().endObject();
        System.out.println(js.toString());
	}

	@Override
	public String toString() {
		return name + " (" + type + ")";
	}
}