package org.example.openj916480.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "demo.config4732")
@Configuration
@Getter
@Setter
public class Configuration4732 {
  private String value;
}
