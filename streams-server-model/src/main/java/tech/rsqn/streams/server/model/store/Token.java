package tech.rsqn.streams.server.model.store;


import tech.rsqn.streams.server.model.messages.TokenRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Token extends Item {
    private TokenRequest request;
    private Map<String,String> parameters = new HashMap<>();

    private Date created;
    private Date expires;
    private String grantedResource;
    private String scope;
    private String token;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getGrantedResource() {
        return grantedResource;
    }

    public void setGrantedResource(String grantedResource) {
        this.grantedResource = grantedResource;
    }

    public TokenRequest getRequest() {
        return request;
    }

    public void setRequest(TokenRequest request) {
        this.request = request;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
