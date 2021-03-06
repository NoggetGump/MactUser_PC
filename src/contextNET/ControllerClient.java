package contextNET;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import actuatorHandler.Actuator;
import controlGUI.ControlPanel;

import static contextNET.SOMCommandHelper_clientVersion.*;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;
import lac.cnclib.sddl.message.ClientLibProtocol.PayloadSerialization;
import menuBar.MyMenuBar;

public class ControllerClient implements NodeConnectionListener {
	
  public static final String cmd_time_test = "time_test";

  private static String			gatewayIP   = "127.0.0.1";
  private static int			gatewayPort = 5500;
  private MrUdpNodeConnection	connection;
  public UUID                	myUUID = UUID.fromString("eb9a5a6f-4d0b-4e96-bdea-7dff918c4024");

  private File file;
  private String dirPath = System.getProperty("user.dir") + File.separator + "Test_Run";
  private String filePath = dirPath + File.separator + "test_1.txt";
  
  private long startTime;
  int count = 0;
  int maxCount = 1000;
  int testScriptIndex = 1;
  boolean testFlag = false;
  
  public ControllerClient() {
	  //myUUID = UUID.randomUUID();

	File dir = new File(dirPath);
	if(!dir.exists())
		dir.mkdir();
	file = new File(filePath);
	
	try {
		file.createNewFile();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

      InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
      try {
          connection = new MrUdpNodeConnection();
          connection.addNodeConnectionListener(this);
          connection.connect(address);
          connection.setUuid(myUUID);
      } catch (IOException e) {
          e.printStackTrace();
      }
      
      refreshDevicesList();
  }

  @Override
  public void connected(NodeConnection remoteCon) {
  }

  @Override
  public void newMessageReceived(NodeConnection remoteCon, Message message) {
	  
	String content = (String) new String(message.getContent());
	
//	System.out.println("new message received, content = " + content);
	
	JSONParser parser = new JSONParser();
	
	try {
		JSONObject jsonObject = (JSONObject) parser.parse(content);
		String tag = (String) jsonObject.get(atr_tag);
		
		if(!tag.equals(SOM_TAG))
			return;
		else	
			handleSOMMessage(jsonObject);
	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
  
	private void handleSOMMessage(JSONObject jsonObject) {
		
		String command = (String) jsonObject.get(atr_cmd);

//		System.out.println("Recebeu o comando: " + command);

		switch (command) {
		
		case cmd_get_connected_devices:
			handleGetDevicesListCommand(jsonObject);
			break;
			
		case cmd_time_test:
			timeTest();
			break;
			
		default:
			break;
		}
	}

@Override
  public void reconnected(NodeConnection remoteCon, SocketAddress endPoint, boolean wasHandover, boolean wasMandatory) {}

  @Override
  public void disconnected(NodeConnection remoteCon) {}

  @Override
  public void unsentMessages(NodeConnection remoteCon, List<Message> unsentMessages) {}

  @Override
  public void internalException(NodeConnection remoteCon, Exception e) {}
  
  /**
   * Handle the income of new mobile hubs and connected devices
   * 
   * @author nog
   */
    
  private void handleGetDevicesListCommand(JSONObject jsonObject) {
		JSONArray result = (JSONArray) jsonObject.get(atr_result);
		
		for(Object obj : result) {
			JSONParser parser = new JSONParser();
			JSONObject mobileAct = null;
			try {
				mobileAct = (JSONObject) parser.parse((String) obj);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			String hub = (String) mobileAct.get("hub");
			String name = (String) mobileAct.get("name");
			
			Actuator act = new Actuator(name);
			
			ControlPanel.getControlPanel().add2Lists(act, hub);
		}
}
  
	private void timeTest() {
		long endTime = System.nanoTime();
		long deltaTime = endTime - startTime;
		
		System.out.println("Tempo >> " + deltaTime);
		
         try {
             BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));    
             bw.write(String.valueOf(deltaTime) + "\n");
             bw.close();
         } catch(Exception e){
            	System.out.println(e);
           }
         
         if(testFlag && (count < maxCount)) {
             try {
     			TimeUnit.SECONDS.sleep(2);
     		 } catch (InterruptedException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		   }
 //       	 System.out.println("Reenvio de mensagem para testes");
        	 startTime = System.nanoTime();
        	 try {
	     			MyMenuBar.scripts.get(testScriptIndex - 1).runScript();
	     		} catch (NumberFormatException e1) {
	     			// TODO Auto-generated catch block
	     			e1.printStackTrace();
	     		} catch (IOException e1) {
	     			// TODO Auto-generated catch block
	     			e1.printStackTrace();
	     		}
         }

		return;
	}

/**
   * Refreshes Devices List - via requesting the info
   * from SOM (server)
   * 
   * @author sheriton and nog
   */
  
  public void refreshDevicesList() {
      JSONObject jsonObject = new JSONObject();
      
      jsonObject = SOMCommandHelper_clientVersion.createGetConnectedDevicesCommand();
      
      ApplicationMessage requestMsg = new ApplicationMessage();
      requestMsg.setPayloadType(PayloadSerialization.JSON);
      requestMsg.setContentObject(jsonObject.toJSONString());
      
      sendMessage(requestMsg);
  }
  
  /**
   * Cuida do envio do driver para o SOM
   * 
   * @param driver
   * @param deviceType
   * @param deviceModel
   * @param deviceVendor
   * @return
   * 
   * @author sheriton and nog
   */
  
  public void sendDriver2SOM(String driver, String deviceType, String deviceModel, String deviceVendor) {
	  JSONObject jsonObject = createInsertDriverCommand(driver, deviceType, deviceModel, deviceVendor);
	  
      ApplicationMessage requestMsg = new ApplicationMessage();
      requestMsg.setPayloadType(PayloadSerialization.JSON);
      requestMsg.setContentObject(jsonObject.toJSONString());
      
      if(requestMsg.getContentObject() != null) {
    	sendMessage(requestMsg);
      	return;
      }
      
      System.out.println("Nullpointer exception - sendDriverMsg is null");
  }
  
  /**
   * Cuida do envio do device para o SOM
   * 
   * @param driver
   * @param deviceType
   * @param deviceModel
   * @param deviceVendor
   * @return
   * 
   * @author sheriton and nog
   */
  
  public void sendDevice2SOM(String macAddress, String deviceType, String deviceModel, String deviceVendor) {
	  JSONObject jsonObject = createInsertDeviceCommand(macAddress, deviceType, deviceModel, deviceVendor);
	  
      ApplicationMessage requestMsg = new ApplicationMessage();
      requestMsg.setPayloadType(PayloadSerialization.JSON);
      requestMsg.setContentObject(jsonObject.toJSONString());
      
      if(requestMsg.getContentObject() != null) {
    	sendMessage(requestMsg);
      	return;
      }
     
      System.out.println("Nullpointer exception - sendDriverMsg is null");
  }
  
  /**
   * Send a command to a specific mobile hub - to handle the actuation.
   * 
   * @param actuationCommand
   */
  
  public void sendCommand(String actuationCommand, String mHubUUID) {
	  ApplicationMessage message = new ApplicationMessage();
	  
	  message.setContentObject(actuationCommand);
	  message.setRecipientID(UUID.fromString(mHubUUID));
	  try {
//		System.out.println("COMMAND \"" + actuationCommand + "\" SENT");
		if(testFlag)
			startTime = System.nanoTime();
		connection.sendMessage(message);
	  } catch (IOException e) {
		e.printStackTrace();
	  }
  }
  
  /**
   * Send a message to the ConextNet SOM server.
   * 
   * @param message
   */
  public void sendMessage(Message SOMCommandMessage) {
	  SOMCommandMessage.setSenderID(myUUID);
	  try {
//		System.out.println("COMMAND \"" + SOMCommandMessage.getContentObject().toString() + "\" SENT");
		connection.sendMessage(SOMCommandMessage);
	  } catch (IOException e) {
		e.printStackTrace();
	  }
  }
}