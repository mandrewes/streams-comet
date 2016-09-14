package com.rsqn.streams.server.comet.authentication;


import com.rsqn.streams.server.model.security.Credentials;
import com.rsqn.streams.server.model.security.User;

public interface AuthenticationProvider {
    User authenticate(Credentials credentials);
}
