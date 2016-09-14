package com.rsqn.streams.server.comet.authentication;

import com.rsqn.streams.server.model.security.Credentials;
import com.rsqn.streams.server.model.security.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReferenceAuthenticationProvider implements AuthenticationProvider {
    protected static final Logger log = LoggerFactory.getLogger(ReferenceAuthenticationProvider.class);

    @Override
    public User authenticate(Credentials credentials) {
        User ret = new User();
        ret.setId(credentials.getPrincipal());

        log.info("Successful authentication is hardcoded successful {} ", ToStringBuilder.reflectionToString(credentials));

        return ret;
    }
}
