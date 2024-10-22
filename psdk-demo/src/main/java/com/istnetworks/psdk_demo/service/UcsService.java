package com.istnetworks.psdk_demo.service;

import org.springframework.stereotype.Service;

import com.genesyslab.platform.commons.connection.configuration.ClientADDPOptions;
import com.genesyslab.platform.commons.connection.configuration.PropertyConfiguration;
import com.genesyslab.platform.commons.protocol.ChannelState;
import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.commons.protocol.Message;
import com.genesyslab.platform.commons.protocol.MessageHandler;
import com.genesyslab.platform.commons.protocol.ProtocolException;
import com.genesyslab.platform.contacts.protocol.UniversalContactServerProtocol;
import com.genesyslab.platform.contacts.protocol.contactserver.events.EventError;
import com.genesyslab.platform.standby.WarmStandby;
import com.genesyslab.platform.standby.exceptions.WSException;
import com.istnetworks.psdk_demo.property.UcsProperties;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UcsService {

  private final UcsProperties ucsProperties;

  private UniversalContactServerProtocol ucsConnection;
  private WarmStandby warmStandby;

  @Setter
  private MessageHandler connectionMessageHandler;

  public void openConnection() throws WSException, InterruptedException {
    if (ucsConnection != null && ucsConnection.getState() != ChannelState.Closed)
      return;

    PropertyConfiguration ucsConfig = new PropertyConfiguration();
    ucsConfig.setUseAddp(true);
    ucsConfig.setAddpServerTimeout(60);
    ucsConfig.setAddpClientTimeout(60);
    ucsConfig.setAddpTraceMode(ClientADDPOptions.AddpTraceMode.Both);

    int port = ucsProperties.getPrimary().getPort();
    String backupHost = ucsProperties.getBackup().getHost().getHostAddress();
    String primaryHost = ucsProperties.getPrimary().getHost().getHostAddress();

    Endpoint backupEndpoint = new Endpoint("Backup", backupHost, port, ucsConfig);
    Endpoint primaryEndpoint = new Endpoint("Primary", primaryHost, port, ucsConfig);

    ucsConnection = new UniversalContactServerProtocol(primaryEndpoint);
    ucsConnection.setMessageHandler(connectionMessageHandler);
    warmStandby = new WarmStandby(ucsConnection, primaryEndpoint, backupEndpoint);
    warmStandby.autoRestore();
    warmStandby.open();

    log.info("UCS Connection is opened");
  }

  public void releaseConnection() throws InterruptedException {
    if (warmStandby != null)
      warmStandby.close();
    log.info("UCS Connection is released");
  }

  private <T extends Message> T getEvent(Message request) throws RuntimeException {
    Message message;

    try {
      message = ucsConnection.request(request);
    } catch (ProtocolException e) {
      throw new RuntimeException(e);
    }

    if (message instanceof EventError) {
      EventError ee = (EventError) message;
      throw new RuntimeException(ee.getFaultString());
    }

    if (message == null) {
      throw new RuntimeException("No response from UCS, skipping this task");
    }

    return (T) message;
  }

}