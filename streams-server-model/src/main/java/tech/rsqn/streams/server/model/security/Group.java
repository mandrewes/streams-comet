package tech.rsqn.streams.server.model.security;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<Role> roles = new ArrayList<>();

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
