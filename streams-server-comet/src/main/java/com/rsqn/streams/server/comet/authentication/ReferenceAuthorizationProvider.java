package com.rsqn.streams.server.comet.authentication;

import com.rsqn.streams.server.model.security.User;
import org.springframework.stereotype.Component;

@Component
public class ReferenceAuthorizationProvider implements AuthorizationProvider {

    @Override
    public boolean allowOperation(User user) {
        return true;
    }
}
