package tech.rsqn.streams.server.model.security;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private List<Group> groups = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
