package contextNET;

import java.util.logging.Level;
import java.util.logging.Logger;

import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.serialization.Serialization;
import lac.cnet.sddl.objects.ApplicationObject;
import lac.cnet.sddl.objects.Message;
import lac.cnet.sddl.objects.PrivateMessage;
import lac.cnet.sddl.udi.core.SddlLayer;
import lac.cnet.sddl.udi.core.UniversalDDSLayerFactory;
import lac.cnet.sddl.udi.core.listener.UDIDataReaderListener;

public class testServer implements UDIDataReaderListener<ApplicationObject> {
  SddlLayer  core;
  int        counter;

  public static void main(String[] args) {
    Logger.getLogger("").setLevel(Level.OFF);

    new testServer();
  }

  public testServer() {
    core = UniversalDDSLayerFactory.getInstance();
    core.createParticipant(UniversalDDSLayerFactory.CNET_DOMAIN);

    core.createPublisher();
    core.createSubscriber();

    Object receiveMessageTopic = core.createTopic(Message.class, Message.class.getSimpleName());
    core.createDataReader(this, receiveMessageTopic);

    Object toMobileNodeTopic = core.createTopic(PrivateMessage.class, PrivateMessage.class.getSimpleName());
    core.createDataWriter(toMobileNodeTopic);

    counter = 0;
    System.out.println("=== Server Started (Listening) ===");
    synchronized (this) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onNewData(ApplicationObject topicSample) {
    Message message = (Message) topicSample;
    System.out.println(Serialization.fromJavaByteStream(message.getContent()));
/*
    PrivateMessage privateMessage = new PrivateMessage();
    privateMessage.setGatewayId(message.getGatewayId());
    privateMessage.setNodeId(message.getSenderId());
*/
    synchronized (core) {
      counter++;
    }
/*    
    ApplicationMessage appMsg = new ApplicationMessage();
    appMsg.setContentObject("Hello World!");
    privateMessage.setMessage(Serialization.toProtocolMessage(appMsg));

    core.writeTopic(PrivateMessage.class.getSimpleName(), privateMessage);
*/
  }
}