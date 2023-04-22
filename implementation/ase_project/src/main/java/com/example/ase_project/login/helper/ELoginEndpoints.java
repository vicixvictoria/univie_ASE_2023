package com.example.ase_project.login.helper;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * With the current implementation the LOGIN endpoint (responsible to authenticate users and hand
 * out JWT tokens), has to be unique, regardless of the RequestMethod
 */
public enum ELoginEndpoints {
    LOGIN("/authenticate", RequestMethod.POST),
    REGISTER("/register", RequestMethod.POST),
    GET_USER("", RequestMethod.GET),
    GET_BY_ROLE("/{role}", RequestMethod.GET),
    UPDATE_USER("", RequestMethod.PUT),
    DELETE_USER("", RequestMethod.DELETE);

    private static final String ROOT = "/api/v1/login";
    private final String endpoint;
    private final RequestMethod requestMethod;

    ELoginEndpoints(String endpoint, RequestMethod requestMethod) {
        this.endpoint = endpoint;
        this.requestMethod = requestMethod;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getFullEndpoint() {
        return ROOT + endpoint;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public HttpMethod getHttpMethod() {
        return requestMethod.asHttpMethod();
    }
}
