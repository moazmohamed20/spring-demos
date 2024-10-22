package com.istnetworks.psdk_demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@PropertySource("classpath:genesys.properties")
@ConfigurationProperties(prefix = "tserver")
public class TServerProperties {

  private AddressProperties primary;
  private AddressProperties backup;
  private Integer tenantId;
  private String clientName;
  private String clientPassword;

}
