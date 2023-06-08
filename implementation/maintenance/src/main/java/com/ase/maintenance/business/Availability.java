package com.ase.maintenance.business;

public class Availability {
    private final String hostname;
    private final int returnCode;

    public Availability(String hostname, int returnCode) {
        this.hostname = hostname;
        this.returnCode = returnCode;
    }

    public String getHostname() {
        return this.hostname;
    }

    public int getReturnCode() {
        return this.returnCode;
    }

}
