package com.rsqn.streams.server.comet.authentication;


import com.rsqn.streams.server.model.security.User;

public interface AuthorizationProvider {

    boolean allowOperation(User user);
}