package tech.rsqn.streams.server.comet.authentication;


import tech.rsqn.streams.server.model.security.Credentials;
import tech.rsqn.streams.server.model.security.User;

public interface AuthenticationProvider {
    User authenticate(Credentials credentials);
}
