package com.istnetworks.psdk_demo.service;

import org.springframework.stereotype.Service;

import com.genesyslab.platform.applicationblocks.com.ConfService;
import com.genesyslab.platform.applicationblocks.com.ConfServiceFactory;
import com.genesyslab.platform.commons.connection.configuration.ClientADDPOptions;
import com.genesyslab.platform.commons.connection.configuration.PropertyConfiguration;
import com.genesyslab.platform.commons.protocol.ChannelState;
import com.genesyslab.platform.commons.protocol.Endpoint;
import com.genesyslab.platform.commons.protocol.MessageHandler;
import com.genesyslab.platform.configuration.protocol.ConfServerProtocol;
import com.genesyslab.platform.configuration.protocol.types.CfgAppType;
import com.genesyslab.platform.standby.WarmStandby;
import com.genesyslab.platform.standby.exceptions.WSException;
import com.istnetworks.psdk_demo.property.CfgProperties;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CfgService {

    private final CfgProperties cfgProperties;

    private ConfServerProtocol cfgConnection;
    private ConfService confService;
    private WarmStandby warmStandby;

    @Setter
    private MessageHandler connectionMessageHandler;

    public void openConnection() throws InterruptedException, WSException {
        if (cfgConnection != null && cfgConnection.getState() != ChannelState.Closed)
            return;

        PropertyConfiguration cfgConfig = new PropertyConfiguration();
        cfgConfig.setUseAddp(true);
        cfgConfig.setAddpServerTimeout(10);
        cfgConfig.setAddpClientTimeout(10);
        cfgConfig.setAddpTraceMode(ClientADDPOptions.AddpTraceMode.Both);

        String username = cfgProperties.getUserName();
        String password = cfgProperties.getUserPassword();
        String clientName = cfgProperties.getClientName();

        String primaryHost = cfgProperties.getPrimary().getHost().getHostAddress();
        int backupPort = cfgProperties.getBackup().getPort();

        String backupHost = cfgProperties.getBackup().getHost().getHostAddress();
        int primaryPort = cfgProperties.getPrimary().getPort();

        Endpoint primaryEndpoint = new Endpoint("Primary", primaryHost, primaryPort, cfgConfig);
        Endpoint backupEndpoint = new Endpoint("Backup", backupHost, backupPort, cfgConfig);

        cfgConnection = new ConfServerProtocol(primaryEndpoint);
        cfgConnection.setMessageHandler(connectionMessageHandler);
        cfgConnection.setClientApplicationType(CfgAppType.CFGSCE.asInteger());
        cfgConnection.setUserName(username);
        cfgConnection.setUserPassword(password);
        cfgConnection.setClientName(clientName);

        confService = (ConfService) ConfServiceFactory.createConfService(cfgConnection, true);

        warmStandby = new WarmStandby(cfgConnection, primaryEndpoint, backupEndpoint);
        warmStandby.autoRestore();
        warmStandby.open();

        log.info("CFG Connection is opened");
    }

    public void releaseConnection() throws InterruptedException {
        if (warmStandby != null)
            warmStandby.close();
        log.info("CFG Connection is released");
    }
}