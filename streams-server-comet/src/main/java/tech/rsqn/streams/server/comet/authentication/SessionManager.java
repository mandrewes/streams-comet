package tech.rsqn.streams.server.comet.authentication;


import tech.rsqn.streams.server.model.security.User;

public interface SessionManager {

    UserSession newSession(User user);

    UserSession resolve(String token);
}
