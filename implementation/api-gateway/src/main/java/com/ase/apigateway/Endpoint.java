package com.ase.apigateway;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:4200")
public class Endpoint {

    private String endpoint;
    private String pathBase = "";
    private List<String> paths;
    private boolean authorize = true;
    private String gatewayPathBase = "";

    /**
     * @return The url of the masked service
     */
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @return The base path of the masked service. Defaults to empty String "". For example:
     * "/api/v1/login" if the service starts all its endpoints with this path.
     */
    public String getPathBase() {
        return pathBase;
    }

    public void setPathBase(String pathBase) {
        this.pathBase = pathBase;
    }

    /**
     * @return A list of all paths which should be forwarded. If a path-base is set, it will be
     * appended to the beginning of each of these paths.
     */
    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    /**
     * @return Whether requests made to the forwarded endpoints need to be authorized or not.
     * Defaults to true.
     */
    public boolean getAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }

    /**
     * @return The path base of the redirected endpoints. This will be appended before every
     * endpoint on this api gateway. (The endpoints of the API-gateway are defined via
     * maskedPathBase + path, where the path is the same as the target service.)
     */
    public String getGatewayPathBase() {
        return gatewayPathBase;
    }

    public void setGatewayPathBase(String gatewayPathBase) {
        this.gatewayPathBase = gatewayPathBase;
    }


    @Override
    public String
    toString() {
        return "Endpoint{" +
                "endpoint='" + endpoint + '\'' +
                ", pathBase='" + pathBase + '\'' +
                ", paths=" + paths +
                ", authorize=" + authorize +
                ", maskedPathBase='" + gatewayPathBase + '\'' +
                '}';
    }
}
