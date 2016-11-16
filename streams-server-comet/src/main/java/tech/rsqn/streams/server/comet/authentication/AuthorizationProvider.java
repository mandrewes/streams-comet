package tech.rsqn.streams.server.comet.authentication;


import tech.rsqn.streams.server.model.security.User;

public interface AuthorizationProvider {

    boolean allowOperation(User user);
}
