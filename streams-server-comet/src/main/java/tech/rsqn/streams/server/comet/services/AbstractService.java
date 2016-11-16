package tech.rsqn.streams.server.comet.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import tech.rsqn.streams.server.comet.authentication.AuthenticationProvider;
import tech.rsqn.streams.server.comet.authentication.AuthorizationProvider;
import tech.rsqn.streams.server.comet.authentication.SessionManager;
import tech.rsqn.streams.server.comet.authentication.UserSession;
import tech.rsqn.streams.server.comet.store.Store;
import tech.rsqn.streams.server.comet.util.json.CustomObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AbstractService {
    protected static final Logger log = LoggerFactory.getLogger(AbstractService.class);
    protected ObjectMapper objectMapper;
    protected AuthenticationProvider authenticationProvider;
    protected AuthorizationProvider authorizationProvider;
    protected SessionManager sessionManager;
    protected Store store;

    @Inject
    protected BayeuxServer bayeux;
    @Session
    protected ServerSession serverSession;

    @Required
    @Autowired
    public void setStore(Store store) {
        this.store = store;
    }

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

    public AbstractService() {
        objectMapper = new CustomObjectMapper();
//        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected UserSession getSession(ServerMessage msg) {
        String sessionToken = (String)msg.getExt().get("session");
        return sessionManager.resolve(sessionToken);
    }

    protected <T> T parseObj(Class t,ServerMessage msg) {
        try {
            T ret = (T) t.newInstance();
            BeanUtils.copyProperties(ret, msg.getDataAsMap());
            return ret;
//            log.info("Parsing " + msg.getJSON());
//            return (T) objectMapper.readValue(msg.getJSON(),t);
        } catch (Exception e) {
            log.warn(e.getMessage(),e);
        }
        return null;
    }
}
