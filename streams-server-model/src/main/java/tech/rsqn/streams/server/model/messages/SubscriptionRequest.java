package tech.rsqn.streams.server.model.messages;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionRequest extends AbstractMessage {
    String id;
    String resource;
    Map<String,String> ext = new HashMap<>();

    public Map<String, String> getExt() {
        return ext;
    }

    public void setExt(Map<String, String> ext) {
        this.ext = ext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

}
