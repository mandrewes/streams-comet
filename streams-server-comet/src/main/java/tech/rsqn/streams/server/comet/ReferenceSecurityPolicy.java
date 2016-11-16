package tech.rsqn.streams.server.comet;

import tech.rsqn.streams.server.comet.authentication.AuthenticationProvider;
import tech.rsqn.streams.server.comet.authentication.AuthorizationProvider;
import tech.rsqn.streams.server.comet.authentication.SessionManager;
import tech.rsqn.streams.server.comet.authentication.UserSession;
import tech.rsqn.streams.server.model.security.Credentials;
import tech.rsqn.streams.server.model.security.User;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.DefaultSecurityPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReferenceSecurityPolicy extends DefaultSecurityPolicy {
    protected static final Logger log = LoggerFactory.getLogger(ReferenceSecurityPolicy.class);

    protected AuthenticationProvider authenticationProvider;
    protected AuthorizationProvider authorizationProvider;
    protected SessionManager sessionManager;

    @Required
    @Autowired
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Required
    @Autowired
    public void setAuthorizationProvider(AuthorizationProvider authorizationProvider) {
        this.authorizationProvider = authorizationProvider;
    }

    @Required
    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean canHandshake(BayeuxServer server, ServerSession session, ServerMessage message) {
        Credentials creds = new Credentials();
        try {
            Map<String, Object> map = message.getExt();
            log.info("getExt = " + map);
            if (map != null && map.get("credentials") != null) {
                BeanUtils.copyProperties(creds, map.get("credentials"));
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        User user = authenticationProvider.authenticate(creds);
        ServerMessage.Mutable reply = message.getAssociated();

        if (user != null) {
            UserSession userSession;
            userSession = sessionManager.newSession(user);
            userSession.setServerSession(session);
            log.info("created session =  {}", userSession);
            reply.put("session", userSession.getId());
        } else {
            reply.put("session", "invalid");
        }

        return true;
    }
}