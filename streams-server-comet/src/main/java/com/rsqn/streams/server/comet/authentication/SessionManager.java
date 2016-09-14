package com.rsqn.streams.server.comet.authentication;


import com.rsqn.streams.server.model.security.User;

public interface SessionManager {

    UserSession newSession(User user);

    UserSession resolve(String token);
}
