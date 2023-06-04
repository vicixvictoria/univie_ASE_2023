package com.ase.apigateway;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointConfiguration {

    private final EndpointProperties endpointProperties;

    @Autowired
    public EndpointConfiguration(EndpointProperties endpointProperties) {
        this.endpointProperties = endpointProperties;
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder,
            JWTAuthorizationFilter jwtAuthorizationFilter) {
        // get the defined endpoints which need to be set up
        Map<String, Endpoint> myEndpoints = this.endpointProperties.endpoints();

        // construct the object where the endpoints will be defined
        var building = builder.routes();

        // loop through all endpoints
        for (Endpoint endpoint : myEndpoints.values()) {

            // loop through all paths in a given endpoint
            for (String path : endpoint.getPaths()) {

                // add the endpoint with the given path to the routing
                building = building.route(p -> {

                    // if the endpoint is set to be authorized, add an authorization filter to it
                    if (endpoint.getAuthorize()) {
                        return p.path(endpoint.getPathBase() + path + "/**")
                                .filters(f -> {
                                    f.filter(jwtAuthorizationFilter.apply((Void) null));
                                    return f.rewritePath(
                                            endpoint.getPathBase() + path + "/(?<segment>.*)",
                                            endpoint.getGatewayPathBase() + path + "/${segment}");
                                })
                                .uri(endpoint.getEndpoint());
                    }

                    return p.path(endpoint.getPathBase() + path + "/**")
                            .filters(f -> f.rewritePath(
                                    endpoint.getPathBase() + path + "(?<segment>.*)",
                                    endpoint.getGatewayPathBase() + path + "${segment}"))
                            .uri(endpoint.getEndpoint());
                });
            }
        }

        return building.build();
    }
}
