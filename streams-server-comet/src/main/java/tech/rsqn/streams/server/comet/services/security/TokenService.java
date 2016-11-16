package tech.rsqn.streams.server.comet.services.security;

import tech.rsqn.streams.server.comet.authentication.UserSession;
import tech.rsqn.streams.server.comet.services.AbstractService;
import tech.rsqn.streams.server.model.messages.ErrorMessage;
import tech.rsqn.streams.server.model.messages.TokenRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
@Service("TokenService")
public class TokenService extends AbstractService {
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    @Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;

    @PostConstruct
    public void init() {

    }

    @Listener("/service/token")
    public void processRequest(ServerSession remote, ServerMessage message) {
        TokenRequest req = parseObj(TokenRequest.class, message);
        UserSession session = getSession(message);
        req.setSessionId(session.getId());

        log.info("Token Requested " + ToStringBuilder.reflectionToString(req) + " for sessionId " + session.getId());

        log.info("Unknown type of token requested " + req.getResource());
        remote.deliver(serverSession, req.getReturnChannel(), new ErrorMessage().with("UnSupported Token Type", "Unsupported Token Type " + req.getResource()));

    }
}
