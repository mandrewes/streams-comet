package com.rsqn.streams.server.comet.authentication;

import com.rsqn.streams.server.model.security.User;
import com.rsqn.streams.server.model.store.Token;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReferenceSessionManager implements SessionManager {
    protected static final Logger log = LoggerFactory.getLogger(ReferenceSessionManager.class);

    private Map<String,UserSession> tokensToSessions = new HashMap<>();
    private Map<String,UserSession> sessionIdToSessions = new HashMap<>();

    @Override
    public UserSession newSession(User user) {
        UserSession userSession = new UserSession();
        userSession.setId("s" + RandomStringUtils.randomAlphanumeric(24));
        userSession.setUser(user);

        Token token = new Token();
        token.setScope("SESSION");
        token.setToken("t" + RandomStringUtils.randomAlphanumeric(24));

        userSession.setAuthenticationToken(token);

        tokensToSessions.put(token.getToken(), userSession);
        sessionIdToSessions.put(userSession.getId(), userSession);

        log.info("Created Session " + ToStringBuilder.reflectionToString(userSession));
        return userSession;
    }

    @Override
    public UserSession resolve(String token) {
        UserSession ret = tokensToSessions.get(token);
        if ( ret == null) {
            ret = sessionIdToSessions.get(token);
        }
        return ret;
    }
}

