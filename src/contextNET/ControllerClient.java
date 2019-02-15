package contextNET;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;

public class ControllerClient implements NodeConnectionListener {

  private static String			gatewayIP   = "127.0.0.1";
  private static int			gatewayPort = 5500;
  private MrUdpNodeConnection	connection;

  public ControllerClient() {
      InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
      try {
          connection = new MrUdpNodeConnection();
          connection.addNodeConnectionListener(this);
          connection.connect(address);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  @Override
  public void connected(NodeConnection remoteCon) {
      ApplicationMessage message = new ApplicationMessage();
      message.setContentObject("connection test");

      try {
          connection.sendMessage(message);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  @Override
  public void newMessageReceived(NodeConnection remoteCon, Message message) {
      System.out.println(message.getContentObject());
  }
    
  // other methods

  @Override
  public void reconnected(NodeConnection remoteCon, SocketAddress endPoint, boolean wasHandover, boolean wasMandatory) {}

  @Override
  public void disconnected(NodeConnection remoteCon) {}

  @Override
  public void unsentMessages(NodeConnection remoteCon, List<Message> unsentMessages) {}

  @Override
  public void internalException(NodeConnection remoteCon, Exception e) {}
  
  public void sendCommand(String command) {
	  ApplicationMessage message = new ApplicationMessage();
	  
	  message.setContentObject(command);
	  try {
		connection.sendMessage(message);
	  } catch (IOException e) {
		e.printStackTrace();
	  }
  }
}