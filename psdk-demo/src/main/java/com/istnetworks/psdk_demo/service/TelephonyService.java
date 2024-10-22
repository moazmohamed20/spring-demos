package com.istnetworks.psdk_demo.service;

import org.springframework.stereotype.Service;

import com.genesyslab.platform.commons.connection.configuration.ClientADDPOptions;
import com.genesyslab.platform.commons.connection.configuration.PropertyConfiguration;
import com.genesyslab.platform.commons.protocol.ChannelState;
import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.commons.protocol.MessageHandler;
import com.genesyslab.platform.standby.WarmStandby;
import com.genesyslab.platform.standby.exceptions.WSException;
import com.genesyslab.platform.voice.protocol.TServerProtocol;
import com.istnetworks.psdk_demo.property.TServerProperties;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelephonyService {

  private final TServerProperties tServerProperties;

  private TServerProtocol tServerConnection;
  private WarmStandby warmStandby;

  @Setter
  private MessageHandler connectionMessageHandler;

  public void openConnection() throws InterruptedException, WSException {
    if (tServerConnection != null && tServerConnection.getState() != ChannelState.Closed)
      return;

    PropertyConfiguration tServerConfig = new PropertyConfiguration();
    tServerConfig.setUseAddp(true);
    tServerConfig.setAddpClientTimeout(300);
    tServerConfig.setAddpServerTimeout(400);
    tServerConfig.setAddpTraceMode(ClientADDPOptions.AddpTraceMode.Local);

    String clientName = tServerProperties.getClientName();
    String clientPassword = tServerProperties.getClientPassword();

    String primaryHost = tServerProperties.getPrimary().getHost().getHostAddress();
    int primaryPort = tServerProperties.getPrimary().getPort();

    String backupHost = tServerProperties.getBackup().getHost().getHostAddress();
    int backupPort = tServerProperties.getBackup().getPort();

    Endpoint primaryEndpoint = new Endpoint("Primary", primaryHost, primaryPort, tServerConfig);
    Endpoint backupEndpoint = new Endpoint("Backup", backupHost, backupPort, tServerConfig);

    tServerConnection = new TServerProtocol(primaryEndpoint);
    tServerConnection.setMessageHandler(connectionMessageHandler);
    tServerConnection.setClientName(clientName);
    tServerConnection.setClientPassword(clientPassword);

    warmStandby = new WarmStandby(tServerConnection, primaryEndpoint, backupEndpoint);
    warmStandby.autoRestore();
    warmStandby.open();

    log.info("T-SERVER Connection is opened");
  }

  public void releaseConnection() throws InterruptedException {
    if (warmStandby != null)
      warmStandby.close();
    log.info("T-SERVER Connection is released");
  }

}
