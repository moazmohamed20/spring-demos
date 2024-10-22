package com.istnetworks.psdk_demo.property;

import java.net.InetAddress;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class AddressProperties {

    private InetAddress host;
    private int port;

}
