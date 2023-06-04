package com.ase.apigateway;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public record EndpointProperties(String userTokenEndpoint, Map<String, Endpoint> endpoints) {

}