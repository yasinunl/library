package com.domain.library.security.entity;

public class Token {
    private String token;
    private Role role;

    public Token() {
    }

    public Token(String token, Role role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", role=" + role +
                '}';
    }
}
