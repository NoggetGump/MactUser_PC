package contextNET;

import org.json.simple.JSONObject;

/**
 * Classe para Ajudar a construir comandos em formato JSON string.
 * Esses comandos podem ser enviados ao SOM (Smart Objects Manager)
 * @author sheriton
 *
 * Foram adicionadas constantes para auxiliar a parte do cliente 
 * e removidas as constantes desnecessarias para o mesmo
 * @author nog
 */
@SuppressWarnings("unchecked")
public class SOMCommandHelper_clientVersion {
	private static final String tag_value = "SOM";
	
	public static final String SOM_TAG = "SOM";
	
	public static final String cmd_insert_driver = "INSERT_DRIVER";
	
	public static final String cmd_insert_device = "INSERT_DEVICE";
	public static final String cmd_remove_device = "DELETE_DEVICE";
	public static final String cmd_get_device_driver = "GET_DEVICE_DRIVER";
	
	public static final String cmd_connect_device = "CONNECT_DEVICE";
	public static final String cmd_disconnect_device = "DISCONNECT_DEVICE";
	public static final String cmd_get_connected_devices = "GET_CONNECTED_DEVICES";
	
	public final static String atr_mac_address = "macAddress";
	public final static String atr_driver = "driver";
	
	public final static String atr_device_type = "deviceType";
	public final static String atr_device_model = "deviceModel";
	public final static String atr_device_vendor = "deviceVendor";
	
	public final static String atr_hub_uuid = "mobileHubId";
	
	public static final String atr_tag = "tag";
	public static final String atr_cmd = "cmd";
	
	//result constants
	
	public static final String atr_result = "result";
	
	public final static String driver_insert_success = "The driver has been successfully inserted";
	public final static String driver_insert_fail = "Failed to insert the driver with error: ";
	
	public final static String device_insert_success = "The device has been successfully inserted";
	public final static String device_insert_fail = "Failed to insert the device with error: ";
	public final static String device_delete_success = "The device has been successfully deleted";
	public final static String device_delete_fail = "Failed to delete the driver with error: ";
	
	public static JSONObject createInsertDriverCommand(String driver, String deviceType, String deviceModel, String deviceVendor) {
		JSONObject jsonCommand = new JSONObject();
		jsonCommand.put(atr_tag, tag_value);
		jsonCommand.put(atr_cmd, cmd_insert_driver);
		jsonCommand.put(atr_driver, driver);
		jsonCommand.put(atr_device_type, deviceType);
		jsonCommand.put(atr_device_model, deviceModel);
		jsonCommand.put(atr_device_vendor, deviceVendor);
		
		return jsonCommand;
	}
	
	public static JSONObject createInsertDeviceCommand(String deviceMacAddress, String deviceType, String deviceModel, String deviceVendor) {
		JSONObject jsonCommand = new JSONObject();
		jsonCommand.put(atr_tag, tag_value);
		jsonCommand.put(atr_cmd, cmd_insert_device);
		jsonCommand.put(atr_mac_address, deviceMacAddress);
		jsonCommand.put(atr_device_type, deviceType);
		jsonCommand.put(atr_device_model, deviceModel);
		jsonCommand.put(atr_device_vendor, deviceVendor);
		
		return jsonCommand;
	}
	
	public static JSONObject createDeleteDeviceCommand(String deviceMacAddress) {
		JSONObject jsonCommand = new JSONObject();
		jsonCommand.put(atr_tag, tag_value);
		jsonCommand.put(atr_cmd, cmd_remove_device);
		jsonCommand.put(atr_mac_address, deviceMacAddress);
		
		return jsonCommand;
	}

	public static JSONObject createGetConnectedDevicesCommand() {
		JSONObject jsonCommand = new JSONObject();
		jsonCommand.put(atr_tag, tag_value);
		jsonCommand.put(atr_cmd, cmd_get_connected_devices);
		
		return jsonCommand;
	}
}