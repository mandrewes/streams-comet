package tech.rsqn.streams.server.model.messages;


import tech.rsqn.streams.server.model.store.Token;

public class TokenResponse {
    Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
