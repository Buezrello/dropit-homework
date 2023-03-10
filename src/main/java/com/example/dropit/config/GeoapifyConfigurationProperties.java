package com.example.dropit.config;

import lombok.Data;
import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "geoapify")
@ConfigurationPropertiesScan
@Data
@Generated
public class GeoapifyConfigurationProperties {
    private String host;
    private String token;
}
