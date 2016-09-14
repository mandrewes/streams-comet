package com.rsqn.streams.server.model.messages;


public class TokenRequest {
    private String resource;
    private String sessionId;
    private String returnChannel;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getReturnChannel() {
        return returnChannel;
    }

    public void setReturnChannel(String returnChannel) {
        this.returnChannel = returnChannel;
    }

}
