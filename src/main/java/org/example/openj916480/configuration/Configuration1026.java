package org.example.openj916480.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "demo.config1026")
@Configuration
@Getter
@Setter
public class Configuration1026 {
  private String value;
}
