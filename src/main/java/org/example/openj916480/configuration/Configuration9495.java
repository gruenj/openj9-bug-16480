package org.example.openj916480.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "demo.config9495")
@Configuration
@Getter
@Setter
public class Configuration9495 {
  private String value;
}
