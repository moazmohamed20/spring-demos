package com.istnetworks.psdk_demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:genesys.properties")
@ConfigurationProperties(prefix = "cfg")
public class CfgProperties {

  private AddressProperties primary;
  private AddressProperties backup;
  private String userName;
  private Integer tenantId;
  private String clientName;
  private String userPassword;
  private String adminAccessGroupName;

}
