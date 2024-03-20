package com.artemus.app.servlet;
public class AuthorizationHeaderHolder {
    private static AuthorizationHeaderHolder instance;
    private String authorizationHeader;

    private AuthorizationHeaderHolder() {}

    public static synchronized AuthorizationHeaderHolder getInstance() {
        if (instance == null) {
            instance = new AuthorizationHeaderHolder();
        }
        return instance;
    }

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    public void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
    }
}
