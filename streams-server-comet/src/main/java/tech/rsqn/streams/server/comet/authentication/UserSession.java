package tech.rsqn.streams.server.comet.authentication;

import tech.rsqn.streams.server.model.security.User;
import tech.rsqn.streams.server.model.store.Token;
import org.cometd.bayeux.server.ServerSession;

public class UserSession<T> {
    private String id;
    private User user;
    private Token authenticationToken;
    private ServerSession serverSession;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(Token authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public ServerSession getServerSession() {
        return serverSession;
    }

    public void setServerSession(ServerSession serverSession) {
        this.serverSession = serverSession;
    }

}
