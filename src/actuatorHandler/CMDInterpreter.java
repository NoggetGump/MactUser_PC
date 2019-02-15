package actuatorHandler;

import com.google.gson.Gson;

public class CMDInterpreter {
	
	Gson jsonQuery = null;
	String jsonMsg = null;

	/**
	 * Jsonify the parameters into the proper format
	 * and interpret the type to obtain the right driver.
	 * @param Actuator act
	 */
	public void interpret(Actuator act) {
		 jsonMsg = jsonQuery.toJson(act);
	}
}